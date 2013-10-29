package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.Account;
import model.Database;
import model.User;
import controller.Controller;

/**
 * <p>
 * This class extends JFrame class which will be responsible for the Window
 * frame of the program.
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private AuthenticationPanel authPanel;
	private HomePanel homePanel;
	private SignUpPanel signUpPanel;
	private Controller controller;
	private Database db;
	private List<Account> accntList;
	private CreateAccountPanel newAccountPanel;
	private User user;

	/**
	 * <p>
	 * Constructor Method for the {@link MainFrame}.
	 * </p>
	 **/
	public MainFrame() {
		super("Password Manager");
		authPanel = new AuthenticationPanel();
		homePanel = new HomePanel();
		signUpPanel = new SignUpPanel();
		controller = new Controller();
		newAccountPanel = new CreateAccountPanel();

		// Connect to the Database
		Connection conn = null;
		String dbName = "PasswordDB";

		try {
			db = new Database(conn, dbName);
			controller.dbConnect(db);

		} catch (SQLException e) {
			e.printStackTrace();

		}

		try {
			db.getConn().setAutoCommit(false);
		} catch (SQLException e) {
			JOptionPane
					.showMessageDialog(
							MainFrame.this,
							"Cannot connect to the database, please close another instance of this program before running it again.",
							"Unable to connect to the database",
							JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
		}
		// set Layout
		setLayout(new BorderLayout());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(authPanel, BorderLayout.CENTER);
		setSize(new Dimension(600, 500));
		setResizable(false);
		// window listener
		this.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {

			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				// //////////////Disconnect///////////////////////////
				try {
					db.dbDisconnect();

				} catch (SQLException e) {
					System.out.println("Failed to disconnect");
				}
				// /////////////Shutdown//////////////////////////
				try {
					db.dbShutDown();

				} catch (SQLException e) {
					System.out.println("Failed to shutdown");
				}
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowOpened(WindowEvent arg0) {
			}

		});
		// listener to the AuthenticationPanel
		authPanel
				.setAuthenticationEventListener(new AuthenticationEventListener() {
					public void authenticationEventOccured(
							AuthenticationEvent authEv) {
						// if sign up button has been clicked go to the sign up
						// panel
						if (authEv.isSignUpBtnClicked()) {
							controller.swapPanel(MainFrame.this, authPanel,
									signUpPanel);
						} else {
							// else sign in button has been clicked and must
							// authenticate the user
							if (controller.userAllowed(db,
									authEv.getUsername(), authEv.getPassword())) {
								String username = authEv.getUsername().trim();
								String password = authEv.getPassword();
								// get the user id of the user
								int userId = controller.getUserId(db,
										authEv.getUsername(),
										authEv.getPassword());
								user = new User(userId, username, password);
								setJMenuBar(creatMenus());// set the menu
								// get the list of accounts related to the
								// userId
								accntList = controller.getAccount(db, userId);
								homePanel.setData(accntList);
								// set the fields with the account details
								if (accntList.size() > 0) {
									Account accnt = controller
											.getSelectedAccount(accntList, 0);
									homePanel.getUsernameFld().setText(
											accnt.getUsername());
									homePanel.getPasswordFld().setText(
											accnt.getPassword());
									homePanel.getEmailFld().setText(
											accnt.getEmail());
									homePanel.getSecretQuestionFld().setText(
											accnt.getQuestion());
									homePanel.getAnswerFld().setText(
											accnt.getAnswer());
									homePanel.getNotes().setText(
											accnt.getNotes());
									homePanel.refresh();
								}
								// go into the home panel
								controller.swapPanel(MainFrame.this, authPanel,
										homePanel);
							} else {
								JOptionPane
										.showMessageDialog(
												MainFrame.this,
												"User does not exist or wrong credentials have been entered",
												"Unable to Sign In",
												JOptionPane.OK_OPTION);
							}

						}

					}

				});
		// listener to the cancel button found in the CreateAccountPanel
		newAccountPanel
				.setCancelCreateAccountEventListener(new CancelCreateAccountEventListener() {

					public void cancelCreateAccountEventOccured(
							CancelCreateAccountEvent ev) {
						// confirm the user if he/she wants to cancel the
						// account creation
						int option = JOptionPane
								.showConfirmDialog(
										MainFrame.this,
										"Do you really want to cancel? The account details will not be save.",
										"Cancel Account Creation",
										JOptionPane.OK_CANCEL_OPTION);
						if (option == JOptionPane.OK_OPTION) {
							// if yes then reset the fields
							newAccountPanel.getNameFld().setText("");
							newAccountPanel.getUsernameFld().setText("");
							newAccountPanel.getPasswordFld().setText("");
							newAccountPanel.getEmailFld().setText("");
							newAccountPanel.getQuestionFld().setText("");
							newAccountPanel.getAnswerFld().setText("");
							newAccountPanel.getNotesArea().setText("");
							controller.swapPanel(MainFrame.this,
									newAccountPanel, homePanel);
						}
					}
				});
		// listener to the create button found in the CreateAccountPanel
		newAccountPanel
				.setCreateAccountEventListener(new CreateAccountEventListener() {

					public void createAccountEventOccured(CreateAccountEvent ev) {
						// get the account details entered in the fields
						String name = ev.getName();
						String username = ev.getUsername();
						String password = ev.getPassword();
						String email = ev.getEmail();
						String question = ev.getQuestion();
						String answer = ev.getAnswer();
						String notes = ev.getNotes();
						int userId = user.getUserId();
						// check if the input are all valid
						if (name.length() > 50) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account name which is 50",
											"Invalid Account Name",
											JOptionPane.OK_OPTION);
						} else if (username.length() > 50) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account username which is 50",
											"Invalid Account Username",
											JOptionPane.OK_OPTION);
						} else if (password.length() > 20) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account password which is 20",
											"Invalid Account Password",
											JOptionPane.OK_OPTION);
						} else if (email.length() > 50) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account email which is 50",
											"Invalid Account Email",
											JOptionPane.OK_OPTION);
						} else if (question.length() > 50) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account secret question which is 50",
											"Invalid Account Secret Question",
											JOptionPane.OK_OPTION);
						} else if (answer.length() > 50) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account answer which is 50",
											"Invalid Account Answer",
											JOptionPane.OK_OPTION);
						} else if (notes.length() > 140) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"You have exceeded the allowed number of characters for the account notes which is 140",
											"Invalid Account Notes",
											JOptionPane.OK_OPTION);
						} else if (name.equals("")) {
							JOptionPane.showMessageDialog(MainFrame.this,
									"Please enter a name for the account.",
									"Invalid Account", JOptionPane.OK_OPTION);
						} else if (password.equals("")) {
							JOptionPane.showMessageDialog(MainFrame.this,
									"Please enter a password for the account.",
									"Invalid Account", JOptionPane.OK_OPTION);
						} else {
							// if all inputs are valid then create a new account
							controller.createAccount(db, userId, name,
									username, password, email, question,
									answer, notes);
							// commit the new account in the database
							try {
								db.dbCommit();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							// reset the fields
							newAccountPanel.getNameFld().setText("");
							newAccountPanel.getUsernameFld().setText("");
							newAccountPanel.getPasswordFld().setText("");
							newAccountPanel.getEmailFld().setText("");
							newAccountPanel.getQuestionFld().setText("");
							newAccountPanel.getAnswerFld().setText("");
							newAccountPanel.getNotesArea().setText("");
							// refresh the account list
							accntList = controller.getAccount(db, userId);
							homePanel.setData(accntList);
							homePanel.refresh();
							controller.swapPanel(MainFrame.this,
									newAccountPanel, homePanel);
						}
					}
				});
		// listener to the new button found in the HomePanel
		homePanel.setNewAccountEventListener(new NewAccountEventListener() {

			public void newAccountEventOccured(NewAccountEvent ev) {
				// go to the CreateAccountPanel
				controller
						.swapPanel(MainFrame.this, homePanel, newAccountPanel);
			}
		});
		// listener to the save button found in the HomePanel, it updates the
		// account
		homePanel.setUpdateEventListener(new UpdateEventListener() {

			public void updateEventOccured(UpdateEvent updateEv) {
				// get the account details from the fields
				int userId = user.getUserId();
				int id = updateEv.getId();
				String name = updateEv.getName();
				String username = updateEv.getUsername();
				String password = updateEv.getPassword();
				String email = updateEv.getEmail();
				String question = updateEv.getQuestion();
				String answer = updateEv.getAnswer();
				String notes = updateEv.getNotes();
				// update it to the database
				db.updateDataToPD(id, name, username, password, email,
						question, answer, notes);
				// commit changes to the database
				try {
					db.dbCommit();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				// refresh the account list
				accntList = controller.getAccount(db, userId);
				homePanel.setData(accntList);
				homePanel.refresh();
			}
		});
		// listener to the delete button found in the HomePanel, it deletes the
		// selected account
		homePanel
				.setDeleteAccountEventListener(new DeleteAccountEventListener() {

					public void deleteAccountEventOccured(DeleteAccountEvent ev) {
						// get the necessary details to delete an account
						Account accnt = ev.getAccount();
						int accntId = accnt.getAccntId();
						int userId = user.getUserId();
						// check if there is an account selected
						if (accnt != null) {
							// if account exist then confirm the deletion
							int option = JOptionPane.showConfirmDialog(
									MainFrame.this,
									"Do you really want to delete this account "
											+ accnt.getName() + "?", "Delete "
											+ accnt.getName() + "?",
									JOptionPane.OK_CANCEL_OPTION);
							if (option == JOptionPane.OK_OPTION) {
								// if yes then delete the account with the
								// account id
								controller.deleteAccount(db, accntId);
								// commit the changes
								try {
									db.dbCommit();
								} catch (SQLException e) {

									e.printStackTrace();
								}
								// refresh the account list
								accntList = controller.getAccount(db, userId);
								homePanel.setData(accntList);
								homePanel.refresh();
								// update the details shown in the HomePanel
								int selectedRow = homePanel.getSelectedRow2() - 1;
								if (selectedRow < 0) {
									homePanel.setSelectedRow(0);
								} else {
									homePanel.setSelectedRow(selectedRow);
								}
								if (accntList.size() > 0) {
									// show the account details in the fields
									Account accnt2 = controller
											.getSelectedAccount(accntList,
													homePanel.getSelectedRow2());
									homePanel.getUsernameFld().setText(
											accnt2.getUsername());
									homePanel.getPasswordFld().setText(
											accnt2.getPassword());
									homePanel.getEmailFld().setText(
											accnt2.getEmail());
									homePanel.getSecretQuestionFld().setText(
											accnt2.getQuestion());
									homePanel.getAnswerFld().setText(
											accnt2.getAnswer());
									homePanel.getNotes().setText(
											accnt2.getNotes());
									homePanel.refresh();
								} else {
									// if there is no account left after
									// deletion then set the fields to empty
									homePanel.getUsernameFld().setText("");
									homePanel.getPasswordFld().setText("");
									homePanel.getEmailFld().setText("");
									homePanel.getSecretQuestionFld()
											.setText("");
									homePanel.getAnswerFld().setText("");
									homePanel.getNotes().setText("");
									homePanel.refresh();
								}
							}
						}
					}
				});
		// listener to the SignUpPanel
		signUpPanel.setSignUpEventListener(new SignUpEventListener() {

			public void signUpEventOccured(SignUpEvent signUpEv) {
				// if cancel button have been clicked in the SignUpPanel
				if (signUpEv.isCancelIsClicked()) {
					// go back to the AuthenticationPanel
					controller
							.swapPanel(MainFrame.this, signUpPanel, authPanel);
				} else {
					// if create button have been clicked then get the details
					String username = signUpEv.getUsername().trim();
					String password = signUpEv.getPassword();
					String passwordConfrm = signUpEv.getPasswordConfirm();
					// check if there is no invalid characters have been entered
					Pattern p = Pattern.compile("[^a-z0-9]",
							Pattern.CASE_INSENSITIVE);
					Matcher usernameMatcher = p.matcher(username);
					boolean isAlphaNumeric = usernameMatcher.find();
					// check if the details entered are all valid
					if (username.length() < 4) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Username must be 4 or more characters.",
								"Invalid Username", JOptionPane.OK_OPTION);
					} else if (isAlphaNumeric) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Username must be alphanumeric only.",
								"Invalid Username", JOptionPane.OK_OPTION);
					} else if (password.length() < 6) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Password must be 6 or more characters.",
								"Invalid Password", JOptionPane.OK_OPTION);
					} else if (isAlphaNumeric) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Username must be alphanumeric only.",
								"Invalid Password", JOptionPane.OK_OPTION);
					} else if (!password.equals(passwordConfrm)) {
						JOptionPane
								.showMessageDialog(
										MainFrame.this,
										"Please check and confirm your password again.",
										"Confirmation Failed",
										JOptionPane.OK_OPTION);
					} else if (db.userExist(username, password)) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Username and Password have been used already",
								"User Exist Already", JOptionPane.OK_OPTION);
					} else {
						// if all details are all valid then create a new User
						controller.signUpUser(db, username, password);
						// get the user details and switch to the HomePanel
						int userId = controller.getUserId(db,
								signUpEv.getUsername(), signUpEv.getPassword());
						user = new User(userId, username, password);
						setJMenuBar(creatMenus());
						accntList = controller.getAccount(db, userId);
						homePanel.setData(accntList);

						if (accntList.size() > 0) {
							Account accnt = controller.getSelectedAccount(
									accntList, 0);
							homePanel.getUsernameFld().setText(
									accnt.getUsername());
							homePanel.getPasswordFld().setText(
									accnt.getPassword());
							homePanel.getEmailFld().setText(accnt.getEmail());
							homePanel.getSecretQuestionFld().setText(
									accnt.getQuestion());
							homePanel.getAnswerFld().setText(accnt.getAnswer());
							homePanel.getNotes().setText(accnt.getNotes());
							homePanel.refresh();
						}
						try {
							db.dbCommit();
						} catch (SQLException e) {
							JOptionPane
									.showMessageDialog(
											MainFrame.this,
											"Failed to save your details into the database! Please contact CyberPipzProduction.",
											"Database Commit Error",
											JOptionPane.OK_OPTION);
						}
						JOptionPane
								.showMessageDialog(
										MainFrame.this,
										"Congratulations, you have created an account successfully! You will be signed in automatically.",
										"User Creation Successful",
										JOptionPane.OK_OPTION
												| JOptionPane.INFORMATION_MESSAGE);
						controller.swapPanel(MainFrame.this, signUpPanel,
								homePanel);

					}
				}
			}
		});
	}

	/**
	 * <p>
	 * This method sets up and return a Menu.
	 * </p>
	 * 
	 * @return A JMenuBar.
	 **/
	public JMenuBar creatMenus() {
		JMenuBar menu = new JMenuBar();
		// fileMenu
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem logOutItem = new JMenuItem("Log Out");
		JMenu database = new JMenu("Database");
		JMenuItem deleteAllItem = new JMenuItem("Delete All Accounts");
		JMenuItem deleteUserItem = new JMenuItem("Delete Current User");
		// ///////////File Menu////////////
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		logOutItem.setMnemonic(KeyEvent.VK_L);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		logOutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		// log out listener
		logOutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// set the fields to empty and switch to the AuthenticationPanel
				homePanel.getUsernameFld().setText("");
				homePanel.getPasswordFld().setText("");
				homePanel.getEmailFld().setText("");
				homePanel.getSecretQuestionFld().setText("");
				homePanel.getAnswerFld().setText("");
				homePanel.getNotes().setText("");
				homePanel.refresh();
				controller.swapPanel(MainFrame.this, homePanel, authPanel);
				setJMenuBar(null);// set off the menu
				authPanel.getUserNameFld().setText("");
				authPanel.getPasswordFld().setText("");
			}
		});
		// Exit listener
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// /disconnect the database
				try {
					db.dbDisconnect();
				} catch (SQLException e) {
					System.out.println("Failed to disconnect");
				}
				// /////////////Shutdown//////////////////////////
				try {
					db.dbShutDown();
				} catch (SQLException e) {
					System.out.println("Failed to shutdown");
				}
				System.exit(0);
			}
		});
		fileMenu.add(logOutItem);
		fileMenu.add(exitItem);
		// //////////Database Menu//////////////
		// Delete all account listener
		deleteAllItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// confirm if the user wants to delete all accounts
				int option = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to delete ALL of your accounts?",
						"Delete All Accounts?", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					// if yes then delete all the accounts associated with the
					// user id
					controller.deleteAllAccountByUserId(db, user.getUserId());
					accntList = controller.getAccount(db, user.getUserId());
					homePanel.setData(accntList);
					homePanel.refresh();
					// commit the changes to the database
					try {
						db.dbCommit();
					} catch (SQLException e) {
						JOptionPane
								.showMessageDialog(
										MainFrame.this,
										"Failed to delete All of your Accounts! Please contact CyberPipzProduction.",
										"Database Commit Error",
										JOptionPane.OK_OPTION);
					}
					homePanel.getUsernameFld().setText("");
					homePanel.getPasswordFld().setText("");
					homePanel.getEmailFld().setText("");
					homePanel.getSecretQuestionFld().setText("");
					homePanel.getAnswerFld().setText("");
					homePanel.getNotes().setText("");
					homePanel.refresh();
				}
			}
		});
		// Delete the current user listener
		deleteUserItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// confirm the user if he/she wants to be deleted
				int option = JOptionPane
						.showConfirmDialog(
								MainFrame.this,
								"Do you really want to remove your priviledge of using this amazing Password Manager?",
								"Remove Your Self?",
								JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					// if yes then delete All accounts and all of his user
					// priviledges
					controller.deleteAllAccountByUserId(db, user.getUserId());
					controller.deleteUserByUserId(db, user.getUserId());
					try {
						db.dbCommit();
					} catch (SQLException e) {
						JOptionPane
								.showMessageDialog(
										MainFrame.this,
										"Failed to commit changes in the database! Please contact CyberPipzProduction.",
										"Database Commit Error",
										JOptionPane.OK_OPTION);
					}
					// go back the AuthenticationPanel
					controller.swapPanel(MainFrame.this, homePanel, authPanel);
					setJMenuBar(null);
					authPanel.getUserNameFld().setText("");
					authPanel.getPasswordFld().setText("");
				}
			}
		});
		database.setMnemonic(KeyEvent.VK_D);
		database.add(deleteAllItem);
		database.add(deleteUserItem);
		menu.add(fileMenu);
		menu.add(database);
		return menu;
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import model.Account;

/**
 * <p>
 * This class extends JPanel class which will be responsible for the Home/Main
 * panel of the program
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class HomePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable accountTbl;
	private AccountTableModel accntTblMdl;
	private JTextField usernameFld;
	private JTextField passwordFld;
	private JTextField emailFld;
	private JTextField secretQuestionFld;
	private JTextField answerFld;
	private JTextArea notes;
	private JButton newAccount;
	private JButton deleteAccount;
	private JButton edit;
	private JButton cancel;
	private JButton save;
	private UpdateEventListener updateEventListener;
	private static int selectedRow = 0;
	private NewAccountEventListener newAccountListener;
	private DeleteAccountEventListener deleteListener;

	/**
	 * <p>
	 * Constructor Method for the {@link HomePanel}.
	 * </p>
	 **/
	public HomePanel() {
		accntTblMdl = new AccountTableModel();
		accountTbl = new JTable(accntTblMdl);
		usernameFld = new JTextField(15);
		passwordFld = new JTextField(15);
		emailFld = new JTextField(15);
		secretQuestionFld = new JTextField(15);
		answerFld = new JTextField(15);
		notes = new JTextArea(10, 15);
		newAccount = new JButton("New");
		deleteAccount = new JButton("Delete");
		edit = new JButton("Edit");
		cancel = new JButton("Cancel");
		save = new JButton("Save");
		// Create the layout of the home/main panel
		homePanelLayout();
		// delete account button action listener. Passes the account to be
		// deleted.
		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Account accnt;
				List<Account> accounts = accntTblMdl.getAccountList();
				// determining which account is selected to be deleted
				if (accounts.size() != 0) {
					if (accountTbl.getSelectedRow() >= 0) {
						accnt = accounts.get(accountTbl.getSelectedRow());
						selectedRow = accountTbl.getSelectedRow();
					} else {
						accnt = accounts.get(selectedRow);
					}
					DeleteAccountEvent ev = new DeleteAccountEvent(arg0, accnt);
					if (deleteListener != null) {
						deleteListener.deleteAccountEventOccured(ev);
					}
				}
			}
		});
		// Account Table mouse listener. Passes the account's details which is
		// being selected in the table.
		accountTbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					List<Account> accounts = accntTblMdl.getAccountList();
					Account accnt = accounts.get(accountTbl.getSelectedRow());
					usernameFld.setText(accnt.getUsername());
					passwordFld.setText(accnt.getPassword());
					emailFld.setText(accnt.getEmail());
					secretQuestionFld.setText(accnt.getQuestion());
					answerFld.setText(accnt.getAnswer());
					notes.setText(accnt.getNotes());
				}
			}
		});
		// edit button action listener.
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Account> accounts = accntTblMdl.getAccountList();
				// Change the visibility or editability of the buttons or fields
				// accordingly.
				if (accounts.size() != 0) {
					edit.setVisible(false);
					save.setVisible(true);
					cancel.setVisible(true);
					usernameFld.setEditable(true);
					passwordFld.setEditable(true);
					emailFld.setEditable(true);
					secretQuestionFld.setEditable(true);
					answerFld.setEditable(true);
					notes.setEditable(true);
				} else {
					JOptionPane.showMessageDialog(HomePanel.this,
							"You dont have any accounts to edit",
							"Unable to Edit", JOptionPane.OK_OPTION);
				}
			}
		});
		// new account button action listener.
		newAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewAccountEvent event = new NewAccountEvent(arg0);

				if (newAccountListener != null) {
					newAccountListener.newAccountEventOccured(event);
				}
			}
		});
		// cancel button action listener.
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane
						.showConfirmDialog(
								HomePanel.this,
								"Do you really want to cancel? The account details will not be save.",
								"Cancel Account Creation",
								JOptionPane.OK_CANCEL_OPTION);
				// If canceled it change the visibility or editability of the
				// buttons or fields accordingly.
				if (option == JOptionPane.OK_OPTION) {
					List<Account> accounts = accntTblMdl.getAccountList();
					Account accnt = accounts.get(selectedRow);

					edit.setVisible(true);
					save.setVisible(false);
					cancel.setVisible(false);
					usernameFld.setEditable(false);
					passwordFld.setEditable(false);
					emailFld.setEditable(false);
					secretQuestionFld.setEditable(false);
					answerFld.setEditable(false);
					notes.setEditable(false);
					// Set the display values of the fields
					usernameFld.setText(accnt.getUsername());
					passwordFld.setText(accnt.getPassword());
					emailFld.setText(accnt.getEmail());
					secretQuestionFld.setText(accnt.getQuestion());
					answerFld.setText(accnt.getAnswer());
					notes.setText(accnt.getNotes());
					refresh();
				}
			}
		});
		// save button action listener.
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameFld.getText().trim();
				String password = passwordFld.getText().trim();
				String email = emailFld.getText().trim();
				String question = secretQuestionFld.getText();
				String answer = answerFld.getText();
				String note = notes.getText();
				// check if the inputs are all valid.
				if (username.length() > 50) {
					JOptionPane
							.showMessageDialog(
									HomePanel.this,
									"You have exceeded the allowed number of characters for the account username which is 50",
									"Invalid Account Username",
									JOptionPane.OK_OPTION);
				} else if (password.length() > 20) {
					JOptionPane
							.showMessageDialog(
									HomePanel.this,
									"You have exceeded the allowed number of characters for the account password which is 20",
									"Invalid Account Password",
									JOptionPane.OK_OPTION);
				} else if (email.length() > 50) {
					JOptionPane
							.showMessageDialog(
									HomePanel.this,
									"You have exceeded the allowed number of characters for the account email which is 50",
									"Invalid Account Email",
									JOptionPane.OK_OPTION);
				} else if (question.length() > 50) {
					JOptionPane
							.showMessageDialog(
									HomePanel.this,
									"You have exceeded the allowed number of characters for the account secret question which is 50",
									"Invalid Account Secret Question",
									JOptionPane.OK_OPTION);
				} else if (answer.length() > 50) {
					JOptionPane
							.showMessageDialog(
									HomePanel.this,
									"You have exceeded the allowed number of characters for the account answer which is 50",
									"Invalid Account Answer",
									JOptionPane.OK_OPTION);
				} else if (note.length() > 140) {
					JOptionPane
							.showMessageDialog(
									HomePanel.this,
									"You have exceeded the allowed number of characters for the account notes which is 140",
									"Invalid Account Notes",
									JOptionPane.OK_OPTION);
				} else {
					Account accnt;
					List<Account> accounts = accntTblMdl.getAccountList();
					// determine the selected account in the table
					if (accountTbl.getSelectedRow() >= 0) {
						accnt = accounts.get(accountTbl.getSelectedRow());
						selectedRow = accountTbl.getSelectedRow();
					} else {
						accnt = accounts.get(selectedRow);
					}
					// pass all the relevant details of the selected account
					UpdateEvent updateEvent = new UpdateEvent(arg0, accnt
							.getAccntId(), accnt.getName(), username, password,
							email, question, answer, note);
					// change the visibility or editability of the buttons or
					// fields accordingly.
					edit.setVisible(true);
					save.setVisible(false);
					cancel.setVisible(false);
					usernameFld.setEditable(false);
					passwordFld.setEditable(false);
					emailFld.setEditable(false);
					secretQuestionFld.setEditable(false);
					answerFld.setEditable(false);
					notes.setEditable(false);
					if (updateEventListener != null) {
						updateEventListener.updateEventOccured(updateEvent);
					}
				}
			}
		});
	}

	/**
	 * <p>
	 * This method sets up the Layout of the {@link HomePanel}.
	 * </p>
	 **/
	public void homePanelLayout() {

		JPanel accountPanel = new JPanel();
		JPanel detailsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		int space = 0;
		int spaceDetails = 15;

		Border spaceBorder = BorderFactory.createEmptyBorder(48, space, space,
				space);
		Border titleBorder = BorderFactory.createTitledBorder("Accounts");
		accountPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,
				titleBorder));

		Border spaceBorderDetails = BorderFactory.createEmptyBorder(48,
				spaceDetails, spaceDetails, spaceDetails);
		Border titleBorderDetails = BorderFactory.createTitledBorder("Details");
		detailsPanel.setBorder(BorderFactory.createCompoundBorder(
				spaceBorderDetails, titleBorderDetails));

		detailsPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		/*************** Table ************/
		JPanel buttonLayout = new JPanel();
		buttonLayout.setLayout(new FlowLayout());
		newAccount.setMnemonic(KeyEvent.VK_N);
		deleteAccount.setMnemonic(KeyEvent.VK_T);
		buttonLayout.add(newAccount);
		buttonLayout.add(deleteAccount);
		accountPanel.setLayout(new BorderLayout());
		accountPanel.setPreferredSize(new Dimension(200, 100));
		accountPanel.add(buttonLayout, BorderLayout.SOUTH);
		accountPanel.add(new JScrollPane(accountTbl));

		/*************** Details ************/

		// ///////Username///////////////
		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = .5;
		gc.weighty = .5;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		detailsPanel.add(new JLabel("Username: "), gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		usernameFld.setEditable(false);
		detailsPanel.add(usernameFld, gc);
		// //////////Password//////////////
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.EAST;
		detailsPanel.add(new JLabel("Password: "), gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		passwordFld.setEditable(false);
		detailsPanel.add(passwordFld, gc);
		// //////////Password//////////////
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.EAST;
		detailsPanel.add(new JLabel("Email: "), gc);
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		emailFld.setEditable(false);
		detailsPanel.add(emailFld, gc);
		// //////////Secret Question//////////////
		gc.gridx = 0;
		gc.gridy = 3;
		gc.anchor = GridBagConstraints.EAST;
		detailsPanel.add(new JLabel("Secret Question: "), gc);
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		secretQuestionFld.setEditable(false);
		detailsPanel.add(secretQuestionFld, gc);
		// //////////Answer//////////////
		gc.gridx = 0;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.EAST;
		detailsPanel.add(new JLabel("Answer: "), gc);
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		answerFld.setEditable(false);
		detailsPanel.add(answerFld, gc);
		// //////////Answer//////////////
		gc.gridx = 0;
		gc.gridy = 5;
		gc.anchor = GridBagConstraints.EAST;
		detailsPanel.add(new JLabel("Notes: "), gc);
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;
		gc.weightx = 2;
		gc.weighty = 2;
		notes.setEditable(false);
		detailsPanel.add(new JScrollPane(notes), gc);
		// ///////////Buttons//////////////
		gc.gridx = 1;
		gc.gridy = 6;

		save.setMnemonic(KeyEvent.VK_S);
		edit.setMnemonic(KeyEvent.VK_E);
		cancel.setMnemonic(KeyEvent.VK_C);
		save.setVisible(false);
		cancel.setVisible(false);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(edit);
		buttonsPanel.add(save);
		buttonsPanel.add(cancel);
		detailsPanel.add(buttonsPanel, gc);
		/*************** Combine all the panels ************/
		setLayout(new BorderLayout());
		add(new JScrollPane(accountPanel), BorderLayout.WEST);
		add(detailsPanel, BorderLayout.CENTER);
	}

	public void setDeleteAccountEventListener(
			DeleteAccountEventListener listener) {
		this.deleteListener = listener;
	}

	public void setNewAccountEventListener(NewAccountEventListener listener) {
		this.newAccountListener = listener;
	}

	public void setUpdateEventListener(UpdateEventListener listener) {
		this.updateEventListener = listener;
	}

	public void setSelectedRow(int selectedRow) {
		HomePanel.selectedRow = selectedRow;
	}

	public int getSelectedRow2() {
		return selectedRow;
	}

	public JTextField getEmailFld() {
		return emailFld;
	}

	public JTextField getSecretQuestionFld() {
		return secretQuestionFld;
	}

	public JTextField getAnswerFld() {
		return answerFld;
	}

	public JTextArea getNotes() {
		return notes;
	}

	public JTable getAccountTbl() {
		return accountTbl;
	}

	public void setSelectedRow() {
		ListSelectionModel selectionModel = accountTbl.getSelectionModel();
		selectionModel.setSelectionInterval(0, 0);
	}

	public int getSelectedRow() {
		return accountTbl.convertRowIndexToModel(accountTbl.getSelectedRow());
	}

	public void setData(List<Account> account) {
		accntTblMdl.setData(account);
	}

	public JTextField getUsernameFld() {
		return usernameFld;
	}

	public JTextField getPasswordFld() {
		return passwordFld;
	}

	public void refresh() {
		accntTblMdl.fireTableDataChanged();
	}
}

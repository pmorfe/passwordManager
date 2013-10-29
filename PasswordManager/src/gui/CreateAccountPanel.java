package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * <p>
 * This class extends JPanel class which will be responsible for the create account
 * panel of the program
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class CreateAccountPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField usernameFld;
	private JTextField passwordFld;
	private JTextField emailFld;
	private JTextField questionFld;
	private JTextField answerFld;
	private JTextArea notesArea;
	private JTextField nameFld;
	private JButton createBtn;
	private JButton cancelBtn;
	private CancelCreateAccountEventListener cancelListener;
	private CreateAccountEventListener createListener;

	/**
	 * <p>
	 * Constructor Method for the {@link CreateAccountPanel}
	 * </p>
	 **/
	public CreateAccountPanel() {
		nameFld = new JTextField(15);
		usernameFld = new JTextField(15);
		passwordFld = new JTextField(15);
		emailFld = new JTextField(15);
		questionFld = new JTextField(15);
		answerFld = new JTextField(15);
		notesArea = new JTextArea(10, 15);
		createBtn = new JButton("Create");
		cancelBtn = new JButton("Cancel");
		// Create the layout of the account creation panel
		createAccountLayout();
		// Create button action listener. Passes the user inputs into the
		// CreateAccountEvent.
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameFld.getText().trim();
				String username = usernameFld.getText().trim();
				String password = passwordFld.getText().trim();
				String email = emailFld.getText().trim();
				String question = questionFld.getText();
				String answer = answerFld.getText();
				String note = notesArea.getText();
				CreateAccountEvent ev = new CreateAccountEvent(arg0, name,
						username, password, email, question, answer, note);
				if (createListener != null) {
					createListener.createAccountEventOccured(ev);
				}
			}
		});
		// Cancel button action listener. Creates a CancelCreateAccountEvent
		// object.
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CancelCreateAccountEvent ev = new CancelCreateAccountEvent(arg0);
				if (cancelListener != null) {
					cancelListener.cancelCreateAccountEventOccured(ev);
				}
			}
		});
	}

	/**
	 * <p>
	 * This method sets up the Layout of the {@link CreateAccountPanel}.
	 * </p>
	 **/
	public void createAccountLayout() {
		JPanel accountInfopanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		int space = 25;

		Border spaceBorder = BorderFactory.createEmptyBorder(space, space,
				space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Create Account");
		accountInfopanel.setBorder(BorderFactory.createCompoundBorder(
				spaceBorder, titleBorder));

		accountInfopanel.setLayout(new GridBagLayout());
		/*************** Account Name ************/
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Account Name: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		accountInfopanel.add(nameFld, gc);
		/*************** Username ************/
		gc.gridx = 0;
		gc.gridy++;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Username: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		accountInfopanel.add(usernameFld, gc);
		/*************** Password ************/
		gc.gridx = 0;
		gc.gridy++;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Password: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		accountInfopanel.add(passwordFld, gc);
		/*************** Email ************/
		gc.gridx = 0;
		gc.gridy++;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Email: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		accountInfopanel.add(emailFld, gc);
		/*************** Question ************/
		gc.gridx = 0;
		gc.gridy++;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Secret Question: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		accountInfopanel.add(questionFld, gc);
		/*************** Answer ************/
		gc.gridx = 0;
		gc.gridy++;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Answer: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		accountInfopanel.add(answerFld, gc);
		/*************** Notes ************/
		gc.gridx = 0;
		gc.gridy++;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		accountInfopanel.add(new JLabel("Notes: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;

		accountInfopanel.add(new JScrollPane(notesArea), gc);
		/*************** Buttons ************/
		gc.gridx = 1;
		gc.gridy++;
		gc.anchor = GridBagConstraints.EAST;
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		createBtn.setMnemonic(KeyEvent.VK_R);
		cancelBtn.setMnemonic(KeyEvent.VK_C);
		buttonPanel.add(createBtn);
		buttonPanel.add(cancelBtn);
		accountInfopanel.add(buttonPanel, gc);

		setLayout(new BorderLayout());
		add(accountInfopanel, BorderLayout.CENTER);

	}

	public JTextArea getNotesArea() {
		return notesArea;
	}

	public JTextField getUsernameFld() {
		return usernameFld;
	}

	public JTextField getPasswordFld() {
		return passwordFld;
	}

	public JTextField getEmailFld() {
		return emailFld;
	}

	public JTextField getQuestionFld() {
		return questionFld;
	}

	public JTextField getAnswerFld() {
		return answerFld;
	}

	public JTextField getNameFld() {
		return nameFld;
	}

	public void setCancelCreateAccountEventListener(
			CancelCreateAccountEventListener listener) {
		this.cancelListener = listener;
	}

	public void setCreateAccountEventListener(
			CreateAccountEventListener listener) {
		this.createListener = listener;
	}
}

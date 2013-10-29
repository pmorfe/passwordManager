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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * <p>
 * This class extends JPanel class which will be responsible for Sign up panel
 * of the program
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class SignUpPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel usernameLbl;
	private JLabel passwordLbl;
	private JLabel passwordConfrmLbl;
	private JTextField usernameFld;
	private JPasswordField passwordFld;
	private JPasswordField passwordConfrmFld;
	private JButton createBtn;
	private JButton cancelBtn;
	private SignUpEventListener signUpEventListener;

	/**
	 * <p>
	 * Constructor Method for the {@link SignUpPanel}.
	 * </p>
	 **/
	public SignUpPanel() {
		usernameLbl = new JLabel("Username: ");
		passwordLbl = new JLabel("Password: ");
		passwordConfrmLbl = new JLabel("Confirm Password: ");
		usernameFld = new JTextField(10);
		passwordFld = new JPasswordField(10);
		passwordConfrmFld = new JPasswordField(10);
		createBtn = new JButton("Create");
		cancelBtn = new JButton("Cancel");
		// Create the layout of the sign up panel
		signUpPanelLayout();
		// Cancel button action listener.
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SignUpEvent signUpEvent = new SignUpEvent(arg0);
				signUpEvent.setCancelIsClicked(true);
				// Set the Field to have empty texts
				usernameFld.setText("");
				passwordFld.setText("");
				passwordConfrmFld.setText("");
				if (signUpEventListener != null) {
					signUpEventListener.signUpEventOccured(signUpEvent);
				}
			}
		});
		// Create button action listener. Passes the user's details from the
		// fields.
		createBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameFld.getText();
				String password = new String(passwordFld.getPassword());
				String passwordConfrm = new String(passwordConfrmFld
						.getPassword());

				SignUpEvent signUpEvent = new SignUpEvent(arg0, username,
						password, passwordConfrm);
				if (signUpEventListener != null) {
					signUpEventListener.signUpEventOccured(signUpEvent);
				}
			}
		});
	}

	/**
	 * <p>
	 * This method sets up the Layout of the {@link SignUpPanel}.
	 * </p>
	 **/
	public void signUpPanelLayout() {

		JPanel logInInfopanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		int space = 150;

		Border spaceBorder = BorderFactory.createEmptyBorder(space, space,
				space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Sign Up");
		logInInfopanel.setBorder(BorderFactory.createCompoundBorder(
				spaceBorder, titleBorder));

		logInInfopanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		/*************** Username ************/
		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		logInInfopanel.add(usernameLbl, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		logInInfopanel.add(usernameFld, gc);
		/*************** Password ************/
		gc.gridx = 0;
		gc.gridy = 1;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.anchor = GridBagConstraints.EAST;

		logInInfopanel.add(passwordLbl, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;

		logInInfopanel.add(passwordFld, gc);

		/*************** Password Confirmation ************/
		gc.gridx = 0;
		gc.gridy = 2;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.anchor = GridBagConstraints.EAST;

		logInInfopanel.add(passwordConfrmLbl, gc);

		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 1;

		logInInfopanel.add(passwordConfrmFld, gc);
		/*************** Buttons ************/

		gc.gridx = 1;
		gc.gridy = 3;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.anchor = GridBagConstraints.WEST;

		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		createBtn.setMnemonic(KeyEvent.VK_R);
		cancelBtn.setMnemonic(KeyEvent.VK_C);
		buttonPanel.add(createBtn);
		buttonPanel.add(cancelBtn);
		logInInfopanel.add(buttonPanel, gc);

		setLayout(new BorderLayout());
		add(logInInfopanel, BorderLayout.CENTER);
	}

	public void setSignUpEventListener(SignUpEventListener signUpEventListener) {
		this.signUpEventListener = signUpEventListener;
	}
}

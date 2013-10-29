package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
 * This class extends JPanel class which will be responsible for the log in
 * panel of the program
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class AuthenticationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton signInBtn;
	private JButton signUpBtn;
	private JLabel userNameLbl;
	private JLabel passwordLbl;
	private JTextField userNameFld;
	private JPasswordField passwordFld;
	private AuthenticationEventListener authEventListener;
	private boolean isSignUpbtnClicked;

	/**
	 * <p>
	 * Constructor Method for the AuthenticationPanel
	 * </p>
	 **/
	public AuthenticationPanel() {
		signInBtn = new JButton("Sign In");
		signUpBtn = new JButton("Sign Up");
		userNameLbl = new JLabel("Username: ");
		passwordLbl = new JLabel("Password: ");
		userNameFld = new JTextField(10);
		passwordFld = new JPasswordField(10);
		isSignUpbtnClicked = false;
		// Create the layout of the authentication panel
		authPanelLayout();
		// Sign In button action listener. It will get the text on the
		// userNameFld and passwordFld and pass it to the AuthenticationEvent
		signInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = userNameFld.getText();
				char[] password = passwordFld.getPassword();
				AuthenticationEvent authEv = new AuthenticationEvent(this,
						username, new String(password));
				if (authEventListener != null) {
					authEventListener.authenticationEventOccured(authEv);
				}
			}
		});
		// Sign Up button action listener. Set the isSignUpBtnClicked to True.
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSignUpBtnClicked();
				AuthenticationEvent authEv = new AuthenticationEvent(this,
						isSignUpbtnClicked);
				setSignUpBtnClicked();
				if (authEventListener != null) {
					authEventListener.authenticationEventOccured(authEv);
				}
			}
		});
	}

	/**
	 * <p>
	 * This method sets up the Layout of the {@link AuthenticationPanel}.
	 * </p>
	 **/
	public void authPanelLayout() {
		JPanel logInInfopanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		int space = 150;
		// Set up Borders for the logInInfopanel
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space,
				space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Sign In");

		logInInfopanel.setBorder(BorderFactory.createCompoundBorder(
				spaceBorder, titleBorder));
		logInInfopanel.setLayout(new GridBagLayout());
		/************** UserName Label *******************/
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(0, 0, 0, 5);

		logInInfopanel.add(userNameLbl, gc);
		/************** UserName Field *******************/
		gc.gridx = 1;
		gc.gridy = 0;

		gc.weightx = 1;

		gc.anchor = GridBagConstraints.WEST;

		logInInfopanel.add(userNameFld, gc);
		/************** Password Label *******************/
		gc.gridy = 1;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.3;

		gc.anchor = GridBagConstraints.EAST;
		gc.insets = new Insets(0, 0, 0, 5);

		logInInfopanel.add(passwordLbl, gc);
		/************** Password Field *******************/
		gc.gridx = 1;
		gc.gridy = 1;

		gc.weightx = 1;

		gc.anchor = GridBagConstraints.WEST;

		logInInfopanel.add(passwordFld, gc);
		/**************** Sign In Button *****************/
		gc.gridx = 1;
		gc.gridy = 2;
		// setting up buttonPanel such as its layout and buttons
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		signInBtn.setMnemonic(KeyEvent.VK_I);
		signUpBtn.setMnemonic(KeyEvent.VK_U);
		buttonPanel.add(signInBtn);
		buttonPanel.add(signUpBtn);
		logInInfopanel.add(buttonPanel, gc);

		setLayout(new BorderLayout());
		add(logInInfopanel, BorderLayout.CENTER);
	}

	public JTextField getUserNameFld() {
		return userNameFld;
	}

	public JPasswordField getPasswordFld() {
		return passwordFld;
	}

	public void setSignUpBtnClicked() {
		isSignUpbtnClicked = true;
	}

	public boolean getSignUpBtnClicked() {
		return isSignUpbtnClicked;
	}

	public void setAuthenticationEventListener(
			AuthenticationEventListener authEventListener) {
		this.authEventListener = authEventListener;
	}
}

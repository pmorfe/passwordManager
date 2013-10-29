package gui;

import java.util.EventObject;

/**
 * <p>
 * This class extends the EventObject class
 * </p>
 * 
 * @author Pippo Morfe
 * */
public class AuthenticationEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean signUpBtnClicked;

	/**
	 * <p>
	 * This is a constructor for the AuthenticationEvent
	 * </p>
	 * 
	 * @param source
	 *            The source of the event.
	 * */
	public AuthenticationEvent(Object source) {
		super(source);
	}

	/**
	 * <p>
	 * This is a constructor for the AuthenticationEvent
	 * </p>
	 * 
	 * @param source
	 *            The source of the event.
	 * @param signUpBtnClicked
	 *            A boolean value.
	 * */
	public AuthenticationEvent(Object source, boolean signUpBtnClicked) {
		super(source);
		this.signUpBtnClicked = signUpBtnClicked;

	}

	/**
	 * <p>
	 * This is a constructor for the AuthenticationEvent
	 * </p>
	 * 
	 * @param source
	 *            The source of the event.
	 * @param newUsername
	 *            The username of the AuthenticationEvent Object.
	 * @param newPassword
	 *            The password of the AuthenticationEvent Object.
	 * */
	public AuthenticationEvent(Object source, String newUsername,
			String newPassword) {
		super(source);
		this.username = newUsername;
		this.password = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSignUpBtnClicked() {
		return signUpBtnClicked;
	}

	public void setSignUpBtnClicked(boolean signUpBtnClicked) {
		this.signUpBtnClicked = signUpBtnClicked;
	}
}

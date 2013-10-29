package gui;

import java.util.EventObject;

/**
 * <p>
 * This class is responsible for events in {@link SignUpPanel}.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class SignUpEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean cancelIsClicked;
	private String username;
	private String password;
	private String passwordConfirm;

	/**
	 * <p>
	 * Constructor Method for {@link SignUpEvent}.
	 * </p>
	 * 
	 * @param source
	 *            Source of the event.
	 **/
	public SignUpEvent(Object arg0) {
		super(arg0);
	}

	/**
	 * <p>
	 * Constructor Method for {@link SignUpEvent}.
	 * </p>
	 * 
	 * @param source
	 *            Source of the event.
	 * @param username
	 *            Username of the user.
	 * @param password
	 *            Password of the user.
	 * @param passwordConfirm
	 *            Password Confirmation.
	 **/
	public SignUpEvent(Object arg0, String username, String password,
			String passwordConfirm) {
		super(arg0);
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isCancelIsClicked() {
		return cancelIsClicked;
	}

	public void setCancelIsClicked(boolean cancelIsClicked) {
		this.cancelIsClicked = cancelIsClicked;
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

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

}

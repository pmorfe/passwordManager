package model;

/**
 * <p>
 * This class will represent the users Table found in the database.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class User {
	private String password;
	private String username;
	private int userId;

	/**
	 * <p>
	 * Constructor Method for {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            The user id of the user.
	 * @param username
	 *            The username of the user.
	 * @param password
	 *            The password of the user.
	 **/
	public User(int userId, String username, String password) {
		this.userId = userId;
		this.username = username;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

package model;

/**
 * <p>
 * This class will represent the passwordDetails Table found in the database.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class Account {
	private String name;
	private String password;
	private String username;
	private String email;
	private String question;
	private String answer;
	private String notes;
	private int accntId;

	/**
	 * <p>
	 * Constructor Method for {@link Account}.
	 * </p>
	 * 
	 * @param accntId
	 *            The account id of the account.
	 * @param name
	 *            The name of the account.
	 * @param username
	 *            The username of the account.
	 * @param password
	 *            The password of the account.
	 * @param email
	 *            The email of the account.
	 * @param question
	 *            The secret question of the account.
	 * @param answer
	 *            The answer of the secret question.
	 * @param notes
	 *            Notes related to the account.
	 **/
	public Account(int accntId, String name, String username, String password,
			String email, String question, String answer, String notes) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.accntId = accntId;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.notes = notes;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getAccntId() {
		return accntId;
	}

	public void setAccntId(int accntId) {
		this.accntId = accntId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}

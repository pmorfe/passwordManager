package gui;

import java.util.EventObject;

/**
 * <p>
 * This class is responsible for events in {@link CreateAccountPanel}
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class CreateAccountEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private String username;
	private String email;
	private String question;
	private String answer;
	private String notes;

	/**
	 * <p>
	 * Constructor Method for {@link CreateAccountEvent}
	 * </p>
	 * 
	 * @param source
	 *            Source of the event.
	 **/
	public CreateAccountEvent(Object source) {
		super(source);
	}

	/**
	 * <p>
	 * Constructor Method for {@link CreateAccountEvent}
	 * </p>
	 * 
	 * @param source
	 *            Source of the event.
	 * @param name
	 *            String name associated with this event.
	 * @param username
	 *            String username associated with this event.
	 * @param password
	 *            String password.
	 * @param email
	 *            String email.
	 * @param question
	 *            String secret question.
	 * @param answer
	 *            String answer to the question.
	 * @param notes
	 *            String notes.
	 **/
	public CreateAccountEvent(Object source, String name, String username,
			String password, String email, String question, String answer,
			String notes) {
		super(source);
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.notes = notes;
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

}

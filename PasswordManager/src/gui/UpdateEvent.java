package gui;

import java.util.EventObject;

/**
 * <p>
 * This class is responsible for events in the save button found in
 * {@link HomePanel}.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class UpdateEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String question;
	private String answer;
	private String notes;
	private String name;
	private int id;

	/**
	 * <p>
	 * Constructor Method for {@link UpdateEvent}.
	 * </p>
	 * 
	 * @param arg0
	 *            Source of the event.
	 **/
	public UpdateEvent(Object arg0) {
		super(arg0);

	}

	/**
	 * <p>
	 * Constructor Method for {@link UpdateEvent}.
	 * </p>
	 * 
	 * @param arg0
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
	public UpdateEvent(Object arg0, int id, String name, String username,
			String password, String email, String question, String answer,
			String notes) {
		super(arg0);
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.notes = notes;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

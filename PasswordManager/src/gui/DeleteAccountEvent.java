package gui;

import java.util.EventObject;
import model.Account;

/**
 * <p>
 * This class is responsible for events associated with the delete button found
 * in {@link HomePanel}.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class DeleteAccountEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private Account account;

	/**
	 * <p>
	 * Constructor Method for {@link DeleteAccountEvent}.
	 * </p>
	 * 
	 * @param source
	 *            Source of the event.
	 * @param account
	 *            The account associated with this event.
	 **/
	public DeleteAccountEvent(Object source, Account account) {
		super(source);
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}

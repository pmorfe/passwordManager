package gui;

import java.util.EventObject;

/**
 * <p>
 * This class is responsible for events regarding about the cancel button found
 * in {@link CreateAccountPanel}
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class CancelCreateAccountEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public CancelCreateAccountEvent(Object arg0) {
		super(arg0);
	}
}

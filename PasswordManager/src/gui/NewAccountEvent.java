package gui;

import java.util.EventObject;

/**
 * <p>
 * This class is responsible for events associated with the new button found in
 * {@link HomePanel}.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class NewAccountEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public NewAccountEvent(Object arg0) {
		super(arg0);
	}
}

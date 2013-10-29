package gui;

import java.util.EventListener;

/**
 * <p>
 * This class is responsible for listening to the events related to sign up
 * Panel.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public interface SignUpEventListener extends EventListener {
	public void signUpEventOccured(SignUpEvent signUpEv);
}

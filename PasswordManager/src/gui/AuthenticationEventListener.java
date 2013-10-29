package gui;

import java.util.EventListener;

/**
 * <p>
 * This class is an interface for the AuthenticationEventListener which will
 * listen to the events happening in the {@link AuthenticationPanel}
 * </p>
 * 
 * @author Pippo Morfe
 * */
public interface AuthenticationEventListener extends EventListener {
	public void authenticationEventOccured(AuthenticationEvent authEv);
}

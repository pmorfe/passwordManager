package gui;

import java.util.EventListener;

/**
 * <p>
 * This class is responsible for listening to the events related to the save
 * button found in {@link HomePanel}.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public interface UpdateEventListener extends EventListener {
	public void updateEventOccured(UpdateEvent updateEv);
}

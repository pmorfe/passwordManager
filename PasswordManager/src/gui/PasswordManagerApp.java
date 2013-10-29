package gui;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
/**
 * <p>
 * This class will be the Password Manager Main Driver
 * </p>
 * 
 * @author Pippo Morfe
 **/
public class PasswordManagerApp {
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run() {
					new MainFrame();
				}			
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}

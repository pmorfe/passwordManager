package controller;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Account;
import model.Database;

/**
 * <p>
 * This will be a controller class where Model classes and View/GUI classes will
 * communicate with each other.
 * </p>
 * 
 * @author Pippo Morfe
 * */
public class Controller {
	/**
	 * <p>
	 * This method will swap JPanels and refresh the JFrame where they were
	 * added
	 * </p>
	 * 
	 * @param frame
	 *            It is a JFrame where the two JPanels were added.
	 * @param oldPanel
	 *            The JPanel that will be replaced.
	 * @param newPanel
	 *            The new JPanel that will replace the old JPanel.
	 * */
	public void swapPanel(JFrame frame, JPanel oldPanel, JPanel newPanel) {
		frame.remove(oldPanel);
		frame.add(newPanel, BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}

	/**
	 * <p>
	 * This method will try to connect to the database
	 * </p>
	 * 
	 * @param db
	 *            The database trying to connect.
	 * */
	public void dbConnect(Database db) throws SQLException {
		if (!db.dbConnectForExisting()) {
			db.dbConnect();
			db.createTablePD();
			db.dbCommit();
		}
	}

	/**
	 * <p>
	 * This method will return the User Id of a particular User
	 * </p>
	 * 
	 * @param db
	 *            The database that will be queried.
	 * @param username
	 *            The username of the user.
	 * @param password
	 *            The password of the user.
	 * @return The user id of the user that owns the username and password
	 * */
	public int getUserId(Database db, String username, String password) {
		return db.getUserIdByUsername(username, password);
	}

	/**
	 * <p>
	 * This method will determine if a user is allowed to access the program
	 * </p>
	 * 
	 * @param db
	 *            The database being queried.
	 * @param username
	 *            The username of a user.
	 * @param password
	 *            The password of a user.
	 * @return Will return a boolean value of True or False.
	 * */
	public boolean userAllowed(Database db, String username, String password) {
		if (db.userExist(username, password)) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * This method will insert the newly created user to the database
	 * </p>
	 * 
	 * @param db
	 *            The database where the data will be inserted.
	 * @param username
	 *            The username of the user.
	 * @param password
	 *            The password of the user.
	 * */
	public void signUpUser(Database db, String username, String password) {
		db.insertUser(username, password);
	}

	/**
	 * <p>
	 * This method will create the account(details related to the Password)
	 * </p>
	 * 
	 * @param db
	 *            The database where the data will be inserted.
	 * @param userId
	 *            The user id of the user that owns the account being created.
	 * @param name
	 *            The name of the account.
	 * @param username
	 *            The username of the account.
	 * @param password
	 *            The password of the account.
	 * @param email
	 *            The email related to the account.
	 * @param question
	 *            The secret question related to the account
	 * @param answer
	 *            The answer to the secret question.
	 * @param notes
	 *            Notes related to the account.
	 * */
	public void createAccount(Database db, int userId, String name,
			String username, String password, String email, String question,
			String answer, String notes) {
		db.insertDataToPD(userId, name, username, password, email, question,
				answer, notes);
	}

	/**
	 * <p>
	 * This method will return a List<Account> containing list of accounts
	 * related to the user id
	 * </p>
	 * 
	 * @param db
	 *            The database that will be queried.
	 * @param id
	 *            The user id of the user.
	 * @return List of accounts
	 * */
	public List<Account> getAccount(Database db, int id) {
		return db.queryTablePDByUserId(id);
	}

	/**
	 * <p>
	 * This method will return an account that owns the account id
	 * </p>
	 * 
	 * @param accntList
	 *            List of accounts to be searched.
	 * @param id
	 *            account id of the account needed to be found.
	 * @return accnt The account that owns the id
	 * */
	public Account getAccountById(List<Account> accntList, int id) {
		Account accnt = null;

		for (int i = 0; accntList.size() > i; i++) {
			accnt = accntList.get(i);
			if (accnt.getAccntId() == id) {
				return accnt;
			}
		}
		return accnt;
	}

	/**
	 * <p>
	 * This method will delete the account which owns the accountId
	 * </p>
	 * 
	 * @param db
	 *            The database which will be queried.
	 * @param accountId
	 *            The account id of the account to be deleted.
	 * */
	public void deleteAccount(Database db, int accountId) {
		db.removeDataToPD(accountId);
	}

	/**
	 * <p>
	 * This method will delete ALL accounts related to the userId
	 * </p>
	 * 
	 * @param db
	 *            The database which will be queried.
	 * @param userId
	 *            The user id related to the accounts that will be deleted.
	 * */
	public void deleteAllAccountByUserId(Database db, int userId) {
		db.removeDataToPDByUserId(userId);
	}

	/**
	 * <p>
	 * This method will delete user which owns the userId
	 * </p>
	 * 
	 * @param db
	 *            The Database which will be queried.
	 * @param userId
	 *            The user id of the user that will be deleted.
	 * */
	public void deleteUserByUserId(Database db, int userId) {
		db.removeUsersById(userId);
	}

	/**
	 * <p>
	 * This method will return the account found in the accntList based on the
	 * index
	 * </p>
	 * 
	 * @param accntList
	 *            The list of accounts to be searched.
	 * @param index
	 *            The index where to get the account in the accntList.
	 * */
	public Account getSelectedAccount(List<Account> accntList, int index) {
		Account accnt = accntList.get(index);
		return accnt;
	}
}

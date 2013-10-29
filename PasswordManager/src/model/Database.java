package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * This class will be responsible for querying the database and connections.
 * </p>
 * 
 * @author Pippo Morfe
 * 
 **/
public class Database {
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String dbName;
	private Connection conn = null;

	/**
	 * <p>
	 * Constructor method for the {@link Database}.
	 * </p>
	 * 
	 * @param conn
	 *            The Connection of the database.
	 * @param dbName
	 *            The database name.
	 * 
	 **/
	public Database(Connection conn, String dbName) throws SQLException {
		this.conn = conn;
		this.dbName = dbName;
	}

	/**
	 * <p>
	 * This method will create a database when it does not exit. and establishes
	 * a connection to it.
	 * </p>
	 * 
	 * @return Returns True if establishes a connection otherwise returns False
	 * 
	 **/
	public boolean dbConnect() {
		try {
			conn = DriverManager.getConnection("jdbc:derby:" + dbName
					+ ";create=true");
			return true;
		} catch (SQLException e) {
			System.out.println("Did not Connect to the database");
			return false;
		}
	}

	/**
	 * <p>
	 * This method will establishes a connection to the existing database.
	 * </p>
	 * 
	 * @return Returns True if establishes a connection otherwise returns False
	 **/
	public boolean dbConnectForExisting() {
		try {
			conn = DriverManager.getConnection("jdbc:derby:" + dbName);
			System.out.println("Connected Properly Existing");
			return true;
		} catch (SQLException e) {
			System.out.println("did not Connect Properly Existing");
			return false;
		}
	}

	/**
	 * <p>
	 * This method will commit the changes made to the database.
	 * </p>
	 **/
	public void dbCommit() throws SQLException {
		conn.commit();
	}

	/**
	 * <p>
	 * This method will disconnect to the database.
	 * </p>
	 **/
	public void dbDisconnect() throws SQLException {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException sqle) {

		}
		System.out.println("Closed Properly");
	}

	/**
	 * <p>
	 * This method will shutdown the database.
	 * </p>
	 **/
	public void dbShutDown() throws SQLException {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException se) {
			if (!se.getSQLState().equals("XJ015")) {
				throw se;
			}
		}
		System.out.println("Shutdown Properly");
	}

	/**
	 * <p>
	 * This method will load the driver needed for the database connection.
	 * </p>
	 **/
	public void loadDriver() {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("\nUnable to load the JDBC driver " + driver);
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println("\nUnable to instantiate the JDBC driver "
					+ driver);
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println("\nNot allowed to access the JDBC driver "
					+ driver);
			iae.printStackTrace(System.err);
		}
	}

	/**
	 * <p>
	 * This method will create the tables needed for the program in the
	 * database.
	 * </p>
	 **/
	public void createTablePD() {
		Statement s = null;

		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * Creating a statement object that we can use for running various SQL
		 * statements commands against the database.
		 */
		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("failed to create Statements");
		}
		// sql statements in creating passwordDetails and user tables in the
		// database
		try {
			s.execute("create table users(user_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), username varchar(20), password varchar(20))");
			s.execute("create table passwordDetails(user_id INTEGER NOT NULL ,accnt_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) , name varchar(50), username varchar(50), password varchar(20),email varchar(50),question varchar(50),answer varchar(50),notes varchar(140))");
		} catch (SQLException e) {
			System.out.println("Failed to create table");
		}
	}

	/**
	 * <p>
	 * This method will insert new user into the database.
	 * </p>
	 * 
	 * @param username
	 *            The username of the user.
	 * @param password
	 *            The password of the user.
	 **/
	public void insertUser(String username, String password) {
		PreparedStatement psInsert = null;
		// sql script to insert the user.
		try {
			psInsert = conn
					.prepareStatement("insert into users(username,password) values (?,?)");
		} catch (SQLException e) {
			System.out.println("Failed to initialize psInsert");
		}
		// setting up the appopriate values to be inserted
		try {
			psInsert.setString(1, username);
			psInsert.setString(2, password);
		} catch (SQLException e) {
			System.out.println("Failed to insert data");
		}
		// execute the sql script
		try {
			psInsert.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to update");
		}
	}

	/**
	 * <p>
	 * This method will insert new account into the database.
	 * </p>
	 * 
	 * @param id
	 *            The account id of the account.
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
	 **/
	public void insertDataToPD(int id, String name, String username,
			String password, String email, String question, String answer,
			String notes) {
		PreparedStatement psInsert = null;

		try {
			psInsert = conn
					.prepareStatement("insert into passwordDetails(user_id,name,username,password,email,question,answer,notes) values (?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			System.out.println("Failed to initialize psInsert");
		}

		try {
			psInsert.setInt(1, id);
			psInsert.setString(2, name);
			psInsert.setString(3, username);
			psInsert.setString(4, password);
			psInsert.setString(5, email);
			psInsert.setString(6, question);
			psInsert.setString(7, answer);
			psInsert.setString(8, notes);
		} catch (SQLException e) {
			System.out.println("Failed to insert data");
		}

		try {
			psInsert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <p>
	 * This method will remove account from the database. that owns the id
	 * </p>
	 * 
	 * @param id
	 *            The account id of the account to be deleted.
	 **/
	public void removeDataToPD(int id) {
		PreparedStatement psRemove = null;

		try {
			psRemove = conn
					.prepareStatement("delete from passwordDetails where accnt_id = ? ");
		} catch (SQLException e) {
			System.out.println("Failed to initialize psInsert");
		}

		try {
			psRemove.setInt(1, id);

		} catch (SQLException e) {
			System.out.println("Failed to insert data");
		}

		try {
			psRemove.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to update");
		}
	}

	/**
	 * <p>
	 * This method will remove user from the database that owns the userId
	 * </p>
	 * 
	 * @param userId
	 *            The user id of the user to be deleted.
	 **/
	public void removeUsersById(int userId) {
		PreparedStatement psRemove = null;

		try {
			psRemove = conn
					.prepareStatement("delete from users where user_id = ? ");
		} catch (SQLException e) {
			System.out.println("Failed to initialize psInsert");
		}

		try {
			psRemove.setInt(1, userId);

		} catch (SQLException e) {
			System.out.println("Failed to insert data");
		}

		try {
			psRemove.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to update");
		}

	}

	/**
	 * <p>
	 * This method will remove accounts from the database that are related to
	 * the userId.
	 * </p>
	 * 
	 * @param userId
	 *            The user id that the accounts have to be deleted.
	 **/
	public void removeDataToPDByUserId(int userId) {
		PreparedStatement psRemove = null;

		try {
			psRemove = conn
					.prepareStatement("delete from passwordDetails where user_id = ? ");
		} catch (SQLException e) {
			System.out.println("Failed to initialize psInsert");
		}

		try {
			psRemove.setInt(1, userId);

		} catch (SQLException e) {
			System.out.println("Failed to insert data");
		}

		try {
			psRemove.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to update");
		}

	}

	/**
	 * <p>
	 * This method will update the existing account from the database that owns
	 * the key.
	 * </p>
	 * 
	 * @param key
	 *            The account id of the account.
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
	 **/
	public void updateDataToPD(int key, String name, String username,
			String password, String email, String question, String answer,
			String notes) {
		PreparedStatement psUpdate = null;

		try {
			psUpdate = conn
					.prepareStatement("update passwordDetails set name=?, username=?,password=?,email=?,question=?,answer=?,notes=? where accnt_id=?");
		} catch (SQLException e) {
			System.out.println("Failed to initialize psUpdate");
		}

		try {

			psUpdate.setString(1, name);
			psUpdate.setString(2, username);
			psUpdate.setString(3, password);
			psUpdate.setString(4, email);
			psUpdate.setString(5, question);
			psUpdate.setString(6, answer);
			psUpdate.setString(7, notes);
			psUpdate.setInt(8, key);
		} catch (SQLException e) {
			System.out.println("Failed to update data");
		}

		try {
			psUpdate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to update");
		}

	}

	/**
	 * <p>
	 * This method will query the database which accounts related with the
	 * user_id.
	 * </p>
	 * 
	 * @param user_id
	 *            The user id that the accounts have.
	 **/
	public List<Account> queryTablePDByUserId(int user_id) {
		ResultSet rs = null;
		PreparedStatement selectId = null;
		List<Account> accounts = new LinkedList<Account>();
		Account account;

		try {
			selectId = conn
					.prepareStatement("SELECT user_id,accnt_id, name, username,password,email,question,answer,notes FROM passwordDetails where user_id=?");
		} catch (SQLException e) {
			System.out.println("failed to create Statements");
		}

		try {
			selectId.setInt(1, user_id);
			rs = selectId.executeQuery();
		} catch (SQLException e) {
			System.out.println("failed to create ResultSet userId");
		}

		try {
			while (rs.next()) {
				account = new Account(rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9));
				accounts.add(account);
			}

		} catch (SQLException e) {
			System.out.println("Failed to query");
		}
		return accounts;
	}

	/**
	 * <p>
	 * This method will drop the passwordDetails table from the database.
	 * </p>
	 * 
	 **/
	public void dropTablePD() {
		Statement s = null;
		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("failed to create Statements");
		}

		try {
			s.execute("drop table passwordDetails");
		} catch (SQLException e) {
			System.out.println("Failed to Drop Table PD");
		}
	}

	/**
	 * <p>
	 * This method will drop the users table from the database.
	 * </p>
	 * 
	 **/
	public void dropTableUsers() {
		Statement s = null;
		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("failed to create Statements");
		}

		try {
			s.execute("drop table users");
		} catch (SQLException e) {
			System.out.println("Failed to Drop Table Users");
		}
	}

	/**
	 * <p>
	 * This method will return the user id that has the username and password.
	 * </p>
	 * 
	 * @param username
	 *            The username of the user.
	 * @param password
	 *            The password of the user.
	 * @return id The user id of the user.
	 **/
	public int getUserIdByUsername(String username, String password) {
		ResultSet rs = null;
		PreparedStatement getUserId = null;
		int id = 0;

		try {
			getUserId = conn
					.prepareStatement("SELECT user_id FROM users where username=? and password=?");
		} catch (SQLException e) {
			System.out.println("failed to create Statements");
		}

		try {
			getUserId.setString(1, username);
			getUserId.setString(2, password);
			rs = getUserId.executeQuery();
		} catch (SQLException e) {
			System.out.println("failed to create ResultSet");
		}

		try {
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Failed to query");
		}
		return id;
	}

	/**
	 * <p>
	 * This method will check if the user exists in the database.
	 * </p>
	 * 
	 * @param username
	 *            The username of the user.
	 * @param password
	 *            The password of the user.
	 * 
	 * @return A boolean value of True if user exists otherwise False if user
	 *         does not exist.
	 * 
	 **/
	public boolean userExist(String username, String password) {
		ResultSet rs = null;
		Statement s = null;

		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("failed to create Statements");
		}

		try {
			rs = s.executeQuery("SELECT  username,password FROM users");
		} catch (SQLException e) {
			System.out.println("failed to create ResultSet");
		}

		try {
			while (rs.next()) {
				if ((rs.getString(1).equals(username))
						&& (rs.getString(2).equals(password))) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Failed to query");
		}
		return false;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}

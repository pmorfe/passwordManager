package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Account;

/**
 * <p>
 * This class extends the AbstractTableModel
 * </p>
 * 
 * @author Pippo Morfe
 * */
public class AccountTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Account> account;
	private String[] colNames = { "Account Name" };

	/**
	 * <p>
	 * This class returns a string based on the column
	 * </p>
	 * 
	 * @param column
	 *            An integer which specifies the string to be return
	 * @return A string that represents the heading of the column
	 * */
	@Override
	public String getColumnName(int column) {

		return colNames[column];
	}

	/**
	 * <p>
	 * This method will set the List<Account> to be used
	 * </p>
	 * */
	public void setData(List<Account> account) {
		this.account = account;
	}

	/**
	 * <p>
	 * This method will return List<Account>
	 * </p>
	 * 
	 * @return account List<Account>
	 * */
	public List<Account> getAccountList() {
		return account;
	}

	/**
	 * <p>
	 * This method will return the number of columns
	 * </p>
	 * 
	 * @return Number of columns going to be in the table.
	 * */
	@Override
	public int getColumnCount() {
		return 1;
	}

	/**
	 * <p>
	 * This method will return the number of rows
	 * </p>
	 * 
	 * @return Number of rows going to be in the table.
	 * */
	@Override
	public int getRowCount() {
		return account.size();
	}

	/**
	 * <p>
	 * This method will return an Object specified in the parameters
	 * </p>
	 * 
	 * @param row
	 *            The row number of that Object.
	 * @param col
	 *            The column number of that Object.
	 * @return Returns the Object specified.
	 * */
	@Override
	public Object getValueAt(int row, int col) {
		Account accnt = account.get(row);

		switch (col) {
		case 0:
			return accnt.getName();
		}
		return null;
	}
}

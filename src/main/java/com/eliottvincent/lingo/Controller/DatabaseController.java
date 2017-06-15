package com.eliottvincent.lingo.Controller;

/**
 * Created by eliottvincent on 08/06/2017.
 */
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

/**
 * Created by eliottvincent on 06/06/2017.
 */
public class DatabaseController {

	private static Connection connection = null;

	private static DatabaseController instance;

	DatabaseController() {

		loadDriver();
		connection = getConnection(connection);
	}

	static DatabaseController getInstance(){

		if(instance == null) {
			instance = new DatabaseController();
		}
		return instance;
	}

	private static void loadDriver() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConnection(Connection connection) {

		if (connection == null) {

			try {
				connection = DriverManager.getConnection("jdbc:sqlite:lingo.db.sqlite3");
			}
			catch (SQLException e) {

				System.out.println("SQLException: "  + e.getMessage());
				System.out.println("SQLState: "  + e.getSQLState());
				System.out.println("VendorError: "  + e.getErrorCode());
			}
		}
		return connection;
	}

	/**
	 *
	 * @param query
	 * @return
	 */
	List<Map<String,Object>> executeSelectQuery(String query) {

		System.out.println("Executing SELECT query");
		List<Map<String, Object>> statementsList = new ArrayList<>();

		System.out.println(query);

		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet rs = statement.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (rs.next()) {

				Map<String, Object> statementMap = new HashMap<>();

				for (int i = 1; i <= columnsNumber; i++) {

					int columnType = rsmd.getColumnType(i);

					switch (columnType) {
						case Types.VARCHAR:
							statementMap.put(rsmd.getColumnLabel(i), rs.getString(i));
							break;
						case Types.NULL:
							statementMap.put(rsmd.getColumnLabel(i), null);
							break;
						case Types.CHAR:
							statementMap.put(rsmd.getColumnLabel(i), rs.getString(i));
							break;
						case Types.TIMESTAMP:
							statementMap.put(rsmd.getColumnLabel(i), rs.getTimestamp(i));
							break;
						case Types.DOUBLE:
							statementMap.put(rsmd.getColumnLabel(i), rs.getDouble(i));
							break;
						case Types.INTEGER:
							statementMap.put(rsmd.getColumnLabel(i), rs.getString(i));
							break;
						case Types.SMALLINT:
							statementMap.put(rsmd.getColumnLabel(i), rs.getInt(i));
							break;
						case Types.DECIMAL:
							statementMap.put(rsmd.getColumnLabel(i), rs.getBigDecimal(i));
							break;
						default:
							statementMap.put(rsmd.getColumnLabel(i), rs.getString(i));
							break;
					}
				}

				statementsList.add(statementMap);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return statementsList;
	}

	Integer executeInsertQuery(String query) {

		Integer insertId = 0;

		PreparedStatement pstmt = null;

		try {
			pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();

			ResultSet keys = pstmt.getGeneratedKeys();
			keys.next();
			insertId = keys.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertId;

	}

	public void excuteInsertQuery(String query) {

	}

	public void executeUpdateQuery(String query) {

	}

}

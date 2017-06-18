package com.eliottvincent.lingo.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>DatabaseController is the class responsible for the actions performed to the database.</b>
 * <p>DatabaseController is a Singleton, meaning that there will be only one instance of the class during the lifecycle of the application.</p>
 *
 * @author eliottvincent
 */
class DatabaseController {


	//================================================================================
	// Properties
	//================================================================================

	private static Connection connection = null;

	private static DatabaseController instance;


	//================================================================================
	// Constructor and initialization
	//================================================================================

	/**
	 * The default constructor for a DatabaseController
	 */
	DatabaseController() {

		loadDriver();
		connection = getConnection(connection);
	}

	/**
	 *
	 * @return the instance of DatabaseController
	 */
	static DatabaseController getInstance(){

		// if there is no instance of DatabaseController
		if(instance == null) {

			instance = new DatabaseController();
		}
		return instance;
	}

	/**
	 * loadDriver() is the method responsible for loading the JDBC driver.
	 */
	private static void loadDriver() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getConnection() is the method responsible for initializing the Connection object.
	 *
	 * @param connection the connection to initialize
	 * @return
	 */
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
	 * executeSelectQuery() is the method responsible for execute "SELECT" queries to the database.
	 *
	 * @param query the query to execute.
	 * @return A list of Maps, each Map corresponding to a statement retrieved in the database.
	 */
	List<Map<String,Object>> executeSelectQuery(String query) {

		// preparing the list for the retrieved statements in the database
		List<Map<String, Object>> statementsList = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			// the ResultSet represents a table of data retrieved in the database
			ResultSet rs = statement.executeQuery(query);

			// the ResultSetMetaData represents all the metadata of the ResultSet
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

				// adding the Map to the statementsList
				statementsList.add(statementMap);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		// in the end, we return the populated statementsList
		return statementsList;
	}

	/**
	 * executeInsertQuery() is the method responsible for execute "INSERT" queries to the database.
	 *
	 * @param query the query to execute.
	 * @return the id of the inserted statement.
	 */
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

}

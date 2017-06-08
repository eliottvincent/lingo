package com.eliottvincent.lingo.Controller;

/**
 * Created by eliottvincent on 08/06/2017.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eliottvincent on 06/06/2017.
 */
public class DatabaseController {

	private static Connection connection = null;

	public DatabaseController() {

		loadDriver();
		connection = getConnection(connection);
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

	public void executeQuery(String query) {

		System.out.println(query);
		try {

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			if (query.startsWith("select") || query.startsWith("SELECT")) {

				ResultSet rs = statement.executeQuery(query);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();

				for (int i = 1; i <= columnsNumber; i++) {
					System.out.println(rsmd.getColumnLabel(i) + "\t");
				}
				System.out.println("\n");

				while (rs.next()) {

					for (int i = 1; i <= columnsNumber; i++) {

						int columnType = rsmd.getColumnType(i);

						switch (columnType) {
							case Types.VARCHAR:
								System.out.println(rs.getString(i) + "\t");
								break;
							case Types.NULL:
								System.out.println("null");
								break;
							case Types.CHAR:
								System.out.println(rs.getString(i) + "\t");
								break;
							case Types.TIMESTAMP:
								System.out.println(rs.getTimestamp(i) + "\t");
								break;
							case Types.DOUBLE:
								System.out.println(rs.getDouble(i) + "\t");
								break;
							case Types.INTEGER:
								System.out.println(rs.getString(i) + "\t");
								break;
							case Types.SMALLINT:
								System.out.println(rs.getInt(i) + "\t");
								break;
							case Types.DECIMAL:
								System.out.println(rs.getBigDecimal(i) + "\t");
								break;
							default:
								System.out.println(rs.getString(i) + "\t");
								break;
						}
					}
					System.out.println("\n");
				}

			}
			else {

				try {
					int numberOfColumnsUpdate = statement.executeUpdate(query);
					System.out.println(numberOfColumnsUpdate + " columns updated\n");
				}
				catch (SQLException e) {
					System.out.println(e.getMessage() + "\n");
				}


			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List executeSelectQuery(String query) {

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
							statementMap.put(rsmd.getColumnLabel(i), "null");
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

	Integer executeCreateQuery(String query) {

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

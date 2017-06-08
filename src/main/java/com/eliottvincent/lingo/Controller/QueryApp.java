package com.eliottvincent.lingo.Controller;

/**
 * Created by eliottvincent on 08/06/2017.
 */
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by eliottvincent on 06/06/2017.
 */
public class QueryApp {

	private static Connection connection = null;

	public QueryApp() {

		loadDriver();
		this.connection = getConnection(connection);
	}

	private static void loadDriver() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection(Connection connection) {

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

			statement = connection.createStatement();
			String sql = "CREATE TABLE Users " +
				"(ID INT PRIMARY KEY     NOT NULL," +
				" NAME           TEXT    NOT NULL, " +
				" AGE            INT     NOT NULL, " +
				" LANGUAGE        CHAR(50))";
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
			/*if (query.startsWith("select") || query.startsWith("SELECT")) {

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


			}*/
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

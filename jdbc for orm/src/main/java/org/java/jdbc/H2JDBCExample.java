package org.java.jdbc;

import org.h2.tools.Server;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2JDBCExample {
	// JDBC URL for H2 in-memory database
	private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static void main(String[] args) throws SQLException {
		h2UI();
		resultSet();
		rowSet();
	}

	private static void resultSet() {
		// SQL statements
		String createTableSQL = "CREATE TABLE employees (" +
				"id INT PRIMARY KEY, " +
				"name VARCHAR(100), " +
				"position VARCHAR(100))";
		String insertSQL = "INSERT INTO employees (id, name, position) VALUES (?, ?, ?)";
		String selectSQL = "SELECT * FROM employees";

		// Load H2 JDBC Driver
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("H2 JDBC Driver not found.");
			e.printStackTrace();
			return;
		}

		// Establish connection and execute SQL statements
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
			// Create table
			try (Statement statement = connection.createStatement()) {
				statement.execute(createTableSQL);
				System.out.println("Table 'employees' created successfully.");
			}

			// Insert data
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
				preparedStatement.setInt(1, 1);
				preparedStatement.setString(2, "Alice");
				preparedStatement.setString(3, "Developer");
				preparedStatement.executeUpdate();

				preparedStatement.setInt(1, 2);
				preparedStatement.setString(2, "Bob");
				preparedStatement.setString(3, "Designer");
				preparedStatement.executeUpdate();

				System.out.println("Data inserted successfully.");
			}

			// Query data
			try (Statement statement = connection.createStatement();
			     ResultSet resultSet = statement.executeQuery(selectSQL)) {
				System.out.println("Employee Records:");
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String position = resultSet.getString("position");
					System.out.printf("ID: %d, Name: %s, Position: %s%n", id, name, position);
				}
			}
		} catch (SQLException e) {
			System.err.println("Database operation failed.");
			e.printStackTrace();
		}
	}

	private static void rowSet() {
		try {
			// Load H2 driver
			Class.forName("org.h2.Driver");

			// Establish connection
			Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");

			// Create table using Statement
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE Student (RollNo INT PRIMARY KEY, Name VARCHAR(100), Marks INT)");

			// Insert sample data
			stmt.executeUpdate("INSERT INTO Student VALUES (1, 'Alice', 85)");
			stmt.executeUpdate("INSERT INTO Student VALUES (2, 'Bob', 90)");

			// Create JdbcRowSet
			JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
			rowSet.setUrl("jdbc:h2:mem:testdb");
			rowSet.setUsername("sa");
			rowSet.setPassword("");

			// Set and execute SELECT command
			rowSet.setCommand("SELECT * FROM Student");
			rowSet.execute();

			// Process the results
			while (rowSet.next()) {
				System.out.println("RollNo: " + rowSet.getInt("RollNo"));
				System.out.println("Name: " + rowSet.getString("Name"));
				System.out.println("Marks: " + rowSet.getInt("Marks"));
				System.out.println("-----");
			}

			// Close resources
			rowSet.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void h2UI() throws SQLException {
		// Start the H2 Console on port 8082
		Server.createWebServer("-webPort", "8082", "-webAllowOthers").start();
		System.out.println("H2 Console started at http://localhost:8082");
	}
}

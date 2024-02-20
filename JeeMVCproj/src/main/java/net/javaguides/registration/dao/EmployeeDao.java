package net.javaguides.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.registration.model.Employee;

public class EmployeeDao {
	

    public int registerEmployee(Employee employee) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO employee" +
            "  (id, first_name, last_name, username, password, address, contact) VALUES " +
            " (?, ?, ?, ?, ?,?,?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getUsername());
            preparedStatement.setString(5, employee.getPassword());
            preparedStatement.setString(6, employee.getAddress());
            preparedStatement.setString(7, employee.getContact());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        System.out.println("Insertion ok");
        return result;
        
    }

    // Méthode pour mettre à jour les informations d'un employé

    public boolean updateEmployee(Employee employee) throws ClassNotFoundException {
        boolean rowUpdated = false;

        // Charger le pilote JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "")) {
            String UPDATE_EMPLOYEE = "UPDATE employee SET first_name=?, last_name=?, username=?, password=?, address=?, contact=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE)) {
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setString(3, employee.getUsername());
                preparedStatement.setString(4, employee.getPassword());
                preparedStatement.setString(5, employee.getAddress());
                preparedStatement.setString(6, employee.getContact());
                preparedStatement.setInt(7, employee.getId());

                rowUpdated = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    // Méthode pour supprimer un employé
    public boolean deleteEmployee(int id) throws ClassNotFoundException {
        boolean rowDeleted = false;

        // Charger le pilote JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "")) {
            String DELETE_EMPLOYEE = "DELETE FROM employee WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE)) {
                preparedStatement.setInt(1, id);

                rowDeleted = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }
    
    
 // Méthode pour récupérer tous les employés de la base de données
    public List<Employee> getAllEmployees() throws ClassNotFoundException {
        List<Employee> employees = new ArrayList<>();

        // Charger le pilote JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "")) {
            String SELECT_ALL_EMPLOYEES = "SELECT * FROM employee";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String address = resultSet.getString("address");
                    String contact = resultSet.getString("contact");

                    Employee employee = new Employee();
                    employee.setId(id);
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);
                    employee.setUsername(username);
                    employee.setPassword(password);
                    employee.setContact(contact);
                    employee.setAddress(address);

                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employees;
    }

    public Employee getEmployeeById(int employeeId) throws ClassNotFoundException {
        Employee employee = null;

        // Charger le pilote JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "")) {
            String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
                preparedStatement.setInt(1, employeeId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String address = resultSet.getString("address");
                    String contact = resultSet.getString("contact");

                    employee = new Employee();
                    employee.setId(id);
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);
                    employee.setUsername(username);
                    employee.setPassword(password);
                    employee.setContact(contact);
                    employee.setAddress(address);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employee;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
package car_rental_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerDetails {
    private static Connection connection;
    private static Scanner scanner;

    public CustomerDetails(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner; // Fixed the assignment of the scanner
    }

    public void showCustomerDetails() {
        try {
            String query = "SELECT * FROM customer"; // Corrected the table name
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("First Name\t\tLast Name\t\tEmail Id\t\tPhone Number\t\tAadhar Number");


            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("first_name") + "\t\t\t" +
                                resultSet.getString("last_name") + "\t\t\t" +
                                resultSet.getString("email") + "\t\t\t" +
                                resultSet.getString("phone_no") + "\t\t\t" +
                                resultSet.getString("aadhar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching customer details: " + e.getMessage());
        }
    }

    public static void addCustomer() {
        try {
            System.out.print("Enter First Name: ");
            String first_name = scanner.next();
            System.out.print("Enter Last Name: ");
            String last_name = scanner.nextLine();
            System.out.println("enter email Id:");
            String email=scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phone_no = scanner.nextLine();
            System.out.print("Enter Aadhar Number: ");
            String aadhar = scanner.nextLine();

            String query = "INSERT INTO customer (first_name, last_name, email_id, phone_no, aadhar) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone_no);
            preparedStatement.setString(5, aadhar);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer registration completed successfully");
            } else {
                System.out.println("Registration failed");
            }
        } catch (SQLException | java.util.InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Error in customer registration. Please check your input and try again.");
        }
    }
}

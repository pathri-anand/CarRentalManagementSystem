package car_rental_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CarDetails {
    private Connection connection;
    private Scanner scanner;

    public CarDetails(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addCar() {
        System.out.print("Enter Car Number: ");
        String carNumber = scanner.nextLine();
        System.out.print("Enter Car Model: ");
        String carModel = scanner.nextLine();
        System.out.print("Enter Price per Day: ");
        double pricePerDay = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        String isAvailable = "Available";

        try {
            String query = "INSERT INTO car_details(car_number, car_model, price_per_day, is_available) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, carNumber);
            preparedStatement.setString(2, carModel);
            preparedStatement.setDouble(3, pricePerDay);
            preparedStatement.setString(4, isAvailable);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Car details added successfully");
            } else {
                System.out.println("Process FAILED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("car Exist whith enered nuber");
        }
    }

    public void viewCarDetails(String query) {
        try {
            //String query = "SELECT * FROM car_details WHERE is_available = 'Available'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Print column headers
            System.out.println("car_Number\tcar_Model\tprice_per_day\tis_Available");

            while (resultSet.next()) {
                // Print each row
                System.out.println(
                        resultSet.getString("car_number") + "\t\t\t" +
                                resultSet.getString("car_model") + "\t\t\t" +
                                resultSet.getDouble("price_per_day") + "\t\t\t" +
                                resultSet.getString("is_available"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching available car details: " + e.getMessage());
        }
    }

    public void carRent() {
        String query1="SELECT * FROM car_details WHERE is_available = 'Available'";
        viewCarDetails(query1);
        System.out.print("Select the car: ");
        String carNum = scanner.nextLine();
        String isAvailable = "Not Available";

        try {
            String query = "UPDATE car_details SET is_available = ? WHERE car_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, isAvailable);
            preparedStatement.setString(2, carNum);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Car rented successfully.");
            } else {
                System.out.println("Failed to rent the car. Car may not be available or car number is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error renting the car.");
        }
    }

    public void returnCar() {
        System.out.println("Enter car number: ");
        String carNumber = scanner.nextLine();
        String isAvailable = "Available";

        try {
            String query = "UPDATE car_details SET is_available=? WHERE car_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, isAvailable);
            preparedStatement.setString(2, carNumber);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}

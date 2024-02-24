package car_rental_management;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class CarManagementSystem {
    static final String url = "jdbc:mysql://localhost:3306/carmanagementsystem";
    static final String username = "root";
    static final String password = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            CarDetails carDetails = new CarDetails(connection, scanner);
            CustomerDetails customerDetails = new CustomerDetails(connection, scanner);

            System.out.println("--Select user type--");
            System.out.println("1. Admin\n2. Customer");
            int userType = scanner.nextInt();

            if (userType == 1) {
                System.out.print("Enter username: ");
                String enteredUsername = scanner.next();
                System.out.print("Enter password: ");
                String enteredPassword = scanner.next();
                if (authenticateUser(enteredUsername, enteredPassword)) {


                    while (true) {
                        System.out.println("--CAR MANAGEMENT SYSTEM (ADMIN)--");
                        System.out.println("1. Add Car\n2. View Car Details\n3. Customer Details\n4. Exit");
                        System.out.print("Enter your Choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();
                        String query="SELECT * FROM car_details";
                        switch (choice) {
                            case 1:
                                carDetails.addCar();
                                break;
                            case 2:
                                carDetails.viewCarDetails(query);
                                break;
                            case 3:
                                customerDetails.showCustomerDetails();
                                break;
                            case 4:
                                connection.close();
                                System.exit(0);
                            default:
                                System.out.println("Enter Valid Option:");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password for Admin. Exiting.");
                    System.exit(0);
                }
            } else if (userType == 2) {
                //CarDetails carDetails = new CarDetails(connection, scanner);
                while (true) {
                    System.out.println("--CAR MANAGEMENT SYSTEM (USER)--");
                    System.out.println("1.add Customer Details\n2. Rent Car\n3. Return Car\n4. Exit");
                    System.out.print("Enter your Choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    switch (choice) {
                        case 1:
                            CustomerDetails.addCustomer();
                        case 2:
                            carDetails.carRent();
                            break;
                        case 3:
                            carDetails.returnCar();
                            break;
                        case 4:
                            System.exit(0);
                            connection.close();
                        default:
                            System.out.println("Enter Valid Option:");
                    }
                }
            } else {
                System.out.println("Invalid user type. Exiting.");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }

    private static boolean authenticateUser(String username, String password) {
        return username.equals("anand_18") && password.equals("anand_18");
    }
}

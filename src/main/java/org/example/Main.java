import java.sql.*;
import java.util.Scanner;


public class Main {
    // Initialize the
    private final String url = "jdbc:postgresql://localhost:5432/Assignment3";
    private final String user = "postgres";
    private final String password = "NoodleKoala3";

    // Function for returning all the information in students
    public void getAllStudents() {
        try {
            // Connecting to Postgre
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            // SQL statement to select all from students
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM students");

            // Creating and returning the Resultset
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                System.out.print(resultSet.getInt("student_id") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.println(resultSet.getDate("enrollment_date"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Function to add a student to students
    public void addStudent(String first_name, String last_name, String email, String enrollment_date) {
        // Writing the SQL statement with ? for prepared statement
        String SQL = "INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             // Using prepared statements to execute the SQL
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollment_date));
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    // Update Students name based on student_id
    public void updateStudentEmail(int student_id, String newEmail) {
        // SQL statement for Updating email in prepared statement format
        String SQL = "UPDATE students SET email=? WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             // Executing the prepared statement
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
            System.out.println("Student email updated!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete student based on student_id
    public void deleteStudent(int student_id) {
        // Creating SQL statement for deleting a student
        String SQL = "DELETE FROM students WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             // executing the prepared statement
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    // Main function for accessing the functions
    public static void main(String[] args) {
        // Creating an instance of main and a scanner
        Main db = new Main();
        Scanner sc = new Scanner(System.in);
        int choice;

        // Loop to execute the menu
        do {
            // Options demonstrating the input to execute various functions
            System.out.println("\n===== Student Database Menu =====");
            System.out.println("1. View all students");
            System.out.println("2. Add a new student");
            System.out.println("3. Update a student's email");
            System.out.println("4. Delete a student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            // Swtich between possible inputs and execute them
            switch (choice) {
                case 1:
                    db.getAllStudents();
                    break;
                case 2:
                    System.out.print("Enter first name: ");
                    String firstName = sc.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter enrollment date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    db.addStudent(firstName, lastName, email, date);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int idToUpdate = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter new email: ");
                    String newEmail = sc.nextLine();
                    db.updateStudentEmail(idToUpdate, newEmail);
                    break;
                case 4:
                    System.out.print("Enter student ID to delete: ");
                    int idToDelete = sc.nextInt();
                    db.deleteStudent(idToDelete);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
        sc.close();
    }
}
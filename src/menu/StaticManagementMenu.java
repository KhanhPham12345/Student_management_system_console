package menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Actions.StaticStudentManager;
import Actions.Validator;
import Entity.Student;

public class StaticManagementMenu {
    private final StaticStudentManager manager = new StaticStudentManager();
    private final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DOB_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void showMenu() {
        String choice = "";
        do {
            System.out.println("\n===== STUDENT MANAGEMENT (STATIC) =====");
            System.out.println("1. Add Student (press 1)");
            System.out.println("2. Display List of Students (press 2)");
            System.out.println("3. Search Student by ID (press 3)");
            System.out.println("4. Update Student Information (press 4)");
            System.out.println("5. Delete Student (press 5)");
            System.out.println("0. Exit (press 0)");
            System.out.print("Your choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addStudentFlow();
                    break;
                case "2":
                    manager.showAllstudents();
                    break;
                case "3":
                    searchByIdFlow();
                    break;
                case "4":
                    updateStudentFlow();
                    break;
                case "5":
                    deleteStudentFlow();
                    break;
                case "0":
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (!"0".equals(choice));
    }

    private void addStudentFlow() {
        System.out.println("== Add Student ==");
        try {
            String name = Validator.Input(scanner, "name");
            String dobStr = Validator.Input(scanner, "dateofbirth");
            String address = Validator.Input(scanner, "address");
            float height = Float.parseFloat(Validator.Input(scanner, "height"));
            float weight = Float.parseFloat(Validator.Input(scanner, "weight"));
            String studentCode = Validator.Input(scanner, "studentcode");
            String school = Validator.Input(scanner, "school");
            int startYear = Integer.parseInt(Validator.Input(scanner, "startyear"));
            double gpa = Double.parseDouble(Validator.Input(scanner, "gpa"));

            LocalDate dob = LocalDate.parse(dobStr, DOB_FMT);

            Student s = new Student(
                    name, dob, address, height, weight,
                    studentCode, school, startYear, gpa);

            manager.addStudent(s);
        } catch (Exception e) {
            System.out.println("Failed to add student: " + e.getMessage());
        }
    }

    private void searchByIdFlow() {
        System.out.println("== Search Student by ID ==");
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        Student found = manager.findById(id);
        if (found != null) {
            System.out.println(found.toString());
        }
    }

    private void updateStudentFlow() {
        System.out.println("== Update Student ==");
        System.out.print("Enter existing ID: ");
        String id = scanner.nextLine().trim();

        // call the manager's built-in interactive updater
        manager.updateStudent(id, scanner, manager.getStudentsArray());
    }

    private void deleteStudentFlow() {
        System.out.println("== Delete Student ==");
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        manager.deleteStudentById(id);
    }
}

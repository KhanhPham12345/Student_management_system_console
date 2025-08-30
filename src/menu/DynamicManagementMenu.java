package menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Scanner;

import Actions.DynamicStudentManager;
import Actions.Validator;
import Entity.LearningStrength;
import Entity.Student;

public class DynamicManagementMenu {

    Scanner scanner = new Scanner(System.in);
    DynamicStudentManager manager = new DynamicStudentManager("src\\IOFile\\save_students.txt");

    public void runDynamicFlow() {
        while (true) {
            displayMenu();
            String choice = getChoice(scanner.nextLine());
            if (choice.equals("0")) {
                System.out.println("Exiting the program!");
                break;
            }
            processChoice(choice);
        }
    }

    private void displayMenu() {
        System.out.println("\n===== DYNAMIC STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("Add new student (press 1)");
        System.out.println("Find student by ID (press 2)");
        System.out.println("Update student information (press 3)");
        System.out.println("Delete student (press 4)");
        System.out.println("Show all students (press 5)");
        System.out.println("Show students performance (press 6)");
        System.out.println("Show performance distribution (%) (press 7)");
        System.out.println("Show GPA distribution (%) (press 8)");
        System.out.println("Exit (press 0)");
        System.out.println("======================================");
        System.out.print("Enter your choice: ");
    }

    private static String getChoice(String input) {
        return input.trim();
    }

    private void processChoice(String choice) {
        switch (choice) {
            case "1":
                System.out.println("Please input all the student's information below:");
                // Input name
                String name = Validator.Input(scanner, "name");
                // Input dob
                String dob = Validator.Input(scanner, "dateofbirth");
                // Input address
                String address = Validator.Input(scanner, "address");
                // Input height
                String height = Validator.Input(scanner, "height");
                // Input weight
                String weight = Validator.Input(scanner, "weight");
                // Input school name
                String school = Validator.Input(scanner, "school");
                // Input student code
                String studentCode = Validator.Input(scanner, "studentcode");
                // Input startYear
                String startYear = Validator.Input(scanner, "startyear");
                // Input startYear
                String gpa = Validator.Input(scanner, "gpa");
                // Convert all data that is not String
                LocalDate modDob = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                float modHeight = Float.parseFloat(height);
                float modWeight = Float.parseFloat(weight);
                int modStartyear = Integer.parseInt(startYear);
                double modGpa = Double.parseDouble(gpa);

                Student student = new Student(name, modDob, address, modHeight, modWeight, studentCode, school,
                        modStartyear, modGpa);
                // Saving the student to the file
                manager.addStudent(student);

                break;
            case "2":
                System.out.println("Please input student id below:");
                String id = scanner.nextLine();
                if (!Validator.validateId(id, manager)) {
                    System.out.println("There is no student with given id");
                    break;
                }
                Student foundStudent = manager.findById(id);
                System.out.println("\n" + foundStudent.toString());
                break;
            case "3":
                System.out.println("Please input student id below:");
                Validator.updateStudentMenu(manager);
                break;
            case "4":
                String studentId = scanner.nextLine();
                manager.deleteStudentById(studentId);
                break;
            case "5":
                System.out.println("Retrieving all students in dynamic flow");
                List<Student> students = manager.getAllStudents();
                for (Student s : students) {
                    System.out.println(s.toString());
                }
                break;
            case "6":
                System.out.println("Input student tier \n Eg: KEM, YEU, TRUNG BINH, KHA, GIOI, XUATSAC");
                String item = scanner.nextLine();

                LearningStrength tier = Validator.parseLearningStrength(item);
                if (tier == null) {
                    System.out.println("Invalid learning strength input!");
                    break;
                }

                manager.getStudentsByLearningStrength(tier);
                break;
            case "7":
                manager.getLearningStats();
                break;
            case "8":
                manager.percentageGpaStats();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

}

import Entity.Student;
import Actions.StudentManager;
import Actions.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        StudentManager manager = new StudentManager("src\\IOFile\\save_students.txt");
        try (Scanner sc = new Scanner(System.in)) { // auto-close at end
            while (true) {
                System.out.println("STUDENT MANAGEMENT DYNAMIC APPLICATION MENU");
                System.out.println("Update students(Press 1):");
                System.out.println("See all students(Press 2):");
                System.out.println("Search student(input id)(Press 4):");
                System.out.println("Create student(input id)(Press 5):");
                System.out.println("Delete student(input id)(Press 6):");
                System.out.println("Checkout student score stats (Press 7):");
                System.out.println("Exit (Press 3)");

                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println("UPDATE STUDENT BY ID");
                        try {
                            System.out.println("id number: ");
                            String id = sc.nextLine();

                            System.out.println("Full name: ");
                            String name = sc.nextLine();

                            System.out.println("Date of birth (yyyy-MM-dd): ");
                            LocalDate dob = LocalDate.parse(sc.nextLine());

                            System.out.println("Address: ");
                            String address = sc.nextLine();

                            System.out.println("Height (cm): ");
                            float height = Float.parseFloat(sc.nextLine());

                            System.out.println("Weight (kg): ");
                            float weight = Float.parseFloat(sc.nextLine());

                            System.out.println("Student code (10 characters): ");
                            String studentCode = sc.nextLine();

                            System.out.println("School: ");
                            String school = sc.nextLine();

                            System.out.println("Start year: ");
                            int startYear = Integer.parseInt(sc.nextLine());

                            System.out.println("Average GPA: ");
                            double gpa = Double.parseDouble(sc.nextLine());

                            Student updated = new Student(name, dob, address, height, weight, studentCode, school,
                                    startYear, gpa);

                            // Validate
                            Validator.validateStudentForUpdate(id, updated, manager);

                            // If all fields are valid then add the student
                            manager.updateStudent(id, updated);
                            System.out.println("Updated student successfully!");
                        } catch (Exception ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }
                        break;
                    case "2":
                        printList(manager.getAllStudents());
                        break;
                    case "3":
                        System.out.println("Bye!");
                        return;
                    case "4":
                        System.out.println("GET STUDENT BY ID");
                        System.out.println("Input student id below: ");

                        String id = sc.nextLine();
                        Student s = manager.findById(id);
                        if (s != null) {
                            System.out.println(s.toString());
                        } else {
                            System.out.println(
                                    "Unable to find student with given id. Try again with valid id(integer only)");
                        }
                        break;
                    case "5":
                        System.out.println("CREATE & SAVE STUDENT BY ID");
                        try {
                            System.out.println("Full name: ");
                            String name = sc.nextLine();

                            System.out.println("Date of birth (yyyy-MM-dd): ");
                            LocalDate dob = LocalDate.parse(sc.nextLine());

                            System.out.println("Address: ");
                            String address = sc.nextLine();

                            System.out.println("Height (cm): ");
                            float height = Float.parseFloat(sc.nextLine());

                            System.out.println("Weight (kg): ");
                            float weight = Float.parseFloat(sc.nextLine());

                            System.out.println("Student code (10 characters): ");
                            String studentCode = sc.nextLine();

                            System.out.println("School: ");
                            String school = sc.nextLine();

                            System.out.println("Start year: ");
                            int startYear = Integer.parseInt(sc.nextLine());

                            System.out.println("Average GPA: ");
                            double gpa = Double.parseDouble(sc.nextLine());

                            Student newStudent = new Student(name, dob, address, height, weight, studentCode, school,
                                    startYear, gpa);

                            // Validate
                            Validator.validateStudent(newStudent, manager);

                            // If all fields are valid then add the student
                            manager.addStudent(newStudent);
                            System.out.println("Created and added student successfully!");
                        } catch (Exception ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }
                        break;
                    case "6":
                        System.out.println("DELETE STUDENT BY ID");
                        String studentId = sc.nextLine();
                        manager.deleteStudentById(studentId);
                        break;
                    case "7":
                        System.out.println("OVERALL STUDENTS STATS");
                        manager.getLearningStats();
                        manager.percentageGpaStats();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }
    }

    private static void printList(List<Student> students) {
        for (Student s : students) {
            System.out.println(s);
        }
    }

}

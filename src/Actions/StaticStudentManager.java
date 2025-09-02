package Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Entity.Student;

public class StaticStudentManager {

    private Student[] students;
    private int size = 0;
    private int nextId = 1;

    private static final String FILE_PATH = "src\\IOFile\\save_static_students.txt";

    public StaticStudentManager() {
        this.students = new Student[Validator.MAX_STUDENT];
        loadFromFile();
        reseedStaticCounter();
    }

    // CREATE & add a student
    public void addStudent(Student student) {
        // Check if a student has existed or not

        if (student == null)
            return;

        if (size >= students.length) {
            System.out.println("Cannot add student: capacity reached (" + students.length + ").");
            return;
        }

        // Assign an id that continues from saved static data
        student.setId(nextId);

        if (!checkStudentExistence(student)) {
            return;
        }

        students[size++] = student;
        nextId++;
        saveToFile();
        System.out.println("Student added successfully.");
    }

    // READ a student by id
    public Student findById(String id) {
        int studentId;

        try {
            studentId = Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid student's id, student id must be integer");
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (students[i] != null && students[i].getId() == studentId) {
                return students[i];
            }
        }
        System.out.println("No matching data found.");
        return null;
    }

    // UPDATE student by id
    public void updateStudent(String id, Scanner scanner, Student[] students) {
        Student current = findById(id);

        if (current == null) {
            System.out.println("There is no student with given id");
            return;
        }

        System.out.println("Student before update \n" + current.toString());
        while (true) {
            System.out.println("\n===== UPDATE STUDENT MENU =====");
            System.out.println("1. Name");
            System.out.println("2. DateOfBirth - dd/MM/yyyy");
            System.out.println("3. Address");
            System.out.println("4. Height");
            System.out.println("5. Weight");
            System.out.println("6. StudentCode");
            System.out.println("7. School");
            System.out.println("8. StartYear");
            System.out.println("9. GPA");
            System.out.println("0. Exit update");
            System.out.print("Pick an update option: ");

            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                System.out.println("Exit update!");
                break;
            }

            String field;
            String newValue;

            boolean changed = false;
            try {
                switch (choice) {
                    case "1":
                        field = "name";
                        newValue = Validator.Input(scanner, field);
                        current.setName(newValue);
                        changed = true;
                        break;
                    case "2":
                        field = "dateofbirth";
                        newValue = Validator.Input(scanner, field);
                        // convert to LocalDate
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate dob = LocalDate.parse(newValue, formatter);
                        current.setDob(dob);
                        changed = true;
                        break;
                    case "3":
                        field = "address";
                        newValue = Validator.Input(scanner, field);
                        current.setAddress(newValue);
                        changed = true;
                        break;
                    case "4":
                        field = "height";
                        float height = Float.parseFloat(Validator.Input(scanner, field));
                        current.setHeightcm(height);
                        changed = true;
                        break;
                    case "5":
                        field = "weight";
                        float weight = Float.parseFloat(Validator.Input(scanner, field));
                        current.setWeightkg(weight);
                        changed = true;
                        break;
                    case "6":
                        field = "studentcode";
                        newValue = Validator.Input(scanner, field);
                        // Check if there is duplicate student code or not
                        boolean dup = false;
                        for (int i = 0; i < size; i++) {
                            Student s = students[i];
                            if (s != null && s.getId() != current.getId() && newValue.equals(s.getStudentCode())) {
                                dup = true;
                                break;
                            }
                        }

                        if (dup) {
                            System.out.println("Can't update: duplicate student code '" + newValue + "'.");
                            break;
                        }
                        current.setStudentCode(newValue);
                        changed = true;
                        break;
                    case "7":
                        field = "school";
                        newValue = Validator.Input(scanner, field);
                        current.setSchool(newValue);
                        changed = true;
                        break;
                    case "8":
                        field = "startyear";
                        newValue = Validator.Input(scanner, field);
                        int year = Integer.parseInt(newValue);
                        current.setStartYear(year);
                        changed = true;
                        break;
                    case "9":
                        field = "gpa";
                        newValue = Validator.Input(scanner, field);
                        double gpa = Double.parseDouble(newValue);
                        current.setGpa(gpa);
                        changed = true;
                        break;
                    default:
                        System.out.println("Invalid input! Enter your choice again:");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input format");
            }
            System.out.println("Student after update \n" + current.toString());

            if (changed) {
                saveToFile();
                System.out.println("Student after update \n" + current);
            }
        }
    }

    // DELETE a student by id
    public void deleteStudentById(String id) {
        int studentId;

        try {
            studentId = Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid student's id, student id must be integer");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (students[i] != null && students[i].getId() == studentId) {
                // delete the student if found
                int numMoved = size - i - 1;
                if (numMoved > 0) {
                    System.arraycopy(students, i + 1, students, i, numMoved);
                }
                students[size - 1] = null;
                size--;
                saveToFile();
                System.out.println("Student with ID " + id + " has been deleted successfully.");
                return;
            }
        }
        System.out.println("No student found with ID " + id);
    }

    // READ show all students
    public void showAllstudents() {
        if (size == 0) {
            System.out.println("No students in the list.");
            return;
        }
        System.out.println("Rendering all students in list");
        for (int i = 0; i < size; i++) {
            Student s = students[i];
            if (s != null) {
                System.out.println(s);
            }
        }
    }

    // Take in Student object and check for duplicate in id and/or student code
    public boolean checkStudentExistence(Student student) {
        if (students == null)
            return false;

        for (int i = 0; i < size; i++) { // only used slots
            Student s = students[i];
            if (s == null)
                continue;

            // duplicate id
            if (s.getId() == student.getId()) {
                System.out.println("Can't save new student: duplicate id " + student.getId());
                return false;
            }

            // duplicate student code
            String a = s.getStudentCode();
            String b = student.getStudentCode();
            if (a != null && a.equals(b)) {
                System.out.println("Can't save new student: duplicate student code " + b);
                return false;
            }
        }
        return true;
    }

    public Student[] getStudentsArray() {
        return students;
    }

    private void saveToFile() {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists())
            parent.mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeInt(size);
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            int savedSize = ois.readInt();
            Object obj = ois.readObject();

            if (obj instanceof Student[]) {
                Student[] loaded = (Student[]) obj;
                if (loaded.length != this.students.length) {
                    int copy = Math.min(loaded.length, this.students.length);

                    System.arraycopy(loaded, 0, this.students, 0, copy);
                } else {
                    this.students = loaded;
                }
                this.size = Math.max(0, Math.min(savedSize, this.students.length));
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students: " + e.getMessage());
            this.students = new Student[Validator.MAX_STUDENT];
            this.size = 0;
            this.nextId = 1;
        }
    }

    private void reseedStaticCounter() {
        int max = 0;
        for (int i = 0; i < size; i++) {
            Student s = students[i];
            if (s != null && s.getId() > max)
                max = s.getId();
        }
        nextId = Math.max(nextId, max + 1);
    }
}

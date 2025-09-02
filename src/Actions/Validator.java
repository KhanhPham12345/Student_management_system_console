package Actions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import Entity.LearningStrength;
import Entity.Student;

public class Validator {

    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2025;
    public static final int MAX_ADDRESS_LENGTH = 300;
    public static final int MIN_HEIGHT = 50;
    public static final int MAX_HEIGHT = 300;
    public static final int MIN_WEIGHT = 5;
    public static final int MAX_WEIGHT = 1000;

    public static final int STUDENT_CODE = 10;
    public static final int MAX_SCHOOL_NAME_LENGTH = 200;
    public static final double MIN_GPA = 0;
    public static final double MAX_GPA = 10;
    public static final int MAX_STUDENT = 100;

    public static boolean validateName(String name) {
        if (name == null || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            return false;
        }
        return !name.matches(".*\\d.*");
    }

    public static boolean validateId(String id, DynamicStudentManager manager) {
        return id != null && id.matches("\\d+") && manager.findById(id) != null;
    }

    public static boolean validateBirth(String dateOfbirth) {
        if (dateOfbirth == null || dateOfbirth.trim().isEmpty()) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateOfbirth, formatter);
            int year = date.getYear();
            // Check the year if it fits condition
            if (year < MIN_YEAR || year > MAX_YEAR) {
                System.out.print("Invalid date of birth");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Invalid date of birth. Please input correct format dd/MM/yyyy");
            return false;
        }
        return true;
    }

    public static boolean validateAddress(String address) {
        return address != null && !address.trim().isEmpty() && address.length() <= MAX_ADDRESS_LENGTH;
    }

    public static boolean validateHeight(Float height) {
        return height != null && height >= MIN_HEIGHT && height <= MAX_HEIGHT;
    }

    public static boolean validatWeight(Float weight) {
        return weight != null && weight >= MIN_WEIGHT && weight <= MAX_WEIGHT;
    }

    public static boolean validateStudentCode(String studentCode) {
        return studentCode != null && !studentCode.trim().isBlank() && studentCode.length() == STUDENT_CODE;
    }

    public static boolean validateDuplicateStudentCode(String studentCode, List<Student> students) {
        if (studentCode == null || students == null) {
            return false;
        }
        for (Student s : students) {
            if (studentCode.equals(s.getStudentCode())) {
                return false; // Duplicate found
            }
        }
        return true; // No duplicate
    }

    public static boolean validateSchool(String school) {
        return school != null && !school.trim().isBlank() && school.length() <= MAX_SCHOOL_NAME_LENGTH;
    }

    public static boolean validateStartYear(Integer startYear) {
        return startYear != null && startYear >= MIN_YEAR && startYear <= MAX_YEAR;
    }

    public static boolean validateGpa(Double gpa) {
        return gpa != null && gpa >= MIN_GPA && gpa <= MAX_GPA;
    }

    public static boolean validate(String input, String type) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        try {
            switch (type.toLowerCase()) {
                case "name":
                    return validateName(input);

                case "dateofbirth":
                    return validateBirth(input);

                case "address":
                    return validateAddress(input);

                case "height":
                    float height = Float.parseFloat(input);
                    return validateHeight(height);

                case "weight":
                    float weight = Float.parseFloat(input);
                    return validatWeight(weight);

                case "studentcode":
                    return validateStudentCode(input);

                case "school":
                    return validateSchool(input);

                case "startyear":
                    int year = Integer.parseInt(input);
                    return validateStartYear(year);
                case "gpa":
                    double gpa = Double.parseDouble(input);
                    return validateGpa(gpa);

                default:
                    System.out.println("Unknown validation type: " + type);
                    return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for type: " + type);
            return false;
        }
    }

    public static boolean updateStudent(Student updateStudent, String field, String newValue) {
        if (updateStudent == null || field == null || newValue == null) {
            System.out.println("Invalid parameter");
            return false;
        }

        String lowerField = field.toLowerCase();

        if (!Validator.validate(newValue, lowerField)) {
            System.out.println("New value is invalid for field: " + field);
            return false;
        }

        try {
            switch (lowerField) {
                case "name":
                    updateStudent.setName(newValue);
                    break;
                case "dateofbirth":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dob = LocalDate.parse(newValue, formatter);
                    updateStudent.setDob(dob);
                    break;

                case "address":
                    updateStudent.setAddress(newValue);
                    break;

                case "height":
                    updateStudent.setHeightcm(Float.parseFloat(newValue));
                    break;

                case "weight":
                    updateStudent.setWeightkg(Float.parseFloat(newValue));
                    break;

                case "studentcode":
                    // I want to check if the newValue is duplicated or not
                    updateStudent.setStudentCode(newValue);
                    break;

                case "school":
                    updateStudent.setSchool(newValue);
                    break;

                case "startyear":
                    updateStudent.setStartYear(Integer.parseInt(newValue));
                    break;

                case "gpa":
                    updateStudent.setGpa(Double.parseDouble(newValue));
                    break;
                default:
                    System.out.println("Non-existing field: " + field);
                    break;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error updating field: " + e.getMessage());
            return false;
        }
    }

    public static LearningStrength parseLearningStrength(String item) {
        for (LearningStrength i : LearningStrength.values()) {
            if (i.getPerformance().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return null;
    }

    public static String Input(Scanner sc, String type) {
        String input;
        while (true) {
            System.out.println("Input " + type + ":");
            input = sc.nextLine();

            if (Validator.validate(input, type)) {
                return input;
            } else {
                System.out.println("Invalid data, please input again");
            }
        }
    }

    public static void updateStudentMenu(DynamicStudentManager manager) {
        Scanner scanner = new Scanner(System.in);
        String existingId = scanner.nextLine();
        Student existingStudent = manager.findById(existingId);
        if (existingStudent == null) {
            return;
        }
        System.out.println("Student before update \n" + existingStudent.toString());
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

            String field = null;
            switch (choice) {
                case "1":
                    field = "name";
                    break;
                case "2":
                    field = "dateofbirth";
                    break;
                case "3":
                    field = "address";
                    break;
                case "4":
                    field = "height";
                    break;
                case "5":
                    field = "weight";
                    break;
                case "6":
                    field = "studentcode";
                    break;
                case "7":
                    field = "school";
                    break;
                case "8":
                    field = "startyear";
                    break;
                case "9":
                    field = "gpa";
                    break;
                default:
                    System.out.println("Invalid input! Enter your choice again:");
                    continue;
            }

            String newValue = Validator.Input(scanner, field);
            if ("studentcode".equals(field)) {
                if (!validateDuplicateStudentCode(existingId, manager.getAllStudents())) {
                    System.out.println("Duplicate student code. Can't update");
                    break;
                }
            }

            boolean updated = Validator.updateStudent(existingStudent, field, newValue);

            if (updated) {
                System.out.println("Successful update " + field + "!");
                System.out.println("Student after update \n" + existingStudent.toString());
                manager.updateStudent(existingId, existingStudent);
            } else {
                System.out.println("Failed update " + field + "!");
            }
        }
    }

}

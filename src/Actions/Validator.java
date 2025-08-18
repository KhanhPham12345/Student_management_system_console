package Actions;

import java.time.LocalDate;

import Entity.Student;

public class Validator {
    public static void validateStudent(Student s, StudentManager manager) {
        // Name
        if (s.getName() == null || s.getName().trim().isEmpty() || s.getName().length() > 100) {
            throw new IllegalArgumentException("Invalid name (not empty, < 100 characters)");
        }
        // DOB
        if (s.getDob() == null || s.getDob().isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("Invalid date of birth (>= 1900)");
        }
        // Address
        if (s.getAddress() != null && s.getAddress().length() > 300) {
            throw new IllegalArgumentException("Address too long(>300 characters)");
        }
        // Height
        if (s.getHeightcm() < 50.0 || s.getHeightcm() > 300) {
            throw new IllegalArgumentException("Height must be within range 50-300 cm");
        }
        // Weight
        if (s.getWeightkg() < 5.0 || s.getWeightkg() > 1000.0) {
            throw new IllegalArgumentException("Weight must be within range 5-1000kg");
        }
        // Student code
        if (s.getStudentCode() == null || s.getStudentCode().length() != 10) {
            throw new IllegalArgumentException("Student code must have 10 characters");
        }
        if (manager.findByStudentCode(s.getStudentCode()) != null) {
            throw new IllegalArgumentException("Existing student code.");
        }
        // Start year
        if (s.getStartYear() < 1900 || String.valueOf(s.getStartYear()).length() != 4) {
            throw new IllegalArgumentException("Invalid start year(>= 1900, 4 chữ số)");
        }
        // GPA
        if (s.getGpa() < 0.0 || s.getGpa() > 10.0) {
            throw new IllegalArgumentException("GPA must be within range 0-10.0");
        }

    }

}

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

        // School
        if (s.getSchool() == null || s.getSchool().trim().isEmpty() || s.getSchool().length() >= 200) {
            throw new IllegalArgumentException("Invalid school name (not empty & fewer than 200 characters)");
        }

        // Start year
        if (s.getStartYear() < 1900 || String.valueOf(s.getStartYear()).length() != 4) {
            throw new IllegalArgumentException("Invalid start year(>= 1900, 4 numbers)");
        }
        // GPA
        if (s.getGpa() < 0.0 || s.getGpa() > 10.0) {
            throw new IllegalArgumentException("GPA must be within range 0-10.0");
        }

    }

    // New method for UPDATING an existing student
    public static void validateStudentForUpdate(String existingId, Student updatedStudent, StudentManager manager) {
        // Name
        if (updatedStudent.getName() == null || updatedStudent.getName().trim().isEmpty()
                || updatedStudent.getName().length() > 100) {
            throw new IllegalArgumentException("Invalid name (not empty, < 100 characters)");
        }
        // DOB
        if (updatedStudent.getDob() == null || updatedStudent.getDob().isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("Invalid date of birth (>= 1900)");
        }
        // Address
        if (updatedStudent.getAddress() != null && updatedStudent.getAddress().length() > 300) {
            throw new IllegalArgumentException("Address too long(>300 characters)");
        }
        // Height
        if (updatedStudent.getHeightcm() < 50.0 || updatedStudent.getHeightcm() > 300) {
            throw new IllegalArgumentException("Height must be within range 50-300 cm");
        }
        // Weight
        if (updatedStudent.getWeightkg() < 5.0 || updatedStudent.getWeightkg() > 1000.0) {
            throw new IllegalArgumentException("Weight must be within range 5-1000kg");
        }
        // Student code
        if (updatedStudent.getStudentCode() == null || updatedStudent.getStudentCode().length() != 10) {
            throw new IllegalArgumentException("Student code must have 10 characters");
        }

        // Find the student with the new student code.
        Student existingStudent = manager.findByStudentCode(updatedStudent.getStudentCode());

        // If a student is found AND their ID is *not* the ID we are updating, then it's
        // a conflict.
        if (existingStudent != null) {
            int comparingId = manager.convertStringToInt(existingId);

            if (existingStudent.getId() != comparingId) {
                throw new IllegalArgumentException("Existing student code.");
            }
        }

        // School
        if (updatedStudent.getSchool() == null || updatedStudent.getSchool().trim().isEmpty()
                || updatedStudent.getSchool().length() >= 200) {
            throw new IllegalArgumentException("Invalid school name (not empty & fewer than 200 characters)");
        }

        // Start year
        if (updatedStudent.getStartYear() < 1900 || String.valueOf(updatedStudent.getStartYear()).length() != 4) {
            throw new IllegalArgumentException("Invalid start year(>= 1900, 4 numbers)");
        }
        // GPA
        if (updatedStudent.getGpa() < 0.0 || updatedStudent.getGpa() > 10.0) {
            throw new IllegalArgumentException("GPA must be within range 0-10.0");
        }
    }

}

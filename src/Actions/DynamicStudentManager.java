package Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entity.LearningStrength;
import Entity.Person;
import Entity.Student;

public class DynamicStudentManager {
    private String filePath;

    private List<Student> students = new LinkedList<>();

    public DynamicStudentManager(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    // CREATE - add student to the list
    public void addStudent(Student student) {
        for (Student s : students) {
            if (s.getId() == student.getId() || s.getStudentCode().equals(student.getStudentCode())) {
                System.out.println("Can't save new student with duplicate id or student code");
                return;
            }
        }
        students.add(student);
        saveToFile();
        System.out.println("Student added successfully");
    }

    // READ
    public List<Student> getAllStudents() {
        return students;
    }

    // Find student by student code
    public Student findByStudentCode(String studentCode) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentCode().equals(studentCode)) {
                return students.get(i);
            }
        }
        return null;
    }

    // Find student by person id
    public Student findById(String id) {
        int studentId;
        // convert string to integer
        try {
            int number = Integer.parseInt(id);
            studentId = number;
        } catch (NumberFormatException e) {
            System.out.println("Invalid student's id, student id must be integer");
            return null;
        }

        for (Student s : students) {
            if (s.getId() == studentId)
                return s;
        }
        return null;
    }

    // UPDATE with student id
    public void updateStudent(String id, Student updated) {
        Student existing = findById(id);

        if (existing == null) {
            System.out.println("Can't find student with given id.");
            return;
        }

        try {
            // Keep old student id
            updated.setId(existing.getId());
            for (Student s : students) {
                if (s.getId() == existing.getId()) {
                    students.remove(s);
                    students.add(updated);
                    saveToFile();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error when updating student: " + e.getMessage());
        }
    }

    // DELETE student
    public void deleteStudentById(String idOrCode) {
        // check if the string inputted is an "id" or "student code"

        Student s = findById(idOrCode);
        Student s1 = findByStudentCode(idOrCode);

        if (s == null && s1 == null) {
            System.out.println("Can't find student with that id or student code");
            return;
        }

        if (s != null) {
            students.remove(s);
        } else if (s1 != null) {
            students.remove(s1);
        }
        saveToFile();
        System.out.println("Successfully delete student");
    }

    // LOAD: load students from file into a list
    private void loadFromFile() {
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            students.clear();
            Person.reseedCounterFrom(students);
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                students = new LinkedList<>((List<Student>) obj);
                Person.reseedCounterFrom(students);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            students = new LinkedList<>();
        }
    }

    // SAVE: dumping list to a file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Percentage of student learning strength from descending order
    public void getLearningStats() {
        Map<LearningStrength, Integer> map = new HashMap<>();
        for (Student s : students) {
            map.put(s.getLearningstrength(), map.getOrDefault(s.getLearningstrength(), 0) + 1);
        }
        int total = students.size();
        if (total == 0) {
            System.out.println("No students to calculate statistics.");
            return;
        }
        map.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(e -> {
                    double percent = (e.getValue() * 100.0 / total);
                    System.out.printf("%s: %.2f%%\n", e.getKey(), percent);
                });
    }

    // Percentage of student gpa in the list
    public void percentageGpaStats() {
        Map<Double, Integer> map = new HashMap<>();
        for (Student s : students) {
            map.put(s.getGpa(), map.getOrDefault(s.getGpa(), 0) + 1);
        }

        int total = students.size();
        if (total == 0) {
            System.out.println("No students to calculate statistics.");
            return;
        }

        map.entrySet().forEach(e -> {
            double percent = (e.getValue() * 100.0 / total);
            System.out.printf("Score %.1f: %.2f%%\n", e.getKey(), percent);
        });

    }

    // Get all students by LearningStrength
    public void getStudentsByLearningStrength(LearningStrength tier) {
        boolean found = false;
        for (Student student : students) {
            if (student.getLearningstrength() == tier) {
                System.out.println(student.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Warn: there is no Student belong to tier: " + tier.getPerformance());
        }
    }

}

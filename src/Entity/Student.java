package Entity;

import java.time.LocalDate;

public class Student extends Person {
    private static final long serialVersionUID = 1L;
    private String studentCode;
    private String school;
    private int startYear;
    private double gpa;
    private LearningStrength Learningstrength;

    public Student(String name, LocalDate dob, String address, float heightcm, float weightkg, String studentCode,
            String school, int startYear, double gpa) {
        super(name, dob, address, heightcm, weightkg);
        this.studentCode = studentCode;
        this.school = school;
        this.startYear = startYear;
        this.gpa = gpa;
        this.Learningstrength = tinhLearningstrength(gpa);
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
        this.Learningstrength = tinhLearningstrength(gpa);
    }

    public LearningStrength getLearningstrength() {
        return Learningstrength;
    }

    private LearningStrength tinhLearningstrength(double gpa) {
        if (gpa < 3)
            return LearningStrength.KEM;
        if (gpa < 5)
            return LearningStrength.YEU;
        if (gpa < 6.5)
            return LearningStrength.TRUNG_BINH;
        if (gpa < 7.5)
            return LearningStrength.KHA;
        if (gpa < 9)
            return LearningStrength.GIOI;
        return LearningStrength.XUAT_SAC;
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                ", school='" + school + '\'' +
                ", studentCode='" + studentCode + '\'' +
                ", startYear=" + startYear +
                ", gpa=" + gpa +
                ", Learningstrength=" + Learningstrength +
                '}';
    }

}

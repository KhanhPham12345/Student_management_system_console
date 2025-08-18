package Entity;

import java.time.LocalDate;

public class Student extends Person {
    private static final long serialVersionUID = 1L;
    private String studentCode;
    private String school;
    private int startYear;
    private double gpa;
    private HocLuc hocLuc;

    public Student(String name, LocalDate dob, String address, float heightcm, float weightkg, String studentCode,
            String school, int startYear, double gpa) {
        super(name, dob, address, heightcm, weightkg);
        this.studentCode = studentCode;
        this.school = school;
        this.startYear = startYear;
        this.gpa = gpa;
        this.hocLuc = tinhHocLuc(gpa);
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
        this.hocLuc = tinhHocLuc(gpa);
    }

    public HocLuc getHocLuc() {
        return hocLuc;
    }

    private HocLuc tinhHocLuc(double gpa) {
        if (gpa < 3)
            return HocLuc.KEM;
        if (gpa < 5)
            return HocLuc.YEU;
        if (gpa < 6.5)
            return HocLuc.TRUNG_BINH;
        if (gpa < 7.5)
            return HocLuc.KHA;
        if (gpa < 9)
            return HocLuc.GIOI;
        return HocLuc.XUAT_SAC;
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                ", school='" + school + '\'' +
                ", studentCode='" + studentCode + '\'' +
                ", startYear=" + startYear +
                ", gpa=" + gpa +
                ", hocLuc=" + hocLuc +
                '}';
    }

}

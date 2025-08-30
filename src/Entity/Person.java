package Entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int counter = 1;
    private int id;
    private String name;
    private LocalDate dob;
    private String address;
    private float heightcm;
    private float weightkg;

    public Person(String name, LocalDate dob, String address, float heightcm, float weightkg) {
        this.id = counter++;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.heightcm = heightcm;
        this.weightkg = weightkg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getHeightcm() {
        return heightcm;
    }

    public void setHeightcm(float heightcm) {
        this.heightcm = heightcm;
    }

    public float getWeightkg() {
        return weightkg;
    }

    public void setWeightkg(float weightkg) {
        this.weightkg = weightkg;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + (dob != null ? dob.toString() : "null") +
                ", address=" + address +
                ", heightcm=" + heightcm +
                ", weightkg=" + weightkg +
                '}';
    }
}

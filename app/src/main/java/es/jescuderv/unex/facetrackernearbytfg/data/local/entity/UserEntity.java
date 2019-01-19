package es.jescuderv.unex.facetrackernearbytfg.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public final class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String userName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @ColumnInfo(name = "phone_number")
    private Integer phoneNumber;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "blood_type")
    private String bloodType;

    @ColumnInfo(name = "medical_Description")
    private String medicalDescription;

    @ColumnInfo(name = "face_path")
    private String facePath;

    @ColumnInfo(name = "diabetes_treatment")
    private String diabetesTreatment;

    @ColumnInfo(name = "hearth_beat_treatment")
    private String hearthBeatTreatment;

    public UserEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getMedicalDescription() {
        return medicalDescription;
    }

    public void setMedicalDescription(String medicalDescription) {
        this.medicalDescription = medicalDescription;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public String getDiabetesTreatment() {
        return diabetesTreatment;
    }

    public void setDiabetesTreatment(String diabetesTreatment) {
        this.diabetesTreatment = diabetesTreatment;
    }

    public String getHearthBeatTreatment() {
        return hearthBeatTreatment;
    }

    public void setHearthBeatTreatment(String hearthBeatTreatment) {
        this.hearthBeatTreatment = hearthBeatTreatment;
    }
}

package es.jescuderv.unex.facetrackernearbytfg.domain.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Integer id;
    private String userName;
    private String lastName;
    private String birthday;
    private Integer phoneNumber;
    private String address;
    private String description;
    private String facePath;

    private String bloodType;
    private String medicalDescription;
    private List<Intolerance> intoleranceList = new ArrayList<>();
    private List<Surgery> surgeryList = new ArrayList<>();
    private List<Allergy> allergyList = new ArrayList<>();


    public User() {
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

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
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

    public List<Intolerance> getIntoleranceList() {
        return intoleranceList;
    }

    public void setIntoleranceList(List<Intolerance> intoleranceList) {
        this.intoleranceList = intoleranceList;
    }

    public List<Surgery> getSurgeryList() {
        return surgeryList;
    }

    public void setSurgeryList(List<Surgery> surgeryList) {
        this.surgeryList = surgeryList;
    }

    public List<Allergy> getAllergyList() {
        return allergyList;
    }

    public void setAllergyList(List<Allergy> allergyList) {
        this.allergyList = allergyList;
    }


    public String getUserJson() {
        Gson gson = new GsonBuilder().create();
        JsonObject json = (JsonObject) gson.toJsonTree(this);
        json.remove("property");
        return json.toString();
    }
}

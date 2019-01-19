package es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserViewModel implements Serializable {

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
    private List<UserViewModel.Intolerance> intoleranceList = new ArrayList<>();
    private List<UserViewModel.Surgery> surgeryList = new ArrayList<>();
    private List<UserViewModel.Allergy> allergyList = new ArrayList<>();

    private String diabetesMedication;
    private List<Medication> diabetesList = new ArrayList<>();
    private String hearthBeatMedication;
    private List<Medication> hearthBeatList = new ArrayList<>();


    public UserViewModel() {
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


    public String getDiabetesMedication() {
        return diabetesMedication;
    }

    public void setDiabetesMedication(String diabetesMedication) {
        this.diabetesMedication = diabetesMedication;
    }

    public List<Medication> getDiabetesList() {
        return diabetesList;
    }

    public void setDiabetesList(List<Medication> diabetesList) {
        this.diabetesList = diabetesList;
    }

    public String getHearthBeatMedication() {
        return hearthBeatMedication;
    }

    public void setHearthBeatMedication(String hearthBeatMedication) {
        this.hearthBeatMedication = hearthBeatMedication;
    }

    public List<Medication> getHearthBeatList() {
        return hearthBeatList;
    }

    public void setHearthBeatList(List<Medication> hearthBeatList) {
        this.hearthBeatList = hearthBeatList;
    }

    public static class Intolerance implements Serializable {
        private Integer id;
        private String name;


        public Intolerance(String name) {
            this.name = name;
        }

        public Intolerance(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Surgery implements Serializable {
        private Integer id;
        private String name;


        public Surgery(String name) {
            this.name = name;
        }

        public Surgery(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Allergy implements Serializable {
        private Integer id;
        private String name;

        public Allergy(String name) {
            this.name = name;
        }

        public Allergy(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Medication implements Serializable {
        private Integer id;
        private float value;
        private float date;

        public Medication(float value, float date) {
            this.value = value;
            this.date = date;
        }

        public Medication(Integer id, float value, float date) {
            this.id = id;
            this.value = value;
            this.date = date;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public float getDate() {
            return date;
        }

        public void setDate(float date) {
            this.date = date;
        }
    }
}

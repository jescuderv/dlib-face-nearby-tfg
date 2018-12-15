package es.jescuderv.unex.facetrackernearbytfg.ui.viewmodel;

import java.io.Serializable;
import java.util.List;

public class UserMedicalInfoViewModel implements Serializable {

    private String bloodType;
    private List<Intolerance> intoleranceList;
    private List<Surgery> surgeryList;
    private List<Allergy> allergyList;

    public UserMedicalInfoViewModel() {
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
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


    public class Intolerance {
        private Integer id;
        private String name;

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

    public class Surgery {
        private Integer id;
        private String name;

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

    public static class Allergy {
        private Integer id;
        private String name;

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
}

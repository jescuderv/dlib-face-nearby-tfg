package es.jescuderv.unex.facetrackernearbytfg.domain.model;

public class Medication {

    private Integer id;
    private float value;
    private float date;

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

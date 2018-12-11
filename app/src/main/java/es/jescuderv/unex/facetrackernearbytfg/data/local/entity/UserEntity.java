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
}
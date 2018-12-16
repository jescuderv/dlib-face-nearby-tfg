package es.jescuderv.unex.facetrackernearbytfg.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "user_id"))
public final class AllergyEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "user_id")
    private Long userId;

    public AllergyEntity() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

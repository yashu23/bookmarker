package com.lucky5.bookmarker.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project      : bookmarker
 * Name         : com.lucky5.bookmarker.model.Record.java
 * Author       : yashpalrawat
 * Created      : 7/09/2018 22:10
 * Description  : Model class for information record
 */
public class Record {

    @Id
    private String id;

    @NotEmpty(message = "info cant be blank")
    private String info;

    private List<String> tags;
    private Date lastUpdated;
    private Date creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + getId() + '\'' +
                ", info='" + getInfo() + '\'' +
                ", tags=" + getTags() +
                ", lastUpdated=" + getLastUpdated() +
                ", creationDate=" + getCreationDate() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return id != null ? id.equals(record.id) : record.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}


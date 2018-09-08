package com.lucky5.bookmarker.model;

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

    private String id;
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
                "id='" + id + '\'' +
                ", info='" + info + '\'' +
                ", tags=" + tags +
                ", lastUpdated=" + lastUpdated +
                ", creationDate=" + creationDate +
                '}';
    }
}

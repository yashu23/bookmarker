package com.lucky5.bookmarker.services;

import com.lucky5.bookmarker.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project      : bookmarker
 * Name         : com.lucky5.bookmarker.services.BookmarkerServiceImpl.java
 * Author       : yashpalrawat
 * Created      : 7/09/2018 22:22
 * Description  : Book marker service implementation
 */
@Service
public class BookmarkerServiceImpl implements BookmarkerService {

    private final Map<String, Record> records = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(BookmarkerServiceImpl.class);

    @Override
    public boolean addRecord(String info, List<String> tags) {

        // If no valid information present then it should not be added to bookmark store
        if ( null == info || info.trim().length() == 0 )
            return false;
        
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setInfo(info);

        Date date = new Date();

        record.setCreationDate(date);
        record.setLastUpdated(date);

        if (tags == null)
            record.setTags(new ArrayList<>());
        else
            record.setTags(tags);

        records.put(record.getId(), record);

        log.debug("record {} added successfully", record);

        return true;
    }

    @Override
    public boolean deleteRecord(String id) {
        if (records.containsKey(id)) {
            records.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Record> getAllRecords() {
        return new ArrayList<>(records.values());
    }

    @Override
    public List<Record> getFilteredRecord(String tag) {

        if (null == tag || tag.trim().length() == 0) {
            return getAllRecords();
        } else {
            return records.values().stream()
                    .filter((value) -> value.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean addTag(String id, String tag) {
        Record record = records.get(id);
        if (record != null) {

            if (tag != null && tag.trim().length() > 0) {
                record.getTags().add(tag);
                record.setLastUpdated(new Date());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean removeTag(String id, String tag) {
        Record record = records.get(id);
        if (record != null) {

            if (tag != null && tag.trim().length() > 0) {
                record.getTags().remove(tag);
                record.setLastUpdated(new Date());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

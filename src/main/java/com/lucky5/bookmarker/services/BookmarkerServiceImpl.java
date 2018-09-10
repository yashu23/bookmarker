package com.lucky5.bookmarker.services;

import com.lucky5.bookmarker.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * Add a bookmark record to inventory.
     *
     * @param info - Information to be stored
     * @param tags - Information tags
     *
     * @return Id of newly created record.
     */
    @Override
    public String addRecord(String info, List<String> tags) {

        // If no valid information present then it should not be added to bookmark store
        if ( null == info || info.trim().length() == 0 )
            throw new IllegalArgumentException("info cant be blank or null");
        
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

        return record.getId();
    }


    /**
     * Delete record from inventory
     *
     * @param id - id of record to be deleted
     *
     * @return - true if delete was successful, else false
     */
    @Override
    public boolean deleteRecord(String id) {
        if (records.containsKey(id)) {
            records.remove(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns all records present in inventory.
     *
     * @return - {@link List} list of records {@link Record} present in the inventory.
     */
    @Override
    public List<Record> getAllRecords() {
        return new ArrayList<>(records.values());
    }


    /**
     * Filter records based on input tag.
     *
     * @param tag - tags to be used for filtering
     *
     * @return - {@link List} list of records {@link Record} present in the filtered response.
     */
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


    /**
     * Update record information
     *
     * @param record - {@link Record}
     *
     * @return - true if updated else false
     */
    @Override
    public boolean updateRecord(Record record) {

        if (record == null || StringUtils.isEmpty(record.getId())
                || records.get(record.getId()) == null) {
            throw new IllegalArgumentException("invalid record");
        }

        Record originalRecord = records.get(record.getId());

        log.debug("original record {}", originalRecord);

        if (!StringUtils.isEmpty(record.getInfo())) {

            originalRecord.setInfo(record.getInfo());

        }

        updateTags(originalRecord, record.getTags());

        originalRecord.setLastUpdated(new Date());

        log.debug("updated record {}", originalRecord);

        return true;
    }

    /**
     * Get record by id.
     *
     * @param id - Id of record
     *
     * @return - Record details
     */
    @Override
    public Record getRecord(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("info cant be blank or null");
        }
        return records.get(id);
    }


    /**
     * Update tag on input record.
     *
     * @param record - Record to be updated
     * @param tags - List of tags to be attached to record
     *
     * @return true if updated, else false
     */
    @Override
    public boolean updateTags(Record record, List<String> tags) {

        if (record == null || StringUtils.isEmpty(record.getId())
                || StringUtils.isEmpty(record.getInfo())) {
            throw new IllegalArgumentException("invalid record id");
        }

        if (tags != null && tags.size() > 0) {
            record.setTags(tags);
            return true;
        } else {
            return false;
        }

    }
}

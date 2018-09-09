package com.lucky5.bookmarker.services;

import com.lucky5.bookmarker.model.Record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project      : bookmarker
 * Name         : com.lucky5.bookmarker.services.BookmarkerService.java
 * Author       : yashpalrawat
 * Created      : 7/09/2018 22:14
 * Description  : Interface defining service methods of book marker service
 */
public interface BookmarkerService {

    String addRecord(final String info, final List<String> tags);

    boolean deleteRecord(final String id);

    List<Record> getAllRecords();

    List<Record> getFilteredRecord(final String tag);

    boolean updateRecord(final Record record);

    Record getRecord(final String id);

    boolean updateTags(Record record, List<String> tags);
}

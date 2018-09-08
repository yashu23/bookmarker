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

    public boolean addRecord(final String info, final List<String> tags);

    public boolean deleteRecord(final String id);

    public List<Record> getAllRecords();

    public List<Record> getFilteredRecord(final String tag);

    public boolean addTag(final String id, final String tag);

    public boolean removeTag(final String id, final String tag);
}

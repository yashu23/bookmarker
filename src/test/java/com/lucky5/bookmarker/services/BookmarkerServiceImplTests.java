package com.lucky5.bookmarker.services;

import com.lucky5.bookmarker.model.Record;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project      : bookmarker
 * Name         : com.lucky5.bookmarker.services.BookmarkerServiceImplTests.java
 * Author       : yashpalrawat
 * Created      : 7/09/2018 22:33
 * Description  : Junit test cases for testing book marker service
 */

@RunWith(JUnit4.class)
public class BookmarkerServiceImplTests {

    private BookmarkerService bookmarkerService = new BookmarkerServiceImpl();

    @Before
    public void setup() {
        bookmarkerService.getAllRecords().forEach((record) -> bookmarkerService.deleteRecord(record.getId()));
    }

    @Test
    public void addRecord_WithBlankInfoShouldFail() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertFalse("add record operation fails",
                bookmarkerService.addRecord("", Arrays.asList("test")));

        Assert.assertEquals("no records added",
                initialSize,
                bookmarkerService.getAllRecords().size());

    }

    @Test
    public void addRecord_WithNullInfoShouldFail() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertFalse("add record operation fails",
                bookmarkerService.addRecord(null, Arrays.asList("test")));

        Assert.assertEquals("no records added",
                initialSize,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void addRecord_WithValidInfoEmptyListTagsShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertTrue("add record operation fails",
                bookmarkerService.addRecord("www.google.com", Arrays.asList("google")));

        Assert.assertEquals("record added successfully",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void addRecord_WithValidInfoNullTagsShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertTrue("add record operation fails",
                bookmarkerService.addRecord("www.google.com", Arrays.asList("google")));

        Assert.assertEquals("record added successfully",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void addRecord_WithValidInfoValidTagsShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertTrue("add record operation fails",
                bookmarkerService.addRecord("www.google.com", Arrays.asList("google")));

        Assert.assertEquals("record added successfully",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithValidIdShouldBeSuccessful() {

        bookmarkerService.addRecord("www.google.com", null);
        
        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertTrue("delete record operation fails",
                bookmarkerService.deleteRecord(record.getId()));

        Assert.assertEquals("record added successfully",
                0,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithInvalidIdShouldFail() {
        bookmarkerService.addRecord("www.google.com", null);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("delete record operation fails",
                bookmarkerService.deleteRecord("test"));

        Assert.assertEquals("record added successfully",
                1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithBlankIdShouldFail() {

        bookmarkerService.addRecord("www.google.com", null);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("delete record operation fails",
                bookmarkerService.deleteRecord(""));

        Assert.assertEquals("record added successfully",
                1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithNullIdShouldFail() {

        bookmarkerService.addRecord("www.google.com", null);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("delete record operation fails",
                bookmarkerService.deleteRecord(null));

        Assert.assertEquals("record added successfully",
                1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void getAllRecords_NoRecordsAddedShouldReturnNoRecord() {

        Assert.assertEquals("records present",
                0,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void getAllRecords_OneRecordAddedShouldReturnOneRecord() {

        bookmarkerService.addRecord("www.google.com", null);
        Assert.assertEquals("records present",
                1,
                bookmarkerService.getAllRecords().size());

        Record record = bookmarkerService.getAllRecords().get(0);
    }

    @Test
    public void getFilteredRecord_NoRecordsAddedShouldReturnNoRecord() {

        Assert.assertEquals("records present",
                0,
                bookmarkerService.getFilteredRecord("test").size());
    }

    @Test
    public void getFilteredRecord_RecordsAddedWithNullTagShouldReturnAllRecords() {

        bookmarkerService.addRecord("www.google.com", null);
        bookmarkerService.addRecord("www.apple.com", null);

        Assert.assertEquals("records present",
                2,
                bookmarkerService.getFilteredRecord(null).size());
    }

    @Test
    public void getFilteredRecord_RecordsAddedWithBlankTagShouldReturnAllRecords() {

        bookmarkerService.addRecord("www.google.com", null);
        bookmarkerService.addRecord("www.apple.com", null);

        Assert.assertEquals("records present",
                2,
                bookmarkerService.getFilteredRecord("").size());
    }

    @Test
    public void getFilteredRecord_RecordsAddedWithValidTagShouldReturnOnlyMatchingRecords() {

        bookmarkerService.addRecord("www.google.com", Arrays.asList("test","google"));
        bookmarkerService.addRecord("www.apple.com", Arrays.asList("dev", "apple"));

        Assert.assertEquals("records present",
                1,
                bookmarkerService.getFilteredRecord("google").size());

        Assert.assertEquals("matching record not fetched",
                "www.google.com",
                bookmarkerService.getFilteredRecord("google").get(0).getInfo());
    }

    @Test
    public void getFilteredRecord_RecordsAddedWithInvalidTagShouldReturnNoRecords() {

        bookmarkerService.addRecord("www.google.com", Arrays.asList("test, google"));
        bookmarkerService.addRecord("www.apple.com", Arrays.asList("dev, apple"));

        Assert.assertEquals("records present",
                0,
                bookmarkerService.getFilteredRecord("google1").size());
    }

    @Test
    public void addTag_NullIdWithValidTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("records present",
                bookmarkerService.addTag(null, "valid"));

        Assert.assertEquals("records changed",
                1,
                bookmarkerService.getAllRecords().size());

        record = bookmarkerService.getAllRecords().get(0);

        Assert.assertEquals("tags changed",
                tags,
                record.getTags());

    }

    @Test
    public void addTag_BlankIdWithValidTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("records present",
                bookmarkerService.addTag("", "valid"));

        Assert.assertEquals("records changed",
                1,
                bookmarkerService.getAllRecords().size());

        Assert.assertEquals("tags changed",
                tags,
                record.getTags());
    }

    @Test
    public void addTag_ValidIdWithBlankTagShouldBeIgnored() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("records present",
                bookmarkerService.addTag(record.getId(), ""));

        Assert.assertEquals("tags changed",
                tags,
                record.getTags());
    }

    @Test
    public void addTag_ValidIdWithNullTagShouldBeIgnored() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);
        
        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("records present",
                bookmarkerService.addTag(record.getId(), null));

        Assert.assertEquals("tags changed",
                tags,
                record.getTags());
    }

    @Test
    public void addTag_ValidIdWithValidTagShouldBeSuccessful() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);
        List<String> originalTags = record.getTags();

        Assert.assertTrue("records present",
                bookmarkerService.addTag(record.getId(), "test2"));

        tags.add("test2");

        Assert.assertEquals("tags changed",
                tags,
                record.getTags());
    }

    @Test
    public void removeTag_NullIdWithValidTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);
        List<String> originalTags = record.getTags();

        Assert.assertFalse("records present",
                bookmarkerService.removeTag(null, "valid"));

        Assert.assertEquals("records changed",
                1,
                bookmarkerService.getAllRecords().size());

        record = bookmarkerService.getAllRecords().get(0);

        Assert.assertEquals("tags changed",
                originalTags,
                record.getTags());
    }

    @Test
    public void removeTag_BlankIdWithValidTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);
        List<String> originalTags = record.getTags();

        Assert.assertFalse("records present",
                bookmarkerService.removeTag("", "valid"));

        Assert.assertEquals("records changed",
                1,
                bookmarkerService.getAllRecords().size());

        record = bookmarkerService.getAllRecords().get(0);

        Assert.assertEquals("tags changed",
                originalTags,
                record.getTags());

    }

    @Test
    public void removeTag_ValidIdWithBlankTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);
        List<String> originalTags = record.getTags();

        Assert.assertFalse("records present",
                bookmarkerService.removeTag(record.getId(), ""));

        record = bookmarkerService.getAllRecords().get(0);

        Assert.assertEquals("tags changed",
                originalTags,
                record.getTags());

    }

    @Test
    public void removeTag_ValidIdWithNullTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");

        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);
        List<String> originalTags = record.getTags();

        Assert.assertFalse("records present",
                bookmarkerService.removeTag(record.getId(), null));

        record = bookmarkerService.getAllRecords().get(0);

        Assert.assertEquals("tags changed",
                originalTags,
                record.getTags());
    }

    @Test
    public void removeTag_ValidIdWithValidTagShouldBeSuccessful() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");

        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertTrue("records present",
                bookmarkerService.removeTag(record.getId(), "google"));

        Assert.assertEquals("tags unchanged",
                Arrays.asList("apple"),
                record.getTags());
    }
}

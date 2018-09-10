package com.lucky5.bookmarker.services;

import com.lucky5.bookmarker.model.Record;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        bookmarkerService.getAllRecords().forEach((record) -> bookmarkerService.deleteRecord(record.getId()));
    }

    @Test
    public void addRecord_WithBlankInfoShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("info cant be blank or null");

        bookmarkerService.addRecord("", new ArrayList<>());

    }

    @Test
    public void addRecord_WithNullInfoShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("info cant be blank or null");

        bookmarkerService.addRecord(null, new ArrayList<>());
    }

    @Test
    public void addRecord_WithValidInfoEmptyListTagsShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertNotNull("add record operation failed",
                bookmarkerService.addRecord("www.google.com", new ArrayList<>()));

        Assert.assertEquals("record added successfully",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void addRecord_WithValidInfoNullTagsShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertNotNull("add record operation failed",
                bookmarkerService.addRecord("www.google.com", null));

        Assert.assertEquals("record added successfully",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void addRecord_WithValidInfoValidTagsShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();

        List<String> tags = new ArrayList<>();
        tags.add("google");

        Assert.assertNotNull("add record operation failed",
                bookmarkerService.addRecord("www.google.com", tags));

        Assert.assertEquals("record added successfully",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithValidIdShouldBeSuccessful() {

        int initialSize = bookmarkerService.getAllRecords().size();
        
        bookmarkerService.addRecord("www.google.com", null);
        
        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertTrue("delete record operation fails",
                bookmarkerService.deleteRecord(record.getId()));

        Assert.assertEquals("record size intact",
                initialSize,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithInvalidIdShouldFail() {

        bookmarkerService.addRecord("www.google.com", null);

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertFalse("delete record operation fails",
                bookmarkerService.deleteRecord("test"));

        Assert.assertEquals("record size intact",
                initialSize,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithBlankIdShouldFail() {

        bookmarkerService.addRecord("www.google.com", null);

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertFalse("delete record operation fails",
                bookmarkerService.deleteRecord(""));

        Assert.assertEquals("record added successfully",
                initialSize,
                bookmarkerService.getAllRecords().size());
    }

    @Test
    public void deleteRecord_WithNullIdShouldFail() {

        bookmarkerService.addRecord("www.google.com", null);

        int initialSize = bookmarkerService.getAllRecords().size();

        Assert.assertFalse("delete record operation fails",
                bookmarkerService.deleteRecord(null));

        Assert.assertEquals("record added successfully",
                initialSize,
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

        int initialSize = bookmarkerService.getAllRecords().size();

        bookmarkerService.addRecord("www.google.com", null);

        Assert.assertEquals("records present",
                initialSize + 1,
                bookmarkerService.getAllRecords().size());

    }

    @Test
    public void getFilteredRecord_NoMatchingTagShouldReturnNoRecord() {

        bookmarkerService.addRecord("www.google.com", null);

        Assert.assertEquals("records present",
                0,
                bookmarkerService.getFilteredRecord("test").size());
    }

    @Test
    public void getFilteredRecord_NullInputTagShouldReturnAllRecords() {

        bookmarkerService.addRecord("www.google.com", null);
        bookmarkerService.addRecord("www.apple.com", null);

        Assert.assertEquals("records present",
                2,
                bookmarkerService.getFilteredRecord(null).size());
    }

    @Test
    public void getFilteredRecord_BlankInputTagShouldReturnAllRecords() {

        bookmarkerService.addRecord("www.google.com", null);
        bookmarkerService.addRecord("www.apple.com", null);

        Assert.assertEquals("records present",
                2,
                bookmarkerService.getFilteredRecord("").size());
    }

    @Test
    public void getFilteredRecord_ValidTagShouldReturnOnlyMatchingRecords() {

        bookmarkerService.addRecord("www.google.com", Arrays.asList("test", "google"));
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

        bookmarkerService.addRecord("www.google.com", Arrays.asList("test", "google"));
        bookmarkerService.addRecord("www.apple.com", Arrays.asList("dev", "apple"));

        Assert.assertEquals("records present",
                0,
                bookmarkerService.getFilteredRecord("google1").size());
    }

    @Test
    public void updateRecord_WithNullRecordShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record");

        Assert.assertFalse("records present",
                bookmarkerService.updateRecord(null));

    }

    @Test
    public void updateRecord_WithRecordHavingBlankIdShouldFail() {

        Record record = new Record();
        record.setId("");

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record");

        bookmarkerService.updateRecord(record);
    }

    @Test
    public void updateRecord_WithRecordHavingNullIdShouldFail() {

        Record record = new Record();
        record.setId(null);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record");

        bookmarkerService.updateRecord(record);
    }


    @Test
    public void updateRecord_WithBlankOrNullInfoRecordShouldFail() {

        Record record = new Record();
        record.setId("test");

        record.setInfo("");

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record");

        bookmarkerService.updateRecord(record);

        record.setInfo("");

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record");

        bookmarkerService.updateRecord(record);
    }

    @Test
    public void getRecord_WithBlankIdShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("info cant be blank or null");

        bookmarkerService.getRecord("");
    }

    @Test
    public void getRecord_WithNullIdShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("info cant be blank or null");

        bookmarkerService.getRecord(null);
    }

    @Test
    public void getRecord_WithValidIdShouldBeSuccessful() {

        bookmarkerService.addRecord("www.google.com", null);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertEquals("search mismatch",
                record,
                bookmarkerService.getRecord(record.getId()));
    }


    @Test
    public void updateTags_ValidRecordWithValidTagShouldBeSuccessful() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");
        
        // Create record with two tags present
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        tags.add("test");

        Assert.assertTrue("tag update failed",
                bookmarkerService.updateTags(record, tags));

        Assert.assertEquals("tags changed",
                tags,
                bookmarkerService.getRecord(record.getId()).getTags());
    }

    @Test
    public void updateTags_ValidRecordWithNewTagShouldOverride() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");

        // Create record with two tags present
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        tags.clear();
        tags.add("test1");
        tags.add("test2");

        Assert.assertTrue("tag update failed",
                bookmarkerService.updateTags(record, tags));

        Assert.assertEquals("tags changed",
                tags,
                bookmarkerService.getRecord(record.getId()).getTags());
    }

    @Test
    public void updateTags_ValidRecordWithNullTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");

        // Create record with two tags present
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("tag updated",
                bookmarkerService.updateTags(record, null));

        Assert.assertEquals("tags changed",
                tags,
                bookmarkerService.getRecord(record.getId()).getTags());

    }

    @Test
    public void updateTags_ValidRecordWithEmptyTagShouldFail() {

        List<String> tags = new ArrayList<>();
        tags.add("apple");
        tags.add("google");

        // Create record with two tags present
        bookmarkerService.addRecord("www.google.com", tags);

        Record record = bookmarkerService.getAllRecords().get(0);

        Assert.assertFalse("tag updated",
                bookmarkerService.updateTags(record, new ArrayList<>()));

        Assert.assertEquals("tags changed",
                tags,
                bookmarkerService.getRecord(record.getId()).getTags());

    }

    @Test
    public void updateTags_BlankRecordWithValidTagShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record id");

        bookmarkerService.updateTags(new Record(), Arrays.asList("test1", "test2"));
    }

    @Test
    public void updateTags_NullRecordWithValidTagShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record id");

        bookmarkerService.updateTags(null, Arrays.asList("test1", "test2"));
    }

    @Test
    public void updateTags_InvalidRecordWithValidTagShouldFail() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid record id");

        Record record = new Record();
        record.setId("--");
        bookmarkerService.updateTags(record, Arrays.asList("test1", "test2"));
    }
}

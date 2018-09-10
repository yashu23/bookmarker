package com.lucky5.bookmarker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucky5.bookmarker.model.Record;
import com.lucky5.bookmarker.services.BookmarkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class BookmarkControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkerService bookmarkerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test_addValidRecordShouldPass() throws Exception {

        when(bookmarkerService.addRecord(any(), any())).thenReturn("test");

        mockMvc.perform(post("/records").content("{ \"info\" : \"test\" }")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("{\"id\": \"test\"}"));
    }

    @Test
    public void test_addNullInfoRecordShouldFail() throws Exception {

        when(bookmarkerService.addRecord(any(), any())).thenThrow(new IllegalArgumentException("invalid input"));

        mockMvc.perform(post("/records").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_addBlankInfoRecordShouldFail() throws Exception {

        when(bookmarkerService.addRecord(any(), any())).thenThrow(new IllegalArgumentException("invalid input"));

        mockMvc.perform(post("/records").content("{ \"info\" : \"\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_getRecordShouldPass() throws Exception {

        Record record = new Record();
        record.setInfo("test");
        record.setId("123");

        when(bookmarkerService.getRecord("123")).thenReturn(record);

        mockMvc.perform(get("/records/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(record)));
    }

    @Test
    public void test_getRecordNotPresentShouldFail() throws Exception {

        when(bookmarkerService.getRecord("456")).thenReturn(null);

        mockMvc.perform(get("/records/456"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_getAllRecordsShouldPass() throws Exception {

        Record record = new Record();
        record.setInfo("test");
        record.setId("678");

        List<Record> records = new ArrayList<>();
        records.add(record);

        when(bookmarkerService.getAllRecords()).thenReturn(records);

        mockMvc.perform(get("/records"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }


    @Test
    public void test_updateRecordShouldPass() throws Exception {

        // mock updated record returned from service layer
        Record record = new Record();
        record.setInfo("test");
        record.setId("test");

        List<String> tags = new ArrayList<>();
        tags.add("google");
        tags.add("apple");
        tags.add("microsoft");

        record.setTags(tags);

        // Now we need to mock response for update operation
        when(bookmarkerService.updateRecord(record)).thenReturn(true);


        mockMvc.perform(put("/records/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk());

    }

    @Test
    public void test_updateRecordWithNoSetIdShouldPass() throws Exception {

        // Data setup for mock
        Record record = new Record();
        record.setInfo("test");
        record.setId("test");

        List<String> tags = new ArrayList<>();
        tags.add("google");
        tags.add("apple");
        tags.add("microsoft");

        record.setTags(tags);

        // Now we need to mock response for update operation
        when(bookmarkerService.updateRecord(record)).thenReturn(true);

        // Input record with no id set in the path
        Record inputRecord = new Record();
        inputRecord.setInfo("test");

        List<String> inputTags = new ArrayList<>();
        inputTags.add("google");
        inputTags.add("apple");
        inputTags.add("microsoft");

        inputRecord.setTags(inputTags);

        // If request is made with no id then an exception would be thrown
        when(bookmarkerService.updateRecord(inputRecord)).thenThrow(new IllegalArgumentException("invalid input"));

        mockMvc.perform(put("/records/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(inputRecord)))
                .andExpect(status().isOk());

    }

    @Test
    public void test_updateRecordWithInvalidInfoShouldFail() throws Exception {

        // Now we need to mock response for update operation
        when(bookmarkerService.updateRecord(any())).thenReturn(false);


        mockMvc.perform(put("/records/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{}"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void test_deleteRecordWithValidIdShouldPass() throws Exception {

        when(bookmarkerService.deleteRecord("test")).thenReturn(true);

        mockMvc.perform(delete("/records/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteRecordWithInvalidIdShouldFail() throws Exception {

        when(bookmarkerService.deleteRecord("test")).thenReturn(false);

        mockMvc.perform(delete("/records/test"))
                .andExpect(status().is4xxClientError());
    }

}

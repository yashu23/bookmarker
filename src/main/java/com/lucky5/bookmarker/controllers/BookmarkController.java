package com.lucky5.bookmarker.controllers;

import com.lucky5.bookmarker.model.Record;
import com.lucky5.bookmarker.services.BookmarkerService;
import io.prometheus.client.Histogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookmarkController {

    private BookmarkerService bookmarkerService;

    private Logger log = LoggerFactory.getLogger(BookmarkController.class);

    private static final Histogram operationHistogram =
            Histogram.build()
                    .name("bookmark_e2e_operation")
                    .help("Metrics for tracing supported operation response time")
                    .labelNames("type", "result", "statusCode")
                    .register();


    public BookmarkController(BookmarkerService bookmarkerService) {
        this.bookmarkerService = bookmarkerService;
    }

    @PostMapping(value = "/records", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> addRecord(@Valid @RequestBody Record record) {

        log.info("entering addRecord");
        final String responseId;
        final long startTime = System.nanoTime();
        try {
            responseId = bookmarkerService.addRecord(record.getInfo(), record.getTags());

            operationHistogram.labels("add", "pass", HttpStatus.OK.toString())
                    .observe((System.nanoTime() - startTime) / 1000000);

        } catch (IllegalArgumentException ex) {

            log.error("invalid input received {}", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("leaving addRecord");

        return new ResponseEntity<>("{\"id\": \"" + responseId + "\"}", HttpStatus.OK);
    }


    @GetMapping(value = "/records/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Record> getRecord(@PathVariable String id) {

        log.info("entering getRecord");
        final long startTime = System.nanoTime();
        Record record;
        try {
            record = bookmarkerService.getRecord(id);

            // No data found
            if (record == null) {
                operationHistogram.labels("get", "pass", HttpStatus.NOT_FOUND.toString())
                        .observe((System.nanoTime() - startTime) / 1000000);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                operationHistogram.labels("get", "pass", HttpStatus.OK.toString())
                        .observe((System.nanoTime() - startTime) / 1000000);

                return new ResponseEntity<>(record, HttpStatus.OK);
            }
        } catch (IllegalArgumentException ex) {

            log.error("invalid input received {}", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        finally {
            log.info("leaving getRecord");
        }
    }

    @GetMapping(value = "/records", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Record>> getAllRecords() {
        final long startTime = System.nanoTime();
        log.info("entering getAllRecords");

        List<Record> results = bookmarkerService.getAllRecords();

        operationHistogram.labels("getAll", "pass", HttpStatus.OK.toString())
                .observe((System.nanoTime() - startTime) / 1000000);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping(value = "/records/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable String id, @Valid @RequestBody Record record) {

        log.info("entering updateRecord");
        final long startTime = System.nanoTime();

        try {
            record.setId(id);
            boolean result = bookmarkerService.updateRecord(record);

            // No data found
            if (!result) {
                operationHistogram.labels("update", "pass", HttpStatus.NOT_FOUND.toString())
                        .observe((System.nanoTime() - startTime) / 1000000);

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                operationHistogram.labels("update", "fail", HttpStatus.OK.toString())
                        .observe((System.nanoTime() - startTime) / 1000000);

                return new ResponseEntity<>(record, HttpStatus.OK);
            }
        } catch (IllegalArgumentException ex) {
            operationHistogram.labels("update", "error", HttpStatus.BAD_REQUEST.toString())
                    .observe((System.nanoTime() - startTime) / 1000000);

            log.error("invalid input received {}", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        finally {
            log.info("leaving updateRecord");
        }
    }


    @DeleteMapping(value = "/records/{id}")
    public ResponseEntity<String> deleteRecord(@Valid @RequestBody Record record) {

        log.info("entering addRecord");
        final String responseId;
        final long startTime = System.nanoTime();
        try {
            responseId = bookmarkerService.addRecord(record.getInfo(), record.getTags());

            operationHistogram.labels("add", "pass", HttpStatus.OK.toString())
                    .observe((System.nanoTime() - startTime) / 1000000);

        } catch (IllegalArgumentException ex) {

            log.error("invalid input received {}", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("leaving addRecord");

        return new ResponseEntity<>("{\"id\": \"" + responseId + "\"}", HttpStatus.OK);
    }
}

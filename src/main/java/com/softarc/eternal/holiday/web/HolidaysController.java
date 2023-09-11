package com.softarc.eternal.holiday.web;

import com.softarc.eternal.holiday.logic.Holiday;
import com.softarc.eternal.holiday.logic.HolidayAdder;
import com.softarc.eternal.holiday.logic.HolidayFinder;
import com.softarc.eternal.holiday.logic.HolidayUpdater;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/holiday")
@RestController
@Tag(name = "holidays")
public class HolidaysController {

  private final HolidayAdder adder;
  private final HolidayFinder finder;
  private final HolidayUpdater updater;

  public HolidaysController(HolidayAdder adder, HolidayFinder finder, HolidayUpdater updater) {
    this.adder = adder;
    this.finder = finder;
    this.updater = updater;
  }

  @GetMapping
  @Operation(operationId = "listHolidays")
  public List<Holiday> index() {
    return this.finder.find();
  }

  @GetMapping("{id}")
  public Holiday find(@PathVariable("id") Long id) {
    return this.finder.byId(id);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Holiday add(
    @RequestPart HolidayAdder.AddRequest addRequest,
    @RequestPart MultipartFile cover
    ) {
    return adder.add(addRequest);
  }

  @PutMapping()
  public void update(@RequestBody HolidayUpdater.UpdateRequest updateRequest) {
    updater.update(updateRequest);
  }

  @DeleteMapping("{id}")
  public void remove(@PathVariable("id") Long id) {
    updater.remove(id);
  }

  @GetMapping(
    value = "/{id}/cover",
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
  )
  public ResponseEntity<Resource> viewCover(@PathVariable("id") Long id) {
    return new ResponseEntity<>(finder.getCover(id), new HttpHeaders(), HttpStatus.OK);
  }
}

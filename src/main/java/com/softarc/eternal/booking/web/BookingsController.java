package com.softarc.eternal.booking.web;

import com.softarc.eternal.web.api.BookingsApi;
import com.softarc.eternal.web.model.Booking;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingsController implements BookingsApi {
  @Override
  public ResponseEntity<List<Booking>> findAll() {
    return ResponseEntity.ok(Collections.emptyList());
  }
}

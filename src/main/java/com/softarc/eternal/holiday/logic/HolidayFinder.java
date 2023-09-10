package com.softarc.eternal.holiday.logic;

import com.softarc.eternal.exception.IdNotFoundException;
import com.softarc.eternal.holiday.data.HolidaysRepository;
import com.softarc.eternal.holiday.logic.internal.HolidaysMapper;
import java.nio.file.Path;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class HolidayFinder {
  private final HolidaysMapper mapper;
  private final HolidaysRepository repository;

   HolidayFinder(HolidaysMapper mapper, HolidaysRepository repository) {
    this.mapper = mapper;
    this.repository = repository;
  }

  public Holiday byId(Long id) {
    return repository.findById(id).map(mapper::holidayToResponse).orElseThrow(IdNotFoundException::new);
  }

  public List<Holiday> find() {
    return repository.findAll().stream().map(mapper::holidayToResponse).toList();
  }

  public FileSystemResource getCover(Long id) {
    var holiday = repository.findById(id).orElseThrow(IdNotFoundException::new);
    var cover = holiday.getCoverPath().orElseThrow();
    var file = Path.of("", "filestore", cover);
    return new FileSystemResource(file);
  }
}

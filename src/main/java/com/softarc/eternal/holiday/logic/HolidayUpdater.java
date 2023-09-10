package com.softarc.eternal.holiday.logic;

import com.softarc.eternal.exception.IdNotFoundException;
import com.softarc.eternal.holiday.data.HolidaysRepository;
import com.softarc.eternal.holiday.logic.internal.HolidaysMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class HolidayUpdater {
  private final HolidaysRepository repository;
  private final HolidaysMapper mapper;

  HolidayUpdater(HolidaysRepository repository, HolidaysMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public static record UpdateRequest(
      @NotNull Long id, @NotBlank String name, @NotBlank String description) {}

  @Transactional
  public Holiday update(UpdateRequest updateRequest) {
    var holiday = repository.findById(updateRequest.id()).orElseThrow(IdNotFoundException::new);
    holiday.setName(updateRequest.name());
    holiday.setDescription(updateRequest.description());
    return mapper.holidayToResponse(repository.save(holiday));
  }

  @Transactional public void remove(Long id) {
    var holiday = repository.findById(id).orElseThrow(IdNotFoundException::new);
    repository.delete(holiday);
  }
}

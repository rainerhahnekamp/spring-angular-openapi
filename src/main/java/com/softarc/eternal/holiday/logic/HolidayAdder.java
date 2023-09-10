package com.softarc.eternal.holiday.logic;

import com.softarc.eternal.holiday.data.HolidayEntity;
import com.softarc.eternal.holiday.data.HolidaysRepository;
import com.softarc.eternal.holiday.logic.internal.HolidaysMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

@Service
public class HolidayAdder {
  private final HolidaysRepository repository;
  private final HolidaysMapper mapper;

  HolidayAdder(HolidaysRepository repository, HolidaysMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public static record AddRequest(@NotBlank String name, @NotBlank String description) {}

  @Transactional
  public Holiday add(AddRequest addRequest) {
    var holiday = new HolidayEntity();
    holiday.setName(addRequest.name());
    holiday.setDescription(addRequest.description());
    return mapper.holidayToResponse(repository.save(holiday));
  }
}

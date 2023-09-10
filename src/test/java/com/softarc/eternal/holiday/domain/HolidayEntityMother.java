package com.softarc.eternal.holiday.domain;

import com.softarc.eternal.holiday.data.HolidayEntity;

public class HolidayEntityMother extends HolidayEntity {

  private static Long id = 1L;

  public static HolidayEntityBuilder vienna() {
    return HolidayEntity
      .builder()
      .id(++HolidayEntityMother.id)
      .name("Vienna")
      .coverPath("")
      .description("This is a default description");
  }
}

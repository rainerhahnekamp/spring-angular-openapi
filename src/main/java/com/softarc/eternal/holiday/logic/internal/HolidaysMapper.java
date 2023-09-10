package com.softarc.eternal.holiday.logic.internal;

import com.softarc.eternal.holiday.data.HolidayEntity;
import com.softarc.eternal.holiday.logic.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HolidaysMapper {
  @Mapping(target = "hasCover", expression = "java(holidayEntity.getCoverPath() != null)")
  Holiday holidayToResponse(HolidayEntity holidayEntity);
}

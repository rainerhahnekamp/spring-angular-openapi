package com.softarc.eternal.holiday.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepository extends JpaRepository<HolidayEntity, Long> {
}

package com.softarc.eternal.holiday.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Holiday")
public class HolidayEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  private String coverPath;

  public Optional<String> getCoverPath() {
    return Optional.ofNullable(coverPath);
  }
}

package com.softarc.eternal.holiday.logic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Holiday(@NotNull Long id, @NotBlank String name, @NotBlank String description, @NotNull Boolean hasCover) {

}

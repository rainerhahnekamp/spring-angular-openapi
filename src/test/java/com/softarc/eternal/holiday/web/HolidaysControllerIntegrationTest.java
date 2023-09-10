package com.softarc.eternal.holiday.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.softarc.eternal.holiday.data.HolidaysRepository;
import com.softarc.eternal.holiday.logic.HolidayAdder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
class HolidaysControllerIntegrationTest {
  @Container
  static MySQLContainer mySQLContainer= new MySQLContainer("mysql:8.0.30");

  private static final Path destinationPath = Path.of(
    "",
    "filestore",
    "vienna.jpg"
  );

  @Autowired
  HolidaysController controller;

  @Autowired
  HolidaysRepository repository;

  @BeforeEach
  void removeViennaFile() throws IOException {
    if (Files.exists(destinationPath)) {
      Files.delete(destinationPath);
    }
  }

  @Test
  public void testAddHoliday(@Autowired WebTestClient webTestClient)
    throws Exception {
    assertThat(Files.exists(destinationPath))
      .withFailMessage("Cannot start when vienna.jpg exists in filestore")
      .isFalse();

    var addRequest = new HolidayAdder.AddRequest("Vienna", "Capital of Vienna with reminiscents of the Habsburg empire");

    webTestClient
      .post()
      .uri("/api/holidays")
      .contentType(MediaType.MULTIPART_FORM_DATA)
      .bodyValue(addRequest)
      .exchange()
      .expectStatus()
      .isOk();
    webTestClient
      .get()
      .uri("/api/holidays")
      .exchange()
      .expectBody()
      .jsonPath("[0].name")
      .isEqualTo("Vienna");

    assertThat(Files.exists(destinationPath)).isTrue();
  }
}

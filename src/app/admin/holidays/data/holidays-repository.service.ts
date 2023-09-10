import { inject, Injectable, Signal, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Holiday } from '@app/admin/holidays/model';
import { HttpClient } from '@angular/common/http';

export type AddHoliday = {
  name: string;
  description: string;
  cover: File;
};
export type EditHoliday = AddHoliday & { id: number };

@Injectable({ providedIn: 'root' })
export class HolidaysRepository {
  #initialised = false;
  #holidays = signal<Holiday[]>([]);
  #currentHoliday = signal<Holiday | undefined>(undefined);
  currentHoliday = this.#currentHoliday.asReadonly();
  #httpClient = inject(HttpClient);
  #baseUrl = 'http://localhost:8080/api/holidays';

  constructor() {
    this.#refetch();
  }

  get holidays(): Signal<Holiday[]> {
    return this.#holidays.asReadonly();
  }

  async setCurrentId(id: number) {
    this.#currentHoliday.set(undefined);
    const holiday = await firstValueFrom(
      this.#httpClient.get<Holiday>(`${this.#baseUrl}/${id}`),
    );
    this.#currentHoliday.set(holiday);
  }
  async add(holiday: AddHoliday): Promise<void> {
    const { cover, ...holidayDto } = holiday;
    await firstValueFrom(this.#httpClient.post(this.#baseUrl, holidayDto));
    this.#refetch();
  }

  async save(holiday: EditHoliday) {
    const { cover, ...holidayDto } = holiday;
    await firstValueFrom(this.#httpClient.put(this.#baseUrl, holidayDto));
    this.#refetch();
  }

  async remove(id: number): Promise<void> {
    await firstValueFrom(this.#httpClient.delete(`${this.#baseUrl}/${id}`));
    await this.#refetch();
  }

  async #refetch() {
    const holidays = await firstValueFrom(
      this.#httpClient.get<Holiday[]>(`${this.#baseUrl}`),
    );

    this.#holidays.set(
      holidays.map((holiday) => ({
        ...holiday,
        coverLink: `http://localhost:8080/api/holidays/${holiday.id}/cover`,
      })),
    );
  }
}

import { Component, inject } from '@angular/core';
import { HolidaysRepository } from '@app/admin/holidays/data';
import { ActivatedRoute, Router } from '@angular/router';
import { filterDefined } from '@app/shared/ngrx-utils';
import { HolidayDetailComponent, HolidayForm } from '@app/admin/holidays/ui';
import { LetDirective } from '@ngrx/component';
import { MessageService } from '@app/shared/ui-messaging';
import { map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-edit-holiday',
  template:
    '<app-holiday-detail [holiday]="holiday()" (remove)="handleRemove()" (save)="handleSave($event)"></app-holiday-detail>',
  standalone: true,
  imports: [HolidayDetailComponent, LetDirective],
})
export class EditHolidayComponent {
  #id = 0;
  #holidaysRepository = inject(HolidaysRepository);
  protected holiday = this.#holidaysRepository.currentHoliday;
  #route = inject(ActivatedRoute);
  #router = inject(Router);
  #messageService = inject(MessageService);

  constructor() {
    this.#route.paramMap.pipe(
      map((paramMap) => paramMap.get('id')),
      filterDefined,
      switchMap((value) => {
        const id = Number(value);
        this.#id = id;
        return this.#holidaysRepository.setCurrentId(Number(id));
      }),
    );
  }

  handleRemove() {
    this.#holidaysRepository.remove(this.#id);
    this.#messageService.info('Holiday was removed');
    this.#router.navigate(['..'], { relativeTo: this.#route });
  }

  handleSave(holiday: HolidayForm) {
    this.#holidaysRepository.save({ ...holiday, id: this.#id });
    this.#messageService.info('Holiday was saved');
    this.#router.navigate(['..'], { relativeTo: this.#route });
  }
}

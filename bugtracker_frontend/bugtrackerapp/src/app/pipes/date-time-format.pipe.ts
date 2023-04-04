import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateTimeFormat',
})
export class DateTimeFormatPipe implements PipeTransform {
  transform(value: any): any {
    const datePipe = new DatePipe('en-US');
    const formattedDate = datePipe.transform(value, 'dd-MM-yyyy | HH:MM');
    return formattedDate;
  }
}

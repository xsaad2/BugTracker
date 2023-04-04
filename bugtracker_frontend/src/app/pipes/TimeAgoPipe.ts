import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeAgo',
})
export class TimeAgoPipe implements PipeTransform {
  transform(value: Date): string {
    if (!(value instanceof Date)) {
      value = new Date(value);
    }
    const now = new Date();
    const diff = now.getTime() - value.getTime();

    const minute = 60 * 1000;
    const hour = minute * 60;
    const day = hour * 24;
    const month = day * 30;

    if (diff < minute) {
      return 'just now';
    } else if (diff < hour) {
      const minutes = Math.floor(diff / minute);
      return `${minutes} ${minutes > 1 ? 'minutes' : 'minute'} ago`;
    } else if (diff < day) {
      const hours = Math.floor(diff / hour);
      return `${hours} ${hours > 1 ? 'hours' : 'hour'} ago`;
    } else if (diff < month) {
      const days = Math.floor(diff / day);
      return `${days} ${days > 1 ? 'days' : 'day'} ago`;
    } else {
      const months = Math.floor(diff / month);
      return `${months} ${months > 1 ? 'months' : 'month'} ago`;
    }
  }
}

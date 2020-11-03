export class Timer {
  total: number;
  days: number;
  hours: number;
  minutes: number;
  seconds: number;

  constructor(total: number, days: number, hours: number, minutes: number, seconds: number) {
    this.total = total;
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }
}

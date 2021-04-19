package cloudcode.guestbook.frontend;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Controls printing the time that has passed between a given timestamp and now
 */
public interface PrintDuration {

  int DAYS_IN_YEAR = 365;

  /**
   * print the duration since a timestamp in a human-readable way
   * @param prevTimestamp a timestamp representing when a post was made
   *                      represented as ms since epoch
   * @return a short string representing the time since prevTimestamp
   */
  static String print(long prevTimestamp) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime prev = LocalDateTime.ofInstant(
      Instant.ofEpochMilli(prevTimestamp), TimeZone.getDefault().toZoneId());
    Duration duration = Duration.between(now, prev);

    long num;
    String unit;
    if (Math.abs(duration.toDays()) >= DAYS_IN_YEAR) {
      num = Math.abs(duration.toDays()) / DAYS_IN_YEAR;
      unit = num == 1 ? "year" : "years";
    } else if (Math.abs(duration.toDays()) > 0) {
      num = Math.abs(duration.toDays());
      unit = num == 1 ? "day" : "days";
    } else if (Math.abs(duration.toHours()) > 0) {
      num = Math.abs(duration.toHours());
      unit = num == 1 ? "hour" : "hours";
    } else if (Math.abs(duration.toMinutes()) > 0) {
      num = Math.abs(duration.toMinutes());
      unit = num == 1 ? "minute" : "minutes";
    } else {
      return "just now";
    }
    return String.format("%d %s ago", num, unit);
  }
}

package cloudcode.guestbook.frontend;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public interface PrintDuration {

   static String print(long prevDateTimestamp) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime prev = LocalDateTime.ofInstant(Instant.ofEpochMilli(prevDateTimestamp), TimeZone.getDefault().toZoneId());
    Duration duration = Duration.between(now, prev);

    long num;
    String unit;
    if (Math.abs(duration.toDays()) >= 365) {
      num = Math.abs(duration.toDays())/365;
      unit = num==1 ? "year" : "years";
    } else if (Math.abs(duration.toDays()) > 0) {
      num = Math.abs(duration.toDays());
      unit = num==1 ? "day" : "days";
    } else if (Math.abs(duration.toHours()) > 0){
      num = Math.abs(duration.toHours());
      unit = num==1 ? "hour" : "hours";  
    } else if (Math.abs(duration.toMinutes()) > 0){ 
      num = Math.abs(duration.toMinutes());
      unit = num==1 ? "minute" : "minutes";
    } else {
      return "just now";
    }
    return String.format("%d %s ago", num, unit);
  }
}
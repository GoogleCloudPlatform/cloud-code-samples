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

    if (Math.abs(duration.toDays()) >= 365*2) {
      return String.format("%d years ago", Math.abs(duration.toDays())/365);
    } else if (Math.abs(duration.toDays()) >= 365) {
      return "1 year ago";
    } else if (Math.abs(duration.toDays()) > 1) {
      return String.format("%d days ago", Math.abs(duration.toDays()));
    } else if (Math.abs(duration.toDays()) == 1) {
      return "1 day ago";
    } else if (Math.abs(duration.toHours()) > 1) {
      return String.format("%d hours ago", Math.abs(duration.toHours()));
    } else if (Math.abs(duration.toHours()) == 1) {
      return "1 hour ago";
    } else if (Math.abs(duration.toMinutes()) > 1){ 
      return String.format("%d minutes ago", Math.abs(duration.toMinutes()));
    } else {
      return "just now";
    }
  }
}
/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

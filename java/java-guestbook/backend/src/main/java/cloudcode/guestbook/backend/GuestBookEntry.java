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

package cloudcode.guestbook.backend;

/**
 * defines the data associated with a single guest book entry
 */
public class GuestBookEntry {

    private String author;
    private String message;
    private long date;

    public final String getAuthor() {
        return author;
    }

    public final void setAuthor(String author) {
        this.author = author;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final long getDate() {
        return this.date;
    }

    public final void setDate(long date) {
        this.date = date;
    }
}

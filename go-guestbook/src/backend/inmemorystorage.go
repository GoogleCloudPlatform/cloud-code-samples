// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package main

import "time"

type inMemoryDatabase struct {
	list []guestbookEntry
}

func (i *inMemoryDatabase) entries() ([]guestbookEntry, error) {
	if i.list == nil {
		// fill in-memory database with the mock entries
		i.list = make([]guestbookEntry, len(mockEntries))
		copy(i.list, mockEntries)
	}
	return i.list, nil
}

func (i *inMemoryDatabase) addEntry(e guestbookEntry) error {
	i.list = append([]guestbookEntry{e}, i.list...)
	return nil
}

var (
	mockEntries = []guestbookEntry{
		{
			Author:  "Alice",
			Message: "I just came across your website and it's dope!",
			Date:    time.Now().Add(-time.Minute * 4),
		},
		{
			Author:  "Jeff",
			Message: "Hello, I really love your website!",
			Date:    time.Now().Add(-time.Hour * 2),
		},
		{
			Author:  "Ali",
			Message: "Your website rocks, I'd like to have a website just like yours.",
			Date:    time.Now().Add(-time.Hour * 10),
		},
	}
)

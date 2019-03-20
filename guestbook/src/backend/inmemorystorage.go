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

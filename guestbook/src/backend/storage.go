package main

type database interface {
	entries() ([]guestbookEntry, error)
	addEntry(guestbookEntry) error
}

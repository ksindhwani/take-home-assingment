package database

type GuestListTable struct {
	IdGuestList int64
	Name        string
	TableId     int64
	AccGuests   int
}

type PartyGuestTable struct {
	IdPartyGuest    int64
	Name            string
	InParty         bool
	AccGuestsActual int
	EntryTime       string
}

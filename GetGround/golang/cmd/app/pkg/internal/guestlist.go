package internal

import (
	"database/sql"
	"errors"
	"fmt"
	"net/http"

	"github.com/getground/tech-tasks/backend/cmd/app/pkg/database"
	"github.com/getground/tech-tasks/backend/cmd/app/pkg/httputils"
)

type Guest struct {
	Name               string `json:"name"`
	TableId            int    `json:"table" validate:"required"`
	AccompanyingGuests int    `json:"accompanying_guests" validate:"required"`
}

type ArrivedGuest struct {
	Name               string `json:"name"`
	AccompanyingGuests int    `json:"accompanying_guests" validate:"required"`
	EntryTime          string `json:"time_arrived"`
}

func AddNewGuestInGuestList(db *sql.DB, t Guest) (map[string]string, error) {
	err := database.AddNewGuestInGuestList(db, t.Name, t.TableId, t.AccompanyingGuests)
	if err != nil {
		return nil, err
	}

	return map[string]string{
		"name": t.Name,
	}, nil
}

func GetGuestList(db *sql.DB) (map[string][]Guest, error) {
	var guests []Guest
	guestList, err := database.GetGuestList(db)
	if err != nil {
		return make(map[string][]Guest), err
	}

	for _, guestFromTable := range guestList {
		guest := Guest{
			Name:               guestFromTable.Name,
			TableId:            int(guestFromTable.TableId),
			AccompanyingGuests: guestFromTable.AccGuests,
		}
		guests = append(guests, guest)
	}
	return map[string][]Guest{
		"guests": guests,
	}, nil
}

func GuestArrived(db *sql.DB, t ArrivedGuest) (map[string]string, *httputils.Error) {
	tableId, err := database.GetTableIdByGuestName(db, t.Name)
	if err == sql.ErrNoRows {
		return nil, &httputils.Error{
			StatusCode: http.StatusNotFound,
			Err:        err,
			Message:    fmt.Sprintf("No table is reserved for the guest %s", t.Name),
		}
	}
	if err != nil {
		return nil, &httputils.Error{
			StatusCode: http.StatusInternalServerError,
			Err:        err,
			Message:    fmt.Sprintf("Unable to fetch the table for the guest %s", t.Name),
		}
	}

	tableAvailbleCapacity, err := database.GetTableAvailableCapacity(db, tableId)
	if err != nil {
		return nil, &httputils.Error{
			StatusCode: http.StatusInternalServerError,
			Err:        err,
			Message:    fmt.Sprint("Unable to fetch the table available Capacity"),
		}
	}

	if tableAvailbleCapacity < t.AccompanyingGuests {
		return nil, &httputils.Error{
			StatusCode: http.StatusBadRequest,
			Err:        errors.New("Resrved table can't accomdate all the accompanying guests"),
			Message:    "Resrved table can't accomdate all the accompanying guests",
		}
	}

	err = database.AddArrivingGuest(db, t.Name, t.AccompanyingGuests, tableId)
	if err != nil {
		return nil, &httputils.Error{
			StatusCode: http.StatusInternalServerError,
			Err:        err,
			Message:    fmt.Sprintf("Unable to save the arriving guest  %s", t.Name),
		}
	}

	return map[string]string{
		"name": t.Name,
	}, nil
}

func GuestLeft(db *sql.DB, guestName string) *httputils.Error {
	inParty, err := database.CheckIfGuestInParty(db, guestName)
	if err == sql.ErrNoRows || inParty == false {
		return &httputils.Error{
			StatusCode: http.StatusNotFound,
			Err:        err,
			Message:    fmt.Sprintf("Guest %s is not present in the party", guestName),
		}
	}
	tableId, err := database.GetTableIdByGuestName(db, guestName)
	if err == sql.ErrNoRows {
		return &httputils.Error{
			StatusCode: http.StatusNotFound,
			Err:        err,
			Message:    fmt.Sprintf("No table is reserved for the guest %s", guestName),
		}
	}
	if err != nil {
		return &httputils.Error{
			StatusCode: http.StatusInternalServerError,
			Err:        err,
			Message:    fmt.Sprintf("Unable to fetch the table for the guest %s", guestName),
		}
	}

	actualAccompanyingGuests, err := database.GetActualAccompanyingGuestsByGuestName(db, guestName)
	if err == sql.ErrNoRows {
		return &httputils.Error{
			StatusCode: http.StatusNotFound,
			Err:        err,
			Message:    fmt.Sprintf("Guest not found %s", guestName),
		}
	}

	err = database.DeleteLeavingGuest(db, guestName, tableId, actualAccompanyingGuests)
	if err != nil {
		return &httputils.Error{
			StatusCode: http.StatusInternalServerError,
			Err:        err,
			Message:    fmt.Sprintf("Unable to delete the leaving guest  %s", guestName),
		}
	}
	return nil
}

func GetAllArrivedGuests(db *sql.DB) (map[string][]ArrivedGuest, error) {
	var guests []ArrivedGuest
	partyGuests, err := database.GetAllArrivedGuests(db)
	if err != nil {
		return nil, err
	}

	for _, guestFromTable := range partyGuests {
		guest := ArrivedGuest{
			Name:               guestFromTable.Name,
			AccompanyingGuests: guestFromTable.AccGuestsActual,
			EntryTime:          guestFromTable.EntryTime,
		}
		guests = append(guests, guest)
	}
	return map[string][]ArrivedGuest{
		"guests": guests,
	}, nil
}

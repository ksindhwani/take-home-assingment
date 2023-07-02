package database

import (
	"context"
	"database/sql"
)

func InsertNewTable(Db *sql.DB, capacity int) (int64, error) {
	query := "INSERT INTO `tables` (`capacity`) VALUES (?)"
	insertResult, err := Db.ExecContext(context.Background(), query, capacity)
	if err != nil {
		return 0, err
	}
	id, err := insertResult.LastInsertId()
	if err != nil {
		return 0, err
	}
	return id, nil
}

func AddNewGuestInGuestList(Db *sql.DB, guestName string, tableId int, accompanyingGuests int) error {
	query := "INSERT INTO `guest_list` (`name`,`table_id`,`acc_guests`) VALUES(?,?,?)"
	insertResult, err := Db.ExecContext(context.Background(), query, guestName, tableId, accompanyingGuests)
	if err != nil {
		return err
	}
	_, err = insertResult.LastInsertId()
	if err != nil {
		return err
	}
	return nil
}

func GetGuestList(Db *sql.DB) ([]GuestListTable, error) {
	var guests []GuestListTable
	rows, err := Db.Query("SELECT * FROM `guest_list`")
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	// Loop through rows, using Scan to assign column data to struct fields.
	for rows.Next() {
		var guest GuestListTable
		if err := rows.Scan(&guest.IdGuestList, &guest.Name, &guest.TableId, &guest.AccGuests); err != nil {
			return nil, err
		}
		guests = append(guests, guest)
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return guests, nil

}

func GetTableIdByGuestName(Db *sql.DB, name string) (int64, error) {
	var tableId int64
	getTableIdByGuestName := "SELECT `table_id` from `guest_list` WHERE name = ?"
	err := Db.QueryRow(getTableIdByGuestName, name).Scan(&tableId)
	if err != nil {
		return 0, err
	}
	return tableId, nil
}

func GetTableAvailableCapacity(Db *sql.DB, tableId int64) (int, error) {
	var tableAvailableCapacity int
	getTableCapacity := "SELECT `capacity`-`occupied` from `tables` where id_tables = ?"

	err := Db.QueryRow(getTableCapacity, tableId).Scan(&tableAvailableCapacity)
	if err != nil {
		return -1, err
	}
	return tableAvailableCapacity, nil
}

func GetActualAccompanyingGuestsByGuestName(Db *sql.DB, guestName string) (int, error) {
	var actualAccompanyingGuests int
	getActualAccompanyingGuestsByGuestName := "SELECT `acc_guests_actual` from `party_guest` where `name` = ?"
	err := Db.QueryRow(getActualAccompanyingGuestsByGuestName, guestName).Scan(&actualAccompanyingGuests)
	if err != nil {
		return -1, err
	}
	return actualAccompanyingGuests, nil

}

func CheckIfGuestInParty(Db *sql.DB, guestName string) (bool, error) {
	var inParty bool
	checkIfGuestInParty := "SELECT `in_party` from `party_guest` where `name` = ?"
	err := Db.QueryRow(checkIfGuestInParty, guestName).Scan(&inParty)
	return inParty, err

}

func AddArrivingGuest(Db *sql.DB, name string, accompanyingGuests int, tableId int64) error {
	ctx := context.Background()
	insertInPartyGuest := "INSERT INTO `party_guest` (`name`, `in_party`, `acc_guests_actual`) VALUES (?,?,?)"
	updateTableCapacity := "UPDATE `tables` SET `occupied` = `occupied` + 1 + ? WHERE `id_tables` = ?"
	tx, err := Db.BeginTx(ctx, nil)
	if err != nil {
		return err
	}

	_, err = tx.ExecContext(ctx, insertInPartyGuest, name, 1, accompanyingGuests)
	if err != nil {
		tx.Rollback()
		return err
	}

	_, err = tx.ExecContext(ctx, updateTableCapacity, accompanyingGuests, tableId)
	if err != nil {
		tx.Rollback()
		return err
	}
	err = tx.Commit()
	if err != nil {
		return err
	}
	return nil
}

func DeleteLeavingGuest(Db *sql.DB, name string, tableId int64, accompanyingGuests int) error {
	ctx := context.Background()
	updateInPartyFlag := "UPDATE `party_guest` SET `in_party` = 0 WHERE name = ?"
	updateTableCapacity := "UPDATE `tables` SET `occupied` = `occupied` - 1 - ? WHERE `id_tables` = ?"
	tx, err := Db.BeginTx(ctx, nil)
	if err != nil {
		return err
	}

	_, err = tx.ExecContext(ctx, updateInPartyFlag, name)
	if err != nil {
		tx.Rollback()
		return err
	}

	_, err = tx.ExecContext(ctx, updateTableCapacity, accompanyingGuests, tableId)
	if err != nil {
		tx.Rollback()
		return err
	}
	err = tx.Commit()
	if err != nil {
		return err
	}
	return nil
}

func GetAllArrivedGuests(Db *sql.DB) ([]PartyGuestTable, error) {
	var guests []PartyGuestTable
	rows, err := Db.Query("SELECT * FROM `party_guest`")
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	// Loop through rows, using Scan to assign column data to struct fields.
	for rows.Next() {
		var guest PartyGuestTable
		if err := rows.Scan(&guest.IdPartyGuest, &guest.Name, &guest.InParty, &guest.AccGuestsActual, &guest.EntryTime); err != nil {
			return nil, err
		}
		guests = append(guests, guest)
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return guests, nil
}

func GetEmptySeats(Db *sql.DB) (int, error) {
	var emptySeats int
	emptySeatsQuery := "SELECT SUM(`capacity`-`occupied`) FROM `tables`"

	err := Db.QueryRow(emptySeatsQuery).Scan(&emptySeats)
	return emptySeats, err

}

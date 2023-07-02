package internal

import (
	"database/sql"

	"github.com/getground/tech-tasks/backend/cmd/app/pkg/database"
)

type TableStruct struct {
	Capacity int `json:"capacity" validate:"required"`
}

func AddNewTable(db *sql.DB, t TableStruct) (map[string]int, error) {

	id, err := database.InsertNewTable(db, t.Capacity)
	if err != nil {
		return nil, err
	}
	return map[string]int{
		"id":       int(id),
		"capacity": t.Capacity,
	}, nil
}

func GetEmptySeats(db *sql.DB) (map[string]int, error) {
	emptySeats, err := database.GetEmptySeats(db)
	if err != nil {
		return nil, err
	}
	return map[string]int{
		"seats_empty": emptySeats,
	}, nil
}

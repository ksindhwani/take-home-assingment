package dependencies

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

type ConnectionParams struct {
	DBUsername string
	DBPassword string
	DBHost     string
	DBPort     int
	DBDatabase string
}

func NewDB(cp ConnectionParams) (*sql.DB, error) {
	// init mysql.
	connectionString := fmt.Sprintf("%s:%s@tcp(%s:%d)/%s", cp.DBUsername, cp.DBPassword, cp.DBHost, cp.DBPort, cp.DBDatabase)
	db, err := sql.Open("mysql", connectionString)
	if err != nil {
		log.Println(err)
	}
	return db, err
}

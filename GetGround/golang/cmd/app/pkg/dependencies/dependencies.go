package dependencies

import (
	"database/sql"

	"github.com/getground/tech-tasks/backend/cmd/app/pkg/config"
	"github.com/go-playground/validator/v10"
)

type Dependencies struct {
	DB        *sql.DB
	Validator *validator.Validate
	Config    *config.Config
}

func GetAllDependencies(
	DB *sql.DB,
	validator *validator.Validate,
	config *config.Config,
) *Dependencies {
	deps := &Dependencies{
		DB:        DB,
		Validator: validator,
		Config:    config,
	}

	return deps
}

package config

import (
	"github.com/caarlos0/env/v8"
)

type Config struct {
	DBPort     int    `env:"DB_PORT"`
	DBUser     string `env:"DB_USER"`
	DBPassword string `env:"DB_PASSWORD"`
	DBDatabase string `env:"DB_DATABASE"`
	DBHost     string `env:"DB_HOST"`
	Address    string `env:"ADDR"`
}

const (
	defaultDbPort      = 3306
	defaultDbUser      = "root"
	defaultDbPasswoerd = ""
	defaultDbDatabase  = "getground"
	defaultDbHost      = "localhost"
	defaultAddress     = "0.0.0.0:3000"
)

func New() (*Config, error) {
	cfg := Config{
		DBPort:     defaultDbPort,
		DBUser:     defaultDbUser,
		DBPassword: defaultDbPasswoerd,
		DBDatabase: defaultDbDatabase,
		DBHost:     defaultDbHost,
		Address:    defaultAddress,
	}

	// load .env file
	if err := env.Parse(&cfg); err != nil {
		return nil, err
	}
	return &cfg, nil
}

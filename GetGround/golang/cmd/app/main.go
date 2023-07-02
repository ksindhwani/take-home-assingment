package main

import (
	"context"
	"database/sql"
	"log"
	"net/http"
	"os"
	"os/signal"
	"time"

	"github.com/getground/tech-tasks/backend/cmd/app/pkg/config"
	"github.com/getground/tech-tasks/backend/cmd/app/pkg/dependencies"
	"github.com/getground/tech-tasks/backend/cmd/app/pkg/route"
	"github.com/go-playground/validator/v10"
	"github.com/gorilla/mux"
)

func main() {

	config, err := config.New()
	if err != nil {
		log.Fatal(err)
	}

	db, err := initializeDB(config)
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	validate := initializeNewValidator()

	deps := dependencies.GetAllDependencies(db, validate, config)

	router := initializeRouter(deps)

	srv := &http.Server{
		Addr: config.Address,
		// Good practice to set timeouts to avoid Slowloris attacks.
		WriteTimeout: time.Second * 15,
		ReadTimeout:  time.Second * 15,
		IdleTimeout:  time.Second * 60,
		Handler:      router, // Pass our instance of gorilla/mux in.
	}

	// Run our server in a goroutine so that it doesn't block.
	go func() {
		if err := srv.ListenAndServe(); err != nil {
			log.Println(err)
		}
	}()

	c := make(chan os.Signal, 1)
	// We'll accept graceful shutdowns when quit via SIGINT (Ctrl+C)
	// SIGKILL, SIGQUIT or SIGTERM (Ctrl+/) will not be caught.
	signal.Notify(c, os.Interrupt)

	// Block until we receive our signal.
	<-c

	// Create a deadline to wait for.
	ctx, cancel := context.WithTimeout(context.Background(), time.Second*15)
	defer cancel()
	// Doesn't block if no connections, but will otherwise wait
	// until the timeout deadline.
	srv.Shutdown(ctx)
	// Optionally, you could run srv.Shutdown in a goroutine and block on
	// <-ctx.Done() if your application should wait for other services
	// to finalize based on context cancel
}

func initializeNewValidator() *validator.Validate {
	return validator.New()
}

func initializeRouter(deps *dependencies.Dependencies) *mux.Router {
	return route.NewRouter(deps)
}

func initializeDB(cfg *config.Config) (*sql.DB, error) {
	cp := dependencies.ConnectionParams{
		DBUsername: cfg.DBUser,
		DBPassword: cfg.DBPassword,
		DBHost:     cfg.DBHost,
		DBPort:     cfg.DBPort,
		DBDatabase: cfg.DBDatabase,
	}

	return dependencies.NewDB(cp)
}

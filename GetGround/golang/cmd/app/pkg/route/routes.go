package route

import (
	"net/http"

	"github.com/getground/tech-tasks/backend/cmd/app/pkg/dependencies"
	"github.com/gorilla/mux"
)

func NewRouter(deps *dependencies.Dependencies) *mux.Router {
	r := mux.NewRouter()
	r.HandleFunc("/ping", PingHandler).Methods(http.MethodGet)

	glh := NewGuestListHandler(deps.DB, deps.Validator)

	r.HandleFunc("/tables", glh.TableHandler).Methods(http.MethodPost)
	r.HandleFunc("/guest_list/{name}", glh.AddNewGuestHandler).Methods(http.MethodPost)
	r.HandleFunc("/guest_list", glh.GetGuestListHandler).Methods(http.MethodGet)
	r.HandleFunc("/guests/{name}", glh.GuestArriveHandler).Methods(http.MethodPut)
	r.HandleFunc("/guests/{name}", glh.GuestLeftHandler).Methods(http.MethodDelete)
	r.HandleFunc("/guests", glh.GetGuestsHandler).Methods(http.MethodGet)
	r.HandleFunc("/seats_empty", glh.GetEmptySeatsHandler).Methods(http.MethodGet)

	return r
}

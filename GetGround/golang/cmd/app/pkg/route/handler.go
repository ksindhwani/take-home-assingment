package route

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/getground/tech-tasks/backend/cmd/app/pkg/httputils"
	"github.com/getground/tech-tasks/backend/cmd/app/pkg/internal"
	"github.com/go-playground/validator/v10"
)

type GuestListHandler struct {
	Db        *sql.DB
	Validator *validator.Validate
}

func NewGuestListHandler(
	Db *sql.DB,
	validator *validator.Validate,
) *GuestListHandler {
	return &GuestListHandler{
		Db:        Db,
		Validator: validator,
	}
}

func PingHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "pong\n")
}

func (glh GuestListHandler) TableHandler(w http.ResponseWriter, r *http.Request) {
	var t internal.TableStruct
	body, err := httputils.GetRequestBody(w, r)
	if err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Unable to Parse Request Body",
			"error":   err.Error(),
		})
		return
	}

	if err := json.Unmarshal(body, &t); err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Unable to Marshal Request Body",
			"error":   err.Error(),
		})
		return
	}

	if err = glh.Validator.Struct(t); err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Required fields missing in request body",
			"error":   err.Error(),
		})
		return
	}

	response, err := internal.AddNewTable(glh.Db, t)
	if err != nil {
		httputils.WriteResponse(w, http.StatusInternalServerError, map[string]string{
			"message": "Unable to create new table in database",
			"error":   err.Error(),
		})
		return
	}

	httputils.WriteResponse(w, http.StatusOK, response)
}

func (glh GuestListHandler) AddNewGuestHandler(w http.ResponseWriter, r *http.Request) {
	var t internal.Guest
	body, err := httputils.GetRequestBody(w, r)
	if err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Unable to Parse Request Body",
			"error":   err.Error(),
		})
		return
	}

	if err := json.Unmarshal(body, &t); err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Unable to Marshal Request Body",
			"error":   err.Error(),
		})
		return
	}

	name, err := httputils.ValidateUrlParam(r, "name")
	if err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Name is missing in url. Provide the url in /guest_list/{name} format",
			"error":   err.Error(),
		})
		return
	}

	if err = glh.Validator.Struct(t); err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Required fields missing in request body",
			"error":   err.Error(),
		})
		return
	}

	t.Name = name
	response, err := internal.AddNewGuestInGuestList(glh.Db, t)
	if err != nil {
		httputils.WriteResponse(w, http.StatusInternalServerError, map[string]string{
			"message": "Unable to add new guest in guestlist",
			"error":   err.Error(),
		})
		return
	}

	httputils.WriteResponse(w, http.StatusOK, response)
}

func (glh GuestListHandler) GetGuestListHandler(w http.ResponseWriter, r *http.Request) {
	response, err := internal.GetGuestList(glh.Db)
	if err != nil {
		httputils.WriteResponse(w, http.StatusInternalServerError, map[string]string{
			"message": "Unable to fetch the guestList",
			"error":   err.Error(),
		})
		return
	}
	httputils.WriteResponse(w, http.StatusOK, response)
}

func (glh GuestListHandler) GuestArriveHandler(w http.ResponseWriter, r *http.Request) {
	var t internal.ArrivedGuest
	body, err := httputils.GetRequestBody(w, r)
	if err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Unable to Parse Request Body",
			"error":   err.Error(),
		})
		return
	}

	if err := json.Unmarshal(body, &t); err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Unable to Marshal Request Body",
			"error":   err.Error(),
		})
		return
	}

	name, err := httputils.ValidateUrlParam(r, "name")
	if err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Name is missing in url. Provide the url in /guestsz/{name} format",
			"error":   err.Error(),
		})
		return
	}

	if err = glh.Validator.Struct(t); err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Required fields missing in request body",
			"error":   err.Error(),
		})
		return
	}

	t.Name = name
	response, errors := internal.GuestArrived(glh.Db, t)
	if errors != nil {
		httputils.WriteResponse(w, errors.StatusCode, map[string]string{
			"message": errors.Message,
			"error":   errors.Err.Error(),
		})
		return
	}

	httputils.WriteResponse(w, http.StatusOK, response)
}

func (glh GuestListHandler) GuestLeftHandler(w http.ResponseWriter, r *http.Request) {
	name, err := httputils.ValidateUrlParam(r, "name")
	if err != nil {
		httputils.WriteResponse(w, http.StatusBadRequest, map[string]string{
			"message": "Name is missing in url. Provide the url in /guestsz/{name} format",
			"error":   err.Error(),
		})
		return
	}
	errors := internal.GuestLeft(glh.Db, name)
	if err != nil {
		httputils.WriteResponse(w, errors.StatusCode, map[string]string{
			"message": errors.Message,
			"error":   errors.Err.Error(),
		})
		return
	}

	httputils.WriteResponse(w, http.StatusNoContent, "")
}

func (glh GuestListHandler) GetGuestsHandler(w http.ResponseWriter, r *http.Request) {
	response, err := internal.GetAllArrivedGuests(glh.Db)
	if err != nil {
		httputils.WriteResponse(w, http.StatusInternalServerError, map[string]string{
			"message": "Unable to fetch all the arrived guests",
			"error":   err.Error(),
		})
		return
	}
	httputils.WriteResponse(w, http.StatusOK, response)
}

func (glh GuestListHandler) GetEmptySeatsHandler(w http.ResponseWriter, r *http.Request) {
	response, err := internal.GetEmptySeats(glh.Db)
	if err != nil {
		httputils.WriteResponse(w, http.StatusInternalServerError, map[string]string{
			"message": "Unable to fetch the empty seats",
			"error":   err.Error(),
		})
		return
	}
	httputils.WriteResponse(w, http.StatusOK, response)
}

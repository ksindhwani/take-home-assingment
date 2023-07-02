package httputils

import (
	"encoding/json"
	"errors"
	"io/ioutil"
	"net/http"

	"github.com/gorilla/mux"
)

func WriteResponse(w http.ResponseWriter, statusCode int, message interface{}) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(statusCode)
	json.NewEncoder(w).Encode(message)
}

func GetRequestBody(w http.ResponseWriter, r *http.Request) ([]byte, error) {
	body, err := ioutil.ReadAll(r.Body)
	return body, err
}

func ValidateUrlParam(r *http.Request, paramName string) (string, error) {
	urlParams := mux.Vars(r)
	_, ok := urlParams[paramName]
	if !ok {
		return "", errors.New("%s is missing in url. Provide the URL as per path template")
	}
	return urlParams[paramName], nil

}

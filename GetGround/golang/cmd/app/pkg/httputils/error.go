package httputils

type Error struct {
	StatusCode int
	Err        error
	Message    string
}

package internal

import (
	"database/sql"
	"errors"
	"reflect"
	"regexp"
	"testing"

	"github.com/DATA-DOG/go-sqlmock"
)

func TestAddNewGuestInGuestList(t *testing.T) {
	db, mock, err := sqlmock.New()
	if err != nil {
		t.Errorf("An error '%s' was not expected when opening a stub database connection", err)
	}
	defer db.Close()

	type args struct {
		db *sql.DB
		t  Guest
	}
	tests := []struct {
		name           string
		args           args
		want           map[string]string
		wantErr        error
		expectResponse sql.Result
	}{
		{
			name: "Testing the success",
			args: args{
				db: db,
				t: Guest{
					Name:               "kunal",
					TableId:            1,
					AccompanyingGuests: 1,
				},
			},
			want: map[string]string{
				"name": "kunal",
			},
			wantErr:        nil,
			expectResponse: sqlmock.NewResult(1, 1),
		},
	}
	for _, tt := range tests {
		mock.ExpectExec("INSERT INTO `guest_list`").WillReturnResult(tt.expectResponse)
		t.Run(tt.name, func(t *testing.T) {
			got, err := AddNewGuestInGuestList(tt.args.db, tt.args.t)
			if err != tt.wantErr {
				t.Errorf("AddNewGuestInGuestList() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("AddNewGuestInGuestList() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestAddNewGuestInGuestListTestFail(t *testing.T) {
	db, mock, err := sqlmock.New()
	if err != nil {
		t.Errorf("An error '%s' was not expected when opening a stub database connection", err)
	}
	defer db.Close()

	type args struct {
		db *sql.DB
		t  Guest
	}
	tests := []struct {
		name           string
		args           args
		want           map[string]string
		wantErr        error
		expectResponse sql.Result
		expectError    error
	}{
		{
			name: "Testing the success",
			args: args{
				db: db,
				t: Guest{
					Name:               "kunal",
					TableId:            1,
					AccompanyingGuests: 1,
				},
			},
			want:           nil,
			wantErr:        errors.New("Connection Refused"),
			expectResponse: nil,
			expectError:    errors.New("Connection Refused"),
		},
	}
	for _, tt := range tests {
		mock.ExpectExec("INSERT INTO `guest_list`").WillReturnError(tt.expectError)
		t.Run(tt.name, func(t *testing.T) {
			got, err := AddNewGuestInGuestList(tt.args.db, tt.args.t)
			if err.Error() != tt.wantErr.Error() {
				t.Errorf("AddNewGuestInGuestList() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("AddNewGuestInGuestList() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestGetGuestList(t *testing.T) {
	db, mock, err := sqlmock.New()
	if err != nil {
		t.Errorf("An error '%s' was not expected when opening a stub database connection", err)
	}
	defer db.Close()

	type args struct {
		db *sql.DB
	}
	tests := []struct {
		name             string
		args             args
		want             map[string][]Guest
		wantErr          error
		expectedResponse *sqlmock.Rows
	}{
		{
			name: "Testing the success",
			args: args{
				db: db,
			},
			want: map[string][]Guest{
				"guests": {
					{
						Name:               "kuna",
						TableId:            1,
						AccompanyingGuests: 2,
					},
					{
						Name:               "abc",
						TableId:            2,
						AccompanyingGuests: 3,
					},
				},
			},
			wantErr: nil,
			expectedResponse: sqlmock.NewRows([]string{"id_guest_list", "name", "table_id", "acc_guests"}).
				AddRow(1, "kuna", 1, 2).
				AddRow(2, "abc", 2, 3),
		},
	}
	for _, tt := range tests {
		mock.ExpectQuery(regexp.QuoteMeta("SELECT * FROM `guest_list`")).WillReturnRows(tt.expectedResponse)
		t.Run(tt.name, func(t *testing.T) {
			got, err := GetGuestList(tt.args.db)
			if err != tt.wantErr {
				t.Errorf("GetGuestList() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetGuestList() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestGetGuestListFail(t *testing.T) {
	db, mock, err := sqlmock.New()
	if err != nil {
		t.Errorf("An error '%s' was not expected when opening a stub database connection", err)
	}
	defer db.Close()

	type args struct {
		db *sql.DB
	}
	tests := []struct {
		name          string
		args          args
		want          map[string][]Guest
		wantErr       error
		expectedError error
	}{
		{
			name: "Testing the success",
			args: args{
				db: db,
			},
			want:          map[string][]Guest{},
			wantErr:       errors.New("Connection Refused"),
			expectedError: errors.New("Connection Refused"),
		},
	}
	for _, tt := range tests {
		mock.ExpectQuery(regexp.QuoteMeta("SELECT * FROM `guest_list`")).WillReturnError(tt.expectedError)
		t.Run(tt.name, func(t *testing.T) {
			got, err := GetGuestList(tt.args.db)
			if err.Error() != tt.wantErr.Error() {
				t.Errorf("GetGuestList() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetGuestList() = %v, want %v", got, tt.want)
			}
		})
	}
}

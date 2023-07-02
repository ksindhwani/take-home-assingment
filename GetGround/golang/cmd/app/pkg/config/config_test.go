package config

import (
	"reflect"
	"testing"
)

func TestNew(t *testing.T) {
	tests := []struct {
		name    string
		want    *Config
		wantErr error
	}{
		{
			name: "Test Default Config",
			want: &Config{
				DBPort:     defaultDbPort,
				DBUser:     defaultDbUser,
				DBPassword: defaultDbPasswoerd,
				DBDatabase: defaultDbDatabase,
				DBHost:     defaultDbHost,
				Address:    defaultAddress,
			},
			wantErr: nil,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			got, err := New()
			if err != tt.wantErr {
				t.Errorf("New() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("New() = %v, want %v", got, tt.want)
			}
		})
	}
}

package stats

import (
	"fmt"
	"os"
	"strconv"
	"time"
)

func InBetweenTime(deliveryFrom int, deliveryTo int, requestFrom int, requestTo int) bool {
	return deliveryFrom >= requestFrom &&
		deliveryFrom <= requestTo &&
		deliveryTo >= requestFrom &&
		deliveryTo <= requestTo &&
		deliveryFrom < deliveryTo
}

func Get24HoursTime(timeStr string) int {
	t, err := time.Parse("3PM", timeStr)
	if err != nil {
		fmt.Println("Error in get24HoursTime:", err)
		os.Exit(1)
	}

	time24Formatted := t.Format("1500")
	finalTime, err := strconv.Atoi(time24Formatted)
	if err != nil {
		fmt.Println("Error in get24HoursTime:", err)
		os.Exit(1)
	}

	return finalTime
}

func GetAMPMTime(timeInt int) string {
	timeString := getTimeString(timeInt)
	time12Reverse, err := time.Parse("1504", timeString)
	if err != nil {
		fmt.Println("Error in getAMPMTime:", err)
		os.Exit(1)
	}

	time12Formatted := time12Reverse.Format("3PM")
	return time12Formatted

}

func getTimeString(timeInt int) string {
	timeString := strconv.Itoa(timeInt)
	if len(timeString) < 4 {
		timeString = "0" + timeString
	}
	return timeString
}

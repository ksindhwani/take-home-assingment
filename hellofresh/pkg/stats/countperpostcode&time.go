package stats

import (
	"strings"
)

type CountPerPostcodeAndTimeRequest struct {
	Postcode string
	From     int
	To       int
}

type CountPerPostcodeAndTimeStat struct {
	PostCode              string
	PostCodeDeliveryCount int
	PostCodeFrom          int
	PostCodeTo            int
}

func NewCountPerPostcodeAndTimeStat() *CountPerPostcodeAndTimeStat {
	return &CountPerPostcodeAndTimeStat{
		PostCodeDeliveryCount: 0,
		PostCodeFrom:          2359,
		PostCodeTo:            0,
	}
}

func (cppt *CountPerPostcodeAndTimeStat) Calculate(recipeRequests []RecipeRequest, request CountPerPostcodeAndTimeRequest) {
	cppt.PostCode = request.Postcode
	for _, recipe := range recipeRequests {
		from, to := getDeliveryTime(recipe.Delivery)
		if recipe.Postcode == cppt.PostCode && InBetweenTime(from, to, request.From, request.To) {
			cppt.PostCodeDeliveryCount += 1
			if from < cppt.PostCodeFrom {
				cppt.PostCodeFrom = from
			}
			if to > cppt.PostCodeTo {
				cppt.PostCodeTo = to
			}
		}
	}
}

func (cppt *CountPerPostcodeAndTimeStat) GetStat() (interface{}, error) {
	return map[string]interface{}{
		POSTCODE:       cppt.PostCode,
		FROM:           GetAMPMTime(cppt.PostCodeFrom),
		TO:             GetAMPMTime(cppt.PostCodeTo),
		DELIVERY_COUNT: cppt.PostCodeDeliveryCount,
	}, nil
}

func getDeliveryTime(delivery string) (int, int) {
	deliveryStrings := strings.Split(delivery, " ")
	from := Get24HoursTime(deliveryStrings[1])
	to := Get24HoursTime(deliveryStrings[3])
	return from, to

}

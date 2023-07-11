package stats

type BusiestPostcodeStat struct {
	BusyPostCode      string
	PostcodeCountMap  map[string]int
	BusyPostCodeCount int
}

func NewBusiestPostcodeStat() *BusiestPostcodeStat {
	return &BusiestPostcodeStat{
		BusyPostCode:      "",
		PostcodeCountMap:  make(map[string]int),
		BusyPostCodeCount: 0,
	}
}

func (bp *BusiestPostcodeStat) Calculate(recipeRequests []RecipeRequest) {
	count := 0
	for _, recipe := range recipeRequests {
		value := bp.PostcodeCountMap[recipe.Postcode]
		count = value + 1
		bp.PostcodeCountMap[recipe.Postcode] = count

		if count > bp.BusyPostCodeCount {
			bp.BusyPostCode = recipe.Postcode
			bp.BusyPostCodeCount = count
		}
	}
}

func (bp *BusiestPostcodeStat) GetStat() (interface{}, error) {
	return map[string]interface{}{
		POSTCODE:       bp.BusyPostCode,
		DELIVERY_COUNT: bp.BusyPostCodeCount,
	}, nil
}

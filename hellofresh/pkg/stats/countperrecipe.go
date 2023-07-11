package stats

type CountPerRecipeStat struct {
	RecipeCount map[string]int
}

func NewCountPerRecipeStat() *CountPerRecipeStat {
	return &CountPerRecipeStat{
		RecipeCount: make(map[string]int),
	}
}

func (cpr *CountPerRecipeStat) Calculate(recipeRequests []RecipeRequest) {
	for _, recipeRequest := range recipeRequests {
		value := cpr.RecipeCount[recipeRequest.Recipe]
		cpr.RecipeCount[recipeRequest.Recipe] = value + 1
	}
}

func (cpr *CountPerRecipeStat) GetStat() (interface{}, error) {
	var response []map[string]interface{}
	for key, recipeCount := range cpr.RecipeCount {
		responseCount := make(map[string]interface{})
		responseCount[RECiPE] = key
		responseCount[COUNT] = recipeCount
		response = append(response, responseCount)
	}
	return response, nil
}

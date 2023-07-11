package stats

type UniqueRecipeStat struct {
	uniqueRecipes map[string]bool
}

func NewUniqueRecipeStat() *UniqueRecipeStat {
	return &UniqueRecipeStat{
		uniqueRecipes: make(map[string]bool),
	}
}

func (urs *UniqueRecipeStat) Calculate(recipeRequests []RecipeRequest) {
	for _, recipeRequest := range recipeRequests {
		if _, ok := urs.uniqueRecipes[recipeRequest.Recipe]; !ok {
			urs.uniqueRecipes[recipeRequest.Recipe] = true
		}
	}
}

func (urs *UniqueRecipeStat) GetStat() (interface{}, error) {
	return len(urs.uniqueRecipes), nil
}

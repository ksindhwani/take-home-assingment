package stats

import (
	"sort"
	"strings"
)

type MatchByNameStat struct {
	Response map[string]bool
}

func NewMatchByNameStat() *MatchByNameStat {
	return &MatchByNameStat{
		Response: make(map[string]bool),
	}
}

func (mbn *MatchByNameStat) Calculate(recipeRequests []RecipeRequest, Keywords []string) {
	for _, recipeRequest := range recipeRequests {
		for _, keyword := range Keywords {
			contains := strings.Contains(recipeRequest.Recipe, keyword)
			_, ok := mbn.Response[recipeRequest.Recipe]
			if contains && !ok {
				mbn.Response[recipeRequest.Recipe] = true
			}
		}
	}
}

func (mbn *MatchByNameStat) GetStat() (interface{}, error) {
	var recipesWithSearchKeywords []string
	for key := range mbn.Response {
		recipesWithSearchKeywords = append(recipesWithSearchKeywords, key)
	}
	sort.Strings(recipesWithSearchKeywords)
	return recipesWithSearchKeywords, nil
}

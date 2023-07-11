package pkg

import (
	"encoding/json"
	"fmt"
	"os"

	"github.com/hellofreshdevtests/ksindhwani-golang-test/pkg/stats"
)

const (
	UNIQUE_RECIPE_COUNT         = "unique_recipe_count"
	COUNT_PER_RECIPE            = "count_per_recipe"
	BUSIEST_POSTCODE            = "busiest_postcode"
	COUNT_PER_POSTCODE_AND_TIME = "count_per_postcode_and_time"
	MATCH_BY_NAME               = "match_by_name"
	BATCH_SIZE                  = 50
)

type RecipeStatsCalculator struct {
	UniqueRecipeStat            *stats.UniqueRecipeStat
	CountPerRecipeStat          *stats.CountPerRecipeStat
	BusiestPostcodeStat         *stats.BusiestPostcodeStat
	CountPerPostcodeAndTimeStat *stats.CountPerPostcodeAndTimeStat
	MatchByNameStat             *stats.MatchByNameStat
	Stats                       map[string]stats.RecipeStats
}

func NewRecipeStatsCalculator() RecipeStatsCalculator {
	recipeStatCalculator := RecipeStatsCalculator{
		UniqueRecipeStat:            stats.NewUniqueRecipeStat(),
		CountPerRecipeStat:          stats.NewCountPerRecipeStat(),
		BusiestPostcodeStat:         stats.NewBusiestPostcodeStat(),
		CountPerPostcodeAndTimeStat: stats.NewCountPerPostcodeAndTimeStat(),
		MatchByNameStat:             stats.NewMatchByNameStat(),
	}
	recipeStatCalculator.Stats = map[string]stats.RecipeStats{
		UNIQUE_RECIPE_COUNT:         recipeStatCalculator.UniqueRecipeStat,
		COUNT_PER_RECIPE:            recipeStatCalculator.CountPerRecipeStat,
		BUSIEST_POSTCODE:            recipeStatCalculator.BusiestPostcodeStat,
		COUNT_PER_POSTCODE_AND_TIME: recipeStatCalculator.CountPerPostcodeAndTimeStat,
		MATCH_BY_NAME:               recipeStatCalculator.MatchByNameStat,
	}

	return recipeStatCalculator
}

func (rsc RecipeStatsCalculator) Calculate(jsonFilePath string) (map[string]interface{}, error) {

	// Open the JSON file
	file, err := os.Open(jsonFilePath)
	if err != nil {
		return nil, fmt.Errorf("unable to open json file: %w", err)
	}
	defer file.Close()
	decoder := json.NewDecoder(file)

	// Read the opening bracket of the array
	_, err = decoder.Token()
	if err != nil {
		fmt.Printf("Failed to read opening bracket: %v\n", err)
		return nil, err
	}

	// Loop through the file until it ends
	for {
		batch := make([]stats.RecipeRequest, 0, BATCH_SIZE)

		for i := 0; i < BATCH_SIZE && decoder.More(); i++ {

			var data stats.RecipeRequest

			// Read the next JSON object
			err := decoder.Decode(&data)

			// Check for errors
			if err != nil {
				fmt.Printf("Failed to parse JSON: %v\n", err)
				continue
			}
			batch = append(batch, data)
		}

		// Process the batch of records
		rsc.processBatch(batch)

		// Check for end of file
		if !decoder.More() {
			break
		}
	}

	// Get the final Result
	response, err := rsc.prepareFinalResponse()
	if err != nil {
		fmt.Printf("Error Occureed: %s", err.Error())
	}
	return response, nil

}

func (rsc *RecipeStatsCalculator) prepareFinalResponse() (map[string]interface{}, error) {
	response := make(map[string]interface{})
	for name, stat := range rsc.Stats {
		result, err := stat.GetStat()
		if err != nil {
			fmt.Printf("Error Occureed: %s", err.Error())
		}
		response[name] = result
	}
	return response, nil
}

func (rsc *RecipeStatsCalculator) processBatch(recipeBatch []stats.RecipeRequest) {
	rsc.calculateUniqueRecipeCount(recipeBatch)
	rsc.calculateCountPerRecipe(recipeBatch)
	rsc.calculateBusiestPostcode(recipeBatch)
	rsc.calculateCountPerPostcodeAndTime(recipeBatch)
	rsc.calculateMatchByName(recipeBatch)
}

func (rsc *RecipeStatsCalculator) calculateMatchByName(recipeBatch []stats.RecipeRequest) {
	keywords := []string{"Potato", "Veggie", "Mushroom"}
	rsc.MatchByNameStat.Calculate(recipeBatch, keywords)
}

func (rsc *RecipeStatsCalculator) calculateCountPerPostcodeAndTime(recipeBatch []stats.RecipeRequest) {
	requestPostCode := "10120"
	requestFrom := "10AM"
	requestTo := "3PM"

	request := stats.CountPerPostcodeAndTimeRequest{
		Postcode: requestPostCode,
		From:     stats.Get24HoursTime(requestFrom),
		To:       stats.Get24HoursTime(requestTo),
	}
	rsc.CountPerPostcodeAndTimeStat.Calculate(recipeBatch, request)
}

func (rsc *RecipeStatsCalculator) calculateBusiestPostcode(recipeBatch []stats.RecipeRequest) {
	rsc.BusiestPostcodeStat.Calculate(recipeBatch)
}

func (rsc *RecipeStatsCalculator) calculateCountPerRecipe(recipeBatch []stats.RecipeRequest) {
	rsc.CountPerRecipeStat.Calculate(recipeBatch)
}

func (rsc *RecipeStatsCalculator) calculateUniqueRecipeCount(recipeBatch []stats.RecipeRequest) {
	rsc.UniqueRecipeStat.Calculate(recipeBatch)
}

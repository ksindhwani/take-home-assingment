package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"os"
	"path/filepath"

	"github.com/hellofreshdevtests/ksindhwani-golang-test/pkg"
)

func main() {
	name := flag.String("name", "", "Json Fixture")
	flag.Parse()

	if *name == "" {
		fmt.Println("Please provide a json fixture file path using -name flag")
		os.Exit(1)
	}
	jsonFilePath, err := getAbsolutePath(*name)
	if err != nil {
		fmt.Printf("Error Occurred !! %s", err.Error())
		os.Exit(1)
	}

	fmt.Println("Please Wait \n\n\nComputing Stats \n\n\n ")

	response, err := pkg.NewRecipeStatsCalculator().Calculate(jsonFilePath)
	if err != nil {
		fmt.Printf("Error Occurred !! %s", err.Error())
		os.Exit(1)
	}

	printResponseOnConsole(response)
}

func getAbsolutePath(name string) (string, error) {
	relativePath := "/app/data/" + name
	// Retrieve the absolute path of the file
	absolutePath, err := filepath.Abs(filepath.Join(os.Getenv("PWD"), relativePath))
	return absolutePath, err
}

func printResponseOnConsole(response map[string]interface{}) {
	jsonBytes, err := json.Marshal(response)
	if err != nil {
		fmt.Printf("Error marshaling JSON: %s", err.Error())
		return
	}

	// Convert the JSON byte slice to a string
	jsonStr := string(jsonBytes)

	fmt.Println("Recipe Stats \n\n\n\n ")

	// Print the JSON string
	fmt.Println(jsonStr)
}

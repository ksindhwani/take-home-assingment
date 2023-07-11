package stats

type RecipeRequest struct {
	Postcode string `json:"postcode"`
	Recipe   string `json:"recipe"`
	Delivery string `json:"delivery"`
}

type RecipeStats interface {
	GetStat() (interface{}, error)
}

package main

import "github.com/honestbank/interview/pkg/models"

func main() {
	Shark := models.NewAnimal(14, 7)
	Barracude := models.NewAnimal(6, 4)
	Human := models.NewHuman(10)

	Barracude.Bite(&Human.LivingEntity)
	Shark.Bite(&Human.LivingEntity)

	print(Human.LivingEntity.IsDead())
}

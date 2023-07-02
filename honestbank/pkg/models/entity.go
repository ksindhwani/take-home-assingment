package models

type AnimalEntity struct {
	LivingEntity
	BiteForce int
}

type HumanEntity struct {
	LivingEntity
}

type LivingEntity struct {
	LifePoints int
}

func NewAnimal(lifePoints int, bitefForce int) AnimalEntity {
	return AnimalEntity{
		LivingEntity: LivingEntity{
			LifePoints: lifePoints,
		},
		BiteForce: bitefForce,
	}
}

func NewHuman(lifePoints int) HumanEntity {
	return HumanEntity{
		LivingEntity: LivingEntity{
			LifePoints: lifePoints,
		},
	}
}

// Postive to add, negative to reduce
func (le *LivingEntity) UpdateLifePoints(lifePoints int) {
	le.LifePoints += lifePoints
}

func (le *LivingEntity) IsDead() bool {
	return le.LifePoints <= 0
}

func (ae *AnimalEntity) Bite(le *LivingEntity) {
	le.UpdateLifePoints(-1 * ae.BiteForce)
}

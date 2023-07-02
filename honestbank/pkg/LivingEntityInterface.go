package pkg

type LivingEntityInterface interface {
	UpdateLifePoints(LifePoints int)
	IsDead() bool
}

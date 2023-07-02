package pkg

import "github.com/honestbank/interview/pkg/models"

type Animal interface {
	Bite(E models.LivingEntity)
}

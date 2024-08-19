package healthctl

import (
	"github.com/Interhyp/go-backend-service-common/acorns/controller"
	"github.com/StephanHCB/go-autumn-acorn-registry/api"
)

// --- implementing Acorn ---

func New() auacornapi.Acorn {
	return &HealthCtlImpl{}
}

// NewNoAcorn performs the full Acorn lifecycle for this component, no further setup necessary
func NewNoAcorn() controller.HealthController {
	return &HealthCtlImpl{}
}

func (a *HealthCtlImpl) IsHealthController() bool {
	return true
}

func (a *HealthCtlImpl) AcornName() string {
	return controller.HealthControllerAcornName
}

func (a *HealthCtlImpl) AssembleAcorn(registry auacornapi.AcornRegistry) error {
	return nil
}

func (a *HealthCtlImpl) SetupAcorn(registry auacornapi.AcornRegistry) error {
	return nil
}

func (a *HealthCtlImpl) TeardownAcorn(registry auacornapi.AcornRegistry) error {
	return nil
}

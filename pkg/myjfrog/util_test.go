package myjfrog_test

import (
	"github.com/hashicorp/terraform-plugin-framework/provider"
	"github.com/hashicorp/terraform-plugin-framework/providerserver"
	"github.com/hashicorp/terraform-plugin-go/tfprotov6"
	"github.com/jfrog/terraform-provider-myjfrog/pkg/myjfrog"
)

// TestProvider PreCheck(t) must be called before using this provider instance.
var TestProvider provider.Provider

func testAccProviders() map[string]func() (tfprotov6.ProviderServer, error) {
	TestProvider = myjfrog.NewProvider()()

	return map[string]func() (tfprotov6.ProviderServer, error){
		"myjfrog": providerserver.NewProtocol6WithError(TestProvider),
	}
}

package myjfrog_test

import (
	"github.com/hashicorp/terraform-plugin-framework/provider"
	"github.com/hashicorp/terraform-plugin-framework/providerserver"
	"github.com/hashicorp/terraform-plugin-go/tfprotov6"
	"github.com/jfrog/terraform-provider-platform/pkg/platform"
)

// TestProvider PreCheck(t) must be called before using this provider instance.
var TestProvider provider.Provider

func testAccProviders() map[string]func() (tfprotov6.ProviderServer, error) {
	TestProvider = platform.NewProvider()()

	return map[string]func() (tfprotov6.ProviderServer, error){
		"platform": providerserver.NewProtocol6WithError(TestProvider),
	}
}

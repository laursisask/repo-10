terraform {
  required_providers {
    myjfrog = {
      source  = "jfrog/myjfrog"
      version = "1.0.0"
    }
  }
}

provider "myjfrog" {
  // supply JFROG_MYJFROG_API_TOKEN as env var
}

resource "myjfrog_ip_allowlist" "myjfrog-ip-allowlist" {
  server_name = "my-jpd-server-name"
  ips = [
    "1.1.1.7/1",
    "2.2.2.7/1",
  ]
}
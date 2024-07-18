resource "myjfrog_custom_domain_name" "my-custom-domain-name" {
  certificate_name = "mycert"
  certificate_body = "-----BEGIN CERTIFICATE-----\nMIIETzCCAzegAwIBAgIGAY...jLjELMAkGA1UECwwC\n-----END CERTIFICATE-----\n"
  certificate_chain = "-----BEGIN CERTIFICATE-----\nMIIETzCCAzegAwIBAgIGAY...jLjELMAkGA1UECwwC\n-----END CERTIFICATE-----\n"
  certificate_private_key = "-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEAqXr...rEgIP8TXkOkCaiA\n-----END RSA PRIVATE KEY-----\n"
  domains_under_certificate = [
    {
      url = "servername1.com"
      server_name = "serverName1"
      type = "platform_base_url"
    }
  ]
}

resource "myjfrog_custom_domain_name" "my-custom-domain-name-2" {
  certificate_name = "mycert2"
  certificate_body = <<EOT
-----BEGIN CERTIFICATE-----
MIIETzCCAzegAwIBAgIGAY...jLjELMAkGA1UECwwC
-----END CERTIFICATE-----
EOT
  certificate_private_key = <<EOT
-----BEGIN RSA PRIVATE KEY-----
MIIEowIBAAKCAQEAqXr...rEgIP8TXkOkCaiA\
-----END RSA PRIVATE KEY-----
EOT
  domains_under_certificate = [
    {
      url = "servername1.com"
      server_name = "serverName1"
      type = "platform_base_url"
    },
    {
      url = "servername2.com"
      server_name = "serverName2"
      type = "docker_sub_domain"
      docker_repository_name_override = "docker-local"
    }
  ]
}
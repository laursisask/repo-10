# Description

Ethereum Helm charts for Kubernetes - select your choice of `elc` and `clc` client and you can deploy it to K8S. This also supports `aws` and `azure` so you can pick the appropriate provider. If you are running this locally, please make sure you have a lot of resources avialable ie. disk space, cpu and RAM

This currently **only** supports beacon nodes, not validators

## Values

| Key      | Type   | Default     | Description |
|----------|--------|-------------|-------------|
| provider | string | `"local"`   |             |
| network  | string | `"sepolia"` |             |
| env      | string | `"dev"`     |             |
| elc      | string | `"besu"`    |             |
| clc      | string | `"teku"`    |             |



### Test the charts:
local
```
helm template jf ./ --namespace ethereum --create-namespace --values ./examples/mainnet/besu-teku-local.yaml > ./artifacts/besu-teku-mainnet-local.yml
```
aws
```
helm template jw ./ --namespace ethereum --create-namespace --values ./examples/mainnet/besu-teku-aws.yaml >./artifacts/besu-teku-mainnet-aws.yml
```
azure
```
helm template jfbt ./ --namespace ethereum --create-namespace --values ./examples/mainnet/besu-teku-azure.yaml > ./artifacts/besu-teku-mainnet-azure.yml

helm template jfgt ./ --namespace ethereum --create-namespace --values ./examples/mainnet/geth-teku-azure.yaml > ./artifacts/geth-teku-mainnet-azure.yml

helm template jfnt ./ --namespace ethereum --create-namespace --values ./examples/mainnet/nethermind-teku-azure.yaml > ./artifacts/nethermind-teku-mainnet-azure.yml

helm template jfbp ./ --namespace ethereum --create-namespace --values ./examples/mainnet/besu-prysm-azure.yaml > ./artifacts/besu-prysm-mainnet-azure.yml

helm template jfbl ./ --namespace ethereum --create-namespace --values ./examples/mainnet/besu-lighthouse-azure.yaml > ./artifacts/besu-lighthouse-mainnet-azure.yml

KUBECONFIG=~/.kube/besu-azure kubectl -n ethereum apply -f ./artifacts/besu-lighthouse-mainnet-azure.yml
```

## TODO
- fixme: storage class for local deployment?
- support for validators + w3s




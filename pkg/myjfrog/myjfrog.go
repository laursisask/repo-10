package myjfrog

import "strings"

type MyJFrogResponseAPIModel struct {
	Status     string   `json:"status"`
	StatusCode int64    `json:"statusCode"`
	Errors     []string `json:"errors"`
}

func (m MyJFrogResponseAPIModel) Error() string {
	return strings.Join(m.Errors, ", ")
}

package assert

import (
	"testing"

	"github.com/stretchr/testify/internal/testproto"
)

func TestProtoObjectsAreEqual(t *testing.T) {
	tests := []struct {
		name     string
		givenExp interface{}
		givenAct interface{}

		want bool
	}{
		{
			name:     "proto v2 structs equal",
			givenExp: &testproto.MessageV2{Field1: "hi"},
			givenAct: &testproto.MessageV2{Field1: "hi"},

			want: true,
		},
		{
			name:     "proto v2 structs not equal",
			givenExp: &testproto.MessageV2{Field1: "hi"},
			givenAct: &testproto.MessageV2{Field1: "hello"},

			want: false,
		},
		{
			name: "proto v2 struct slices equal",
			givenExp: []*testproto.MessageV2{
				{Field1: "hi"},
				{Field1: "hello"},
				{Field1: "howdy"},
				{Field1: "bonjour"},
			},
			givenAct: []*testproto.MessageV2{
				{Field1: "hi"},
				{Field1: "hello"},
				{Field1: "howdy"},
				{Field1: "bonjour"},
			},

			want: true,
		},
		{
			name: "proto v2 struct slices not equal",
			givenExp: []*testproto.MessageV2{
				{Field1: "hi"},
			},
			givenAct: []*testproto.MessageV2{
				{Field1: "hi"},
				{Field1: "hello"},
			},

			want: false,
		},
		{
			name:     "proto v1 structs",
			givenExp: &testproto.MessageV1{Field1: "hi"},
			givenAct: &testproto.MessageV1{Field1: "hi"},

			want: true,
		},
		{
			name: "proto v1 struct slices equal",
			givenExp: []*testproto.MessageV1{
				{Field1: "hi"},
			},
			givenAct: []*testproto.MessageV1{
				{Field1: "hi"},
			},

			want: true,
		},
		{
			name: "proto v1 struct slices not equal",
			givenExp: []*testproto.MessageV1{
				{Field1: "hi"},
			},
			givenAct: []*testproto.MessageV1{
				{Field1: "hi"},
				{Field1: "hello"},
			},

			want: false,
		},
		{
			name:     "proto gogo structs equal",
			givenExp: &testproto.MessageGoGo{Field1: "hi"},
			givenAct: &testproto.MessageGoGo{Field1: "hi"},

			want: true,
		},
		{
			name:     "proto gogo structs not equal",
			givenExp: &testproto.MessageGoGo{Field1: "hi"},
			givenAct: &testproto.MessageGoGo{Field1: "hello"},

			want: false,
		},
		{
			name: "proto gogo struct slices equal",
			givenExp: []*testproto.MessageGoGo{
				{Field1: "hi"},
			},
			givenAct: []*testproto.MessageGoGo{
				{Field1: "hi"},
			},

			want: true,
		},
		{
			name: "proto gogo struct slices not equal",
			givenExp: []*testproto.MessageGoGo{
				{Field1: "hi"},
				{Field1: "howdy"},
			},
			givenAct: []*testproto.MessageGoGo{
				{Field1: "hi"},
				{Field1: "hello"},
			},

			want: false,
		},
	}

	for _, test := range tests {
		t.Run(test.name, func(t *testing.T) {
			got := ObjectsAreEqual(test.givenExp, test.givenAct)
			if got != test.want {
				t.Errorf("wanted %t, got %t.\n\nExpected:\n%#v\n\nActual:\n%#v\n",
					test.want, got, test.givenExp, test.givenAct)
			}
		})
	}

}

package main

import (
	"fmt"
	"strings"
)

func main() {
	var str string
	fmt.Scan(&str)
	str = strings.ReplaceAll(str, "^[u]", "")
	fmt.Println(len(str))
}

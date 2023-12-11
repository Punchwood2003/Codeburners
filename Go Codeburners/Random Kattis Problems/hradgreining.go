package main

import (
	"fmt"
	"strings"
)

func main() {
	var input string
	fmt.Scan(&input)
	var index int
	index = strings.Index(input, "COV")
	if index == -1 {
		fmt.Println("Ekki veikur!")
	} else {
		fmt.Println("Veikur!")
	}
}

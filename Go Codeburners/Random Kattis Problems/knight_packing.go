package main

import (
	"fmt"
)

func main() {
	var n int
	fmt.Scan(&n)
	if n%2 == 1 {
		fmt.Println("first")
	} else {
		fmt.Println("second")
	}
}

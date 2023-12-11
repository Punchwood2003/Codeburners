package main

import (
	"fmt"
)

func main() {
	var x, y int
	fmt.Scan(&x, &y)

	xpos := x > 0
	ypos := y > 0

	if xpos && ypos {
		fmt.Println(1)
	} else if !xpos && ypos {
		fmt.Println(2)
	} else if !xpos && !ypos {
		fmt.Println(3)
	} else {
		fmt.Println(4)
	}
}

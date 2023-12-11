package main

import (
	"fmt"
)

func main() {
	var n, k int
	fmt.Scan(&n, &k)

	var timeLeft = 240 - k
	var i = 1
	for ; i <= n; i++ {
		timeLeft -= 5 * i
		if timeLeft < 0 {
			break
		}
	}

	fmt.Println(i - 1)
}

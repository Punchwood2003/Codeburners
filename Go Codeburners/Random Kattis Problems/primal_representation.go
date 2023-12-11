package main

import (
	"fmt"
)

func main() {
	var num int64
	_, err := fmt.Scanln(&num)

	for err == nil {
		// Print out the -1
		if num < 0 {
			fmt.Printf("-1 ")
			num *= -1
		}

		var i int64 = 2
		for i*i <= num {
			var pow int64 = 0
			for num%i == 0 {
				pow++
				num /= i
			}

			if pow == 1 {
				fmt.Printf("%d ", i)
			} else if pow > 1 {
				fmt.Printf("%d^%d ", i, pow)
			}
			i++
		}

		if num > 1 {
			fmt.Printf("%d", num)
		}
		fmt.Println()
		_, err = fmt.Scanln(&num)
	}
}

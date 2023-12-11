package main

import (
	"fmt"
)

func main() {
	var n int
	fmt.Scan(&n)

	var a = make([]int, n)
	for i := 0; i < n; i++ {
		fmt.Scan(&a[i])
		a[i]--
	}

	// Implement a sliding window to find the longest subarray where
	// the value of elements on the left side of the window is equal
	// to one another, and the values of elements on the right side
	// of the window is equal to one another, and the quantity of
	// elements on the left side of the window is equal to the
	// quantity of elements on the right side of the window.
	var max = 0
	for i := 0; i < n; i++ {
		var left = make(map[int]int)
		var right = make(map[int]int)

		for j := 0; j < i; j++ {
			left[a[j]]++
		}

		for j := i; j < n; j++ {
			right[a[j]]++
		}

		var count = 0
		for k, v := range left {
			if right[k] == v {
				count += v
			}
		}

		if count > max {
			max = count
		}
	}

	fmt.Println(max)
}

package main

import (
	"fmt"
)

func main() {
	var n, m, k int
	fmt.Scan(&n, &m, &k)

	// Perform a binary search over the multiplication table of size m x n, looking for the kth largest number
	// in the table.
	// The search space is [1, m*n], so we start with low = 1 and high = m*n.
	// We then calculate the mid point, and count the number of elements in the table that are less than or equal
	// to the mid point. If this count is less than k, then we know that the kth largest element is greater than
	// the mid point, so we set low = mid + 1. If the count is greater than or equal to k, then we know that the
	// kth largest element is less than or equal to the mid point, so we set high = mid.
	// We continue this process until low == high, at which point we know that we have found the kth largest element.
	low := 1
	high := m * n
	for low < high {
		mid := low + (high-low)/2
		count := countLessThan(mid, n, m)
		if count < k {
			low = mid + 1
		} else {
			high = mid
		}
	}

	fmt.Println(low)
}

// countLessThan returns the number of elements in the multiplication table of size m x n that are less than or equal
// to the given number.
func countLessThan(num, n, m int) int {
	count := 0
	for i := 1; i <= n; i++ {
		count += min(num/i, m)
	}
	return count
}

// min returns the minimum of the two given numbers.
func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}

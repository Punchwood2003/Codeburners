package main

import (
	"fmt"
)

func main() {
	var numTimes, i int64
	fmt.Scan(&numTimes)

	primes := getPrimesLessThanNum(10_000)

	for i = 0; i < numTimes; i++ {
		var K, m int64
		fmt.Scan(&K, &m)

		if m == 1 {
			fmt.Printf("%d %d NO\n", K, m)
			continue
		}
		if primes[m] {
			if isHappy(m) {
				fmt.Printf("%d %d YES\n", K, m)
			} else {
				fmt.Printf("%d %d NO\n", K, m)
			}
		} else {
			fmt.Printf("%d %d NO\n", K, m)
		}
	}
}

func getPrimesLessThanNum(num int64) []bool {
	primes := make([]bool, num+1)
	var i, j int64

	for i = 0; i < num+1; i++ {
		primes[i] = true
	}

	for i = 2; i*i <= num; i++ {
		if primes[i] {
			for j = i * 2; j <= num; j += i {
				primes[j] = false
			}
		}

	}

	return primes
}

func isHappy(num int64) bool {
	var sum int64
	seen := make(map[int64]bool)
	for sum != 1 && !seen[num] {
		seen[num] = true
		sum = 0
		for num > 0 {
			sum += (num % 10) * (num % 10)
			num /= 10
		}
		num = sum
	}
	return sum == 1
}

// Credit for template goes to...
// - https://codeforces.com/blog/entry/94245
// - https://www.codementor.io/@tucnak/using-golang-for-competitive-programming-h8lhvxzt3

package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

var outputFile, _ = os.Create("output/two_apples_a_day_output.txt")
var inputFile, _ = os.Open("input/two_apples_a_day_input.txt")

var file *bufio.Reader = bufio.NewReader(inputFile)
var out *bufio.Writer = bufio.NewWriter(outputFile)

func printf(f string, a ...interface{}) { fmt.Fprintf(out, f, a...) }
func scanf(f string, a ...interface{})  { fmt.Fscanf(file, f, a...) }

func readInt(in *bufio.Reader) int {
	nStr, _ := in.ReadString('\n')
	nStr = strings.ReplaceAll(nStr, "\r", "")
	nStr = strings.ReplaceAll(nStr, "\n", "")
	n, _ := strconv.Atoi(nStr)
	return n
}

func readLineNumbs(in *bufio.Reader) []string {
	line, _ := in.ReadString('\n')
	line = strings.ReplaceAll(line, "\r", "")
	line = strings.ReplaceAll(line, "\n", "")
	numbs := strings.Split(line, " ")
	return numbs
}

func readArrInt(in *bufio.Reader) []int {
	numbs := readLineNumbs(in)
	arr := make([]int, len(numbs))
	for i, n := range numbs {
		val, _ := strconv.Atoi(n)
		arr[i] = val
	}
	return arr
}

func readArrInt64(in *bufio.Reader) []int64 {
	numbs := readLineNumbs(in)
	arr := make([]int64, len(numbs))
	for i, n := range numbs {
		val, _ := strconv.ParseInt(n, 10, 64)
		arr[i] = val
	}
	return arr
}

func main() {
	defer out.Flush()

	numTimes := readInt(file)
	for i := 1; i <= numTimes; i++ {
		n := readInt(file)
		arr := readArrInt(file)

		if n == 1 {
			printf("Case #%d: %d\n", i, arr[0])
			continue
		}

		// Sort the array
		sort.Ints(arr)
		n = (n * 2) - 1

		// Iterate over what index we are removing from the slice
		found := false
		for j := n - 1; j >= 0; j-- {
			// Remove the element at index j
			temp := arr[j]
			arr = append(arr[:j], arr[j+1:]...)

			// Check to make sure that all the sums are the same
			previousSum := arr[0] + arr[n-2]
			correct := true
			for j := 1; j < n/2; j++ {
				if arr[j]+arr[n-2-j] != previousSum {
					correct = false
					break
				}
			}
			if correct {
				ans := previousSum - temp
				if ans > 0 {
					found = true
					printf("Case #%d: %d\n", i, ans)
					break
				}
			}

			// Add back the element we removed
			if j == 0 {
				arr = append([]int{temp}, arr...)
			} else if j == n-1 {
				arr = append(arr, temp)
			} else {
				before := arr[:j]
				after := arr[j:]
				arr = append(before, append([]int{temp}, after...)...)
			}
		}

		// The answer did not exist
		if !found {
			printf("Case #%d: -1\n", i)
		}
	}
}

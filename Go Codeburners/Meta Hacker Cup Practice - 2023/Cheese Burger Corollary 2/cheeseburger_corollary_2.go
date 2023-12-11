// Credit for template goes to...
// - https://codeforces.com/blog/entry/94245
// - https://www.codementor.io/@tucnak/using-golang-for-competitive-programming-h8lhvxzt3

package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

var outputFile, _ = os.Create("output/cheeseburger_corollary_2_validation_output.txt")
var inputFile, _ = os.Open("input/cheeseburger_corollary_2_validation_input.txt")

var file *bufio.Reader = bufio.NewReader(inputFile)
var out *bufio.Writer = bufio.NewWriter(outputFile)

func printf(f string, a ...interface{}) { fmt.Fprintf(outputFile, f, a...) }
func scanf(f string, a ...interface{})  { fmt.Fscanf(inputFile, f, a...) }

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
		arr := readArrInt64(file)
		a, b, c := arr[0], arr[1], arr[2]

		var numPatties int64
		var numBuns int64
		pricePerPattyForB := float64(b) / 2.0
		if pricePerPattyForB <= float64(a) {
			numPatties = (c / b) * 2
			numBuns = numPatties

			c %= b

			numPatties += c / a
			numBuns += (c / a) * 2
		} else {
			numPatties = c / a
			numBuns = numPatties * 2
		}

		diff := numBuns - numPatties
		if diff > 0 {
			printf("Case #%d: %d\n", i, numPatties)
		} else if numPatties > 0 {
			printf("Case #%d: %d\n", i, numBuns-1)
		} else {
			printf("Case #%d: %d\n", i, 0)
		}
	}
}

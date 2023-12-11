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

var outputFile, _ = os.Create("output/cheeseburger_corollary_1_validation_output.txt")
var inputFile, _ = os.Open("input/cheeseburger_corollary_1_validation_input.txt")

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
		arr := readArrInt(file)
		s, d, k := arr[0], arr[1], arr[2]

		numPatties := s + (d * 2)
		numBuns := (s * 2) + (d * 2)

		if numPatties >= k && numBuns >= k+1 {
			printf("Case #%d: YES\n", i)
		} else {
			printf("Case #%d: NO\n", i)
		}
	}
}

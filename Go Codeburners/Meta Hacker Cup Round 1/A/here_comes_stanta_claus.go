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

var inputFile, _ = os.Open("input/here_comes_santa_claus_validation_input.txt")
var outputFile, _ = os.Create("output/here_comes_santa_claus_validation_output.txt")

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
		numElves := readInt(file)
		elves := readArrInt(file)

		// Sort the elves
		sort.Ints(elves)

		if numElves == 4 {
			midLeft := float64(elves[0]+elves[1]) / 2.0
			midRight := float64(elves[2]+elves[3]) / 2.0
			printf("Case #%d: %.6f\n", i, midRight-midLeft)
		} else if numElves == 5 {
			midLeft1 := float64(elves[0]+elves[1]+elves[2]) / 3.0
			midRight1 := float64(elves[3]+elves[4]) / 2.0
			dist1 := midRight1 - midLeft1

			midLeft2 := float64(elves[0]+elves[1]) / 2.0
			midRight2 := float64(elves[2]+elves[3]+elves[4]) / 3.0
			dist2 := midRight2 - midLeft2

			if dist1 > dist2 {
				printf("Case #%d: %.6f\n", i, dist1)
			} else {
				printf("Case #%d: %.6f\n", i, dist2)
			}

		} else {
			midLeft := float64(elves[0]+elves[1]) / 2.0
			midRight := float64(elves[numElves-2]+elves[numElves-1]) / 2.0
			printf("Case #%d: %.6f\n", i, midRight-midLeft)
		}
	}
}

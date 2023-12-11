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

var file *bufio.Reader = bufio.NewReader(os.Stdin)
var out *bufio.Writer = bufio.NewWriter(os.Stdout)

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

	n := readInt(file)
	a := readArrInt(file)
	sort.Ints(a)

	q := readInt(file)

	for i := 0; i < q; i++ {
		m := readInt(file)

		if m < a[0] {
			printf("0\n")
			continue
		} else if m >= a[n-1] {
			printf("%d\n", n)
			continue
		}

		var low, high = 0, n
		for low < high {
			var mid = (low + high) / 2
			if a[mid] <= m {
				low = mid + 1
			} else {
				high = mid
			}
		}

		printf("%d\n", low)
	}
}

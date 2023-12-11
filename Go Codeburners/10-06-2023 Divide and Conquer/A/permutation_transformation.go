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

// Create a node struct for a binary tree
type node struct {
	val   int
	left  *node
	right *node
}

func findMax(nums []int, start int, end int) int {
	max := nums[start]
	maxIndex := start
	for i := start; i <= end; i++ {
		if nums[i] > max {
			max = nums[i]
			maxIndex = i
		}
	}
	return maxIndex
}

func createTree(nums []int, start int, end int) *node {
	if start > end {
		return nil
	}

	maxIndex := findMax(nums, start, end)
	root := &node{val: nums[maxIndex]}
	root.left = createTree(nums, start, maxIndex-1)
	root.right = createTree(nums, maxIndex+1, end)
	return root
}

func printTree(root *node, depth int) {
	if root == nil {
		return
	}
	printTree(root.left, depth+1)
	printf("%d ", depth)
	printTree(root.right, depth+1)
}

func main() {
	defer out.Flush()

	numTimes := readInt(file)
	for i := 0; i < numTimes; i++ {
		numElements := readInt(file)
		nums := readArrInt(file)

		node := createTree(nums, 0, numElements-1)
		printTree(node, 0)
		printf("\n")
	}
}

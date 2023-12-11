// Credit for template goes to...
// - https://codeforces.com/blog/entry/94245
// - https://www.codementor.io/@tucnak/using-golang-for-competitive-programming-h8lhvxzt3

package main

import (
	"bufio"
	"fmt"
	"math"
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

func main() {
	defer out.Flush()

	numTimes := readInt(file)
	for i := 0; i < numTimes; i++ {
		numElements := readInt(file)
		arr := readArrInt(file)

		height := int(math.Ceil(math.Log2(float64(numElements))))
		// Make the tree
		tree := createTree(arr, height, numElements)

		// Determine if the tree can be beautiful
		// This means that the leaves must be in ascending order
		// Additionally, it is possible that a given tree can never be beautiful
		// Determine the smalles number of swaps to make the tree beautiful
		// If it is not possible, print -1
		// Otherwise, print the number of swaps
		numSwaps := 0
		if isBeautiful(tree, &numSwaps) {
			printf("%d\n", numSwaps)
		} else {
			printf("-1\n")
		}
	}
}

var index = -1

func createTree(arr []int, height int, numElements int) *node {
	if index >= numElements {
		return nil
	}

	if height == 0 {
		index += 1
		return &node{val: arr[index]}
	}

	return &node{
		left:  createTree(arr, height-1, numElements),
		right: createTree(arr, height-1, numElements),
	}
}

func isBeautiful(tree *node, numSwaps *int) bool {
	if tree == nil {
		return true
	}

	if tree.left == nil && tree.right == nil {
		return true
	}

	if tree.left != nil && tree.right != nil {
		if tree.left.val > tree.right.val {
			// Swap the values
			*numSwaps++
			tree.left.val, tree.right.val = tree.right.val, tree.left.val
		}
	}

	return isBeautiful(tree.left, numSwaps) && isBeautiful(tree.right, numSwaps)
}

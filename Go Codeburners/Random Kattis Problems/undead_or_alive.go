package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	var scanner = bufio.NewScanner(os.Stdin)
	var line string
	if scanner.Scan() {
		line = scanner.Text()
	}

	var smiley = strings.Contains(line, ":)")
	var frowny = strings.Contains(line, ":(")

	if smiley && frowny {
		fmt.Println("double agent")
	} else if smiley {
		fmt.Println("alive")
	} else if frowny {
		fmt.Println("undead")
	} else {
		fmt.Println("machine")
	}
}

package zyBooks;

import java.util.Scanner;

public class LabProgram {
    public static final int MAX_LENGTH = 19;
    public static void drawTriangle(int numStars) {
        System.out.println("Adding Method " + numStars + " to the stack");
        if (numStars < 0) {
            System.out.println("Base Case hit");
            return;
        }
        drawTriangle(numStars - 2);
        System.out.println("Executing Method " + numStars);
        int numSpaces = (MAX_LENGTH - numStars) / 2; 
        for (int i = 0; i < numSpaces; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < numStars; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        int length = scnr.nextInt();
        drawTriangle(length);
    }
}
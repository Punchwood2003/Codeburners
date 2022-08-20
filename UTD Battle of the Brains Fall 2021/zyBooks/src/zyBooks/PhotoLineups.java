package zyBooks;

import java.util.Scanner;
import java.util.ArrayList;

public class PhotoLineups {
    public static void allPermutations(ArrayList<String> permList, ArrayList<String> nameList) {
        if(nameList.size() == 0) {
            for(String temp : permList) {
                System.out.print(temp + " ");
            }
            System.out.println();
            return;
        }
        for(int i = 0; i < nameList.size(); i++) {
            String currName = nameList.get(i);
            permList.add(currName);
            nameList.remove(currName);
            allPermutations(permList, nameList);
            permList.remove(currName);
            nameList.add(i, currName);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> permList = new ArrayList<String>();
        String[] names = scan.nextLine().split(" ");
        for(int i = 0; i < names.length-1; i++) {
            nameList.add(names[i]);
        }
        allPermutations(permList, nameList);
    }
}

package Lab1;

import java.util.Scanner;
import java.util.ArrayList;

import static Lab1.Functions.*;
import static Lab1.Exceptions.*;

public class Main {

    public static void getInput(String[] args, ArrayList<Integer> array) throws NoNumbersException{
        if (args.length != 0) {
            for (String arg : args) {
                try {
                    int x = Integer.parseInt(arg);
                    array.add(x);
                } catch (NumberFormatException ignored) {
                }
            }
        } else {
            Scanner scn = new Scanner(System.in);
            System.out.print("Give arguments: ");
            String str = scn.nextLine();

            scn = new Scanner(str);
            scn.useDelimiter(" ");

            while (scn.hasNext()) {
                try {
                    int x = Integer.parseInt(scn.next());
                    array.add(x);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        if(array.isEmpty())
            throw new NoNumbersException();
    }


    public static ArrayList<Integer> ex1(ArrayList<Integer> array) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int num : array)
            if (is_prime(num))
                results.add(num);
        return results;
    }

    public static int ex2(ArrayList<Integer> array) {
        int max = Integer.MIN_VALUE;
        for (int num : array)
            if (num > max)
                max = num;
        return max;
    }

    public static int ex3(ArrayList<Integer> array) {
        int min = Integer.MAX_VALUE;
        for (int num : array)
            if (num < min)
                min = num;
        return min;
    }

    public static long ex4(ArrayList<Integer> array) {
        for (int i = 0; i < array.size(); ++i)
            if (array.get(i) <= 0)
                array.remove(i);

        long result = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            result = gcd(result, array.get(i));
        }
        return result;
    }

    public static long ex5(ArrayList<Integer> array) {
        for (int i = 0; i < array.size(); ++i)
            if (array.get(i) <= 0)
                array.remove(i);

        long result = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            result = lcm(result, array.get(i));
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        while (true){
            try {
                getInput(args, array);
                System.out.println("Prime numbers are: " + ex1(array));//prime numbers
                System.out.println("Maxim number is: " + ex2(array)); //max number
                System.out.println("Minim number is: " + ex3(array)); //min number
                System.out.println("GCD is: " + ex4(array)); //gcd
                System.out.println("LCM is: " + ex5(array)); //lcm
                System.exit(0);
            }catch(NoNumbersException ignored){}
        }

    }

}

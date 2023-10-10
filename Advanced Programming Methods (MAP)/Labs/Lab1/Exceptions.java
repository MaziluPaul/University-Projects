package Lab1;

public class Exceptions {

    public static class NoNumbersException extends Exception {
        public NoNumbersException() {
            super();
            System.out.println("There are no numbers in the provided list! Try again!");
        }
    }
}

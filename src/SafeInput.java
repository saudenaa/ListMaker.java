import java.util.Scanner;

public class SafeInput {

    // Method to get a non-empty string from the user
    public static String getNonZeroLenString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
        } while (input.trim().isEmpty());
        return input;
    }

    // Method to get an integer within a specified range from the user
    public static int getRangedInt(Scanner scanner, String prompt, int low, int high) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Discard invalid input
            }
            input = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
        } while (input < low || input > high);
        return input;
    }

    // Method to get a valid string that matches a regular expression
    public static String getRegExString(Scanner scanner, String prompt, String regEx) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
        } while (!input.matches(regEx));
        return input;
    }

    // Method to get a Yes/No confirmation from the user
    public static boolean getYNConfirm(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt + " [Y/N]: ");
            input = scanner.nextLine().trim().toUpperCase();
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("Y");
    }
}



import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class





































































































































ListMaker {

    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false; // Tracks if the list has unsaved changes
    private static String currentFileName = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = SafeInput.getRegExString(scanner,
                    "Enter a choice: ", "[AaDdIiMmOoSsCcVvQq]").toUpperCase();

            switch (choice) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "I":
                    insertItem(scanner);
                    break;
                case "M":
                    moveItem(scanner);
                    break;
                case "O":
                    loadList(scanner);
                    break;
                case "S":
                    saveList(scanner);
                    break;
                case "C":
                    clearList(scanner);
                    break;
                case "V":
                    printList();
                    break;
                case "Q":
                    if (checkUnsavedChanges(scanner)) {
                        running = false;
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    // Display the menu
    private static void displayMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list from file");
        System.out.println("S - Save the current list to file");
        System.out.println("C - Clear the list");
        System.out.println("V - View the list");
        System.out.println("Q - Quit the program");
        System.out.print("Current List: ");
        printList();
    }

    private static void addItem(Scanner scanner) {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter an item to add: ");
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added to the list.");
    }

    private static void deleteItem(Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }
        printList();
        int index = SafeInput.getRangedInt(scanner, "Enter the item number to delete: ", 1, list.size()) - 1;
        list.remove(index);
        needsToBeSaved = true;
        System.out.println("Item deleted from the list.");
    }

    private static void insertItem(Scanner scanner) {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter an item to insert: ");
        int index = SafeInput.getRangedInt(scanner, "Enter the position number to insert the item: ", 1, list.size() + 1) - 1;
        list.add(index, item);
        needsToBeSaved = true;
        System.out.println("Item inserted into the list.");
    }

    private static void moveItem(Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to move.");
            return;
        }
        printList();
        int fromIndex = SafeInput.getRangedInt(scanner, "Enter the item number to move: ", 1, list.size()) - 1;
        int toIndex = SafeInput.getRangedInt(scanner, "Enter the new position: ", 1, list.size()) - 1;

        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void loadList(Scanner scanner) {
        if (checkUnsavedChanges(scanner)) {
            String fileName = SafeInput.getNonZeroLenString(scanner, "Enter the file name to open (without .txt): ");
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"))) {
                list.clear();
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
                currentFileName = fileName;
                needsToBeSaved = false;
                System.out.println("List loaded successfully.");
            } catch (IOException e) {
                System.out.println("Error loading the file: " + e.getMessage());
            }
        }
    }

    private static void saveList(Scanner scanner) {
        if (currentFileName == null) {
            currentFileName = SafeInput.getNonZeroLenString(scanner, "Enter the file name to save as (without .txt): ");
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFileName + ".txt"))) {
            for (String item : list) {
                writer.println(item);
            }
            needsToBeSaved = false;
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }

    private static void clearList(Scanner scanner) {
        if (SafeInput.getYNConfirm(scanner, "Are you sure you want to clear the list?")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }

    private static void printList() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }
    }

    private static boolean checkUnsavedChanges(Scanner scanner) {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(scanner, "You have unsaved changes. Do you want to save?");
            if (save) {
                saveList(scanner);
            }
            return save;
        }
        return true;
    }
}

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// this is simple phonebook project, it has been made by Yousef Walid - Alamein International University Student

public class PhoneBook {
    static String FILE_PATH = "C:\\Users\\Yov\\OneDrive\\Desktop\\Univ\\phonebook.txt"; // path of the txt file
    static Scanner CONSOLE = new Scanner(System.in);

    public static void main(String[] args) {
        showMainMenu();
        int userinput = CONSOLE.nextInt();

        while (userinput != 8) {
            if (userinput == 1) { // load
                load();
            } else if (userinput == 2) { // query
                query();
            } else if (userinput == 3) { // add
                add();
            } else if (userinput == 4) { // delete
                System.out.println("\n------------ Delete ------------");
                showWayToSearchMenu();
                int selectedSearch = CONSOLE.nextInt();
                if (selectedSearch == 1) {
                    System.out.print("\nEnter the name to search: ");
                    String nameToSearch = CONSOLE.next();
                    deleteEntry(nameToSearch);
                } else if (selectedSearch == 2) {
                    System.out.print("\nEnter the name to search: ");
                    String nameToSearch = CONSOLE.next();
                    deleteEntry(nameToSearch);
                } else if (selectedSearch == 3) {
                    System.out.print("\nEnter the address to search: ");
                    String addressToSearch = CONSOLE.next();
                    deleteEntry(addressToSearch);
                } else if (selectedSearch == 4) {
                    System.out.print("\nEnter the city to search: ");
                    String cityToSearch = CONSOLE.next();
                    deleteEntry(cityToSearch);
                } else if (selectedSearch == 5) {
                    System.out.print("\nEnter the zipcode to search: ");
                    String zipToSearch = CONSOLE.next();
                    deleteEntry(zipToSearch);
                }
            } else if (userinput == 5) { // modify
                modify();
            } else if (userinput == 6) { // print
                print();
            } else if (userinput == 7) { // save
                save();
            } else System.out.println("Error, select number from the menu.");
            showMainMenu();
            userinput = CONSOLE.nextInt();
        }
    }
    public static void showMainMenu() {
        System.out.println("""

                This is PhoneBook program, select what method you want to use
                ------------{ Main Menu }------------
                1- Load, (to load all data in phonebook)\t
                2- Query, (to look up information about a specific entry)\t
                3- Add, (to add new user in phonebook)\t
                4- Delete, (to delete a user from phonebook)\t
                5- Modify, (to modify the information for one of the records)\t
                6- Print, (to print entire dictionary in set of order)\t
                7- Save (to reset the data in phonebook)\t
                8- Quit""");
    }
    public static void showWayToSearchMenu() {
        System.out.println("""
                    select way you want to search with:
                    1- First Name.
                    2- Last Name.
                    3- Address.
                    4- City.
                    5- ZipCode.""");
    }
    public static void search(String nameToSearch) {
        String filePath = FILE_PATH;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            boolean found = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(nameToSearch)) {
                    System.out.println("\nFound the entry:");
                    found = true;
                    String[] data = line.split(",");
                    for (String info : data) {
                        System.out.print(STR."\{info}, ");
                    }
                    System.out.println();
                    break;
                }
            }
            if (!found) {
                System.out.println("\nNo entry found for the given name.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void add() {
        String filePath = FILE_PATH;
        System.out.println("\n------------ Add ------------");

        System.out.print("\nEnter First Name: ");
        String firstName = CONSOLE.next();

        System.out.print("Enter Last Name: ");
        String lastName = CONSOLE.next();

        System.out.print("Enter Address: ");
        String address = CONSOLE.nextLine();
        address += CONSOLE.nextLine();

        System.out.print("Enter City: ");
        String city = CONSOLE.next();

        System.out.print("Enter Zip Code: ");
        String zipCode = CONSOLE.next();

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            String newEntry = STR."\{firstName},\{lastName},\{address},\{city},\{zipCode}\n";
            fileWriter.write(newEntry);
            System.out.println("\nNew entry added successfully.");
        } catch (IOException e) {
            System.out.println(STR."Error: \{e.getMessage()}");
        }
    }


    public static void deleteEntry(String nameToDelete) {
        String filePath = FILE_PATH;
        try (FileReader fileReader = new FileReader(filePath);
             FileWriter fileWriter = new FileWriter("tempFile.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.toLowerCase().contains(nameToDelete.toLowerCase())) {
                    printWriter.write(STR."\{line}\n");
                }
            }
        } catch (IOException e) {
            System.out.println(STR."An error occurred while deleting the entry: \{e.getMessage()}");
        }

        // After deleting the entry, we need to replace the original file with the temporary file.
        File originalFile = new File(filePath);
        File tempFile = new File("tempFile.txt");
        if (originalFile.delete() && tempFile.renameTo(originalFile)) {
            System.out.println("Entry deleted successfully.");
        } else {
            System.out.println("Failed to delete the entry or replace the file.");
        }
    }
    public static void modify() {
        System.out.println("\n------------ Modify ------------");
        System.out.print("Enter the first/last name to search: ");
        String nameToSearch = CONSOLE.next();

        String filePath = FILE_PATH;
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            StringBuilder stringBuilder = new StringBuilder();
            boolean found = false;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(nameToSearch)) {
                    found = true;
                    System.out.println("\nFound the entry:");
                    String[] data = line.split(",");
                    String oldFirstName = data[0], oldLastName = data[1], oldAddress = data[2], oldCity = data[3], oldZipCode = data[4];
                    for (int i = 0; i < data.length; i++) {
                        System.out.println(STR."\{i + 1}. \{data[i]} ");
                    }
                    System.out.println();
                    System.out.print("Enter the number of the entry to modify: ");
                    int selectedEntry = CONSOLE.nextInt();
                    if (selectedEntry == 1) {
                        System.out.print("Enter the new First Name: ");
                        String newFirstName = CONSOLE.next();
                        line = STR."\{newFirstName},\{oldLastName},\{oldAddress},\{oldCity},\{oldZipCode}";
                    } else if (selectedEntry == 2) {
                        System.out.print("Enter the new Last Name: ");
                        String newLastName = CONSOLE.next();
                        line = STR."\{oldFirstName},\{newLastName},\{oldAddress},\{oldCity},\{oldZipCode}";
                    } else if (selectedEntry == 3) {
                        System.out.print("Enter the new Address: ");
                        String newAddress = CONSOLE.next();
                        line = STR."\{oldFirstName},\{oldLastName},\{newAddress},\{oldCity},\{oldZipCode}";
                    } else if (selectedEntry == 4) {
                        System.out.print("Enter the new City: ");
                        String newCity = CONSOLE.next();
                        line = STR."\{oldFirstName},\{oldLastName},\{oldAddress},\{newCity},\{oldZipCode}";
                    } else if (selectedEntry == 5) {
                        System.out.print("Enter the new Zip Code: ");
                        String newZipCode = CONSOLE.next();
                        line = STR."\{oldFirstName},\{oldLastName},\{oldAddress},\{oldCity},\{newZipCode}";
                    }
                }
                stringBuilder.append(line).append("\n");
            }
            if (found) {
                try (FileWriter fileWriter = new FileWriter(filePath, false)) {
                    fileWriter.write(stringBuilder.toString());
                } catch (IOException e) {
                    System.out.println(STR."An error occurred while modifying the entry: \{e.getMessage()}");
                }
            } else {
                System.out.println("\nNo entry found for the given name.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void save() {
        System.out.println("\n------------ Save ------------");
        String filePath = FILE_PATH;
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("");
            fileWriter.write("First Name,Last Name,Address,City,ZipCode\n");
            System.out.println("\nPhonebook saved successfully.");
        } catch (IOException e) {
            System.out.println(STR."An error occurred while saving the phonebook: \{e.getMessage()}");
        }
    }
    public static void load() {
        String filePath = FILE_PATH;
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println();
        }
    }
    public static void query() {
        Scanner CONSOLE = new Scanner(System.in);
        System.out.println("\n------------ Query ------------");
        showWayToSearchMenu();
        int selectedSearch = CONSOLE.nextInt();
        if (selectedSearch == 1) {
            System.out.print("\nEnter the name to search: ");
            String nameToSearch = CONSOLE.next();
            search(nameToSearch);
        } else if (selectedSearch == 2) {
            System.out.print("\nEnter the name to search: ");
            String nameToSearch = CONSOLE.next();
            search(nameToSearch);
        } else if (selectedSearch == 3) {
            System.out.print("\nEnter the address to search: ");
            String addressToSearch = CONSOLE.next();
            search(addressToSearch);
        } else if (selectedSearch == 4) {
            System.out.print("\nEnter the city to search: ");
            String cityToSearch = CONSOLE.next();
            search(cityToSearch);
        } else if (selectedSearch == 5) {
            System.out.print("\nEnter the zipcode to search: ");
            String zipToSearch = CONSOLE.next();
            search(zipToSearch);
        }
    }
    public static void print() {
        String filePath = FILE_PATH;
        String[] lines;
        try {
            List<String> lineList = Files.readAllLines(Paths.get(filePath));
            lines = lineList.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println(STR."Error reading file: \{e.getMessage()}");
            return;
        }
        showWayToSearchMenu();
        int selectedSearch = CONSOLE.nextInt();

        Arrays.sort(lines, (a, b) -> {
            String[] aData = a.split(",");
            String[] bData = b.split(",");
            return switch (selectedSearch) {
                case 1 -> aData[0].compareTo(bData[0]);
                case 2 -> aData[1].compareTo(bData[1]);
                case 3 -> aData[2].compareTo(bData[2]);
                case 4 -> aData[3].compareTo(bData[3]);
                case 5 -> aData[4].compareTo(bData[4]);
                default -> throw new RuntimeException("Invalid column selection");
            };
        });
        System.out.println("First Name,Last Name,Address,City,ZipCode");
        for (String line : lines) {
            if (!line.equals("First Name,Last Name,Address,City,ZipCode")) {
                System.out.println(line);
            }
        }
    }
}
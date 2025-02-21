import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner user = new Scanner(System.in);

    public static ArrayList<Event> calendar;

    public static void main(String[] args) {
        calendar = new ArrayList<>();

        //for testing purposes
        for (int i=0; i<1000; i++) {
            int randomDay = (int) (Math.random()*31);
            int randomMonth = (int) (Math.random()*12 + 1);
            int randomYear = (int) (Math.random()*2030);
            calendar.add(new Event("Gaming Session", new int[]{randomDay,randomMonth,randomYear})); // placeholder for testing print method
        }
        String input = "";
        while (!input.equals("stop")) {
            System.out.print("Please input a command: ");
            input = user.next().toLowerCase();
            switch (input) {
                case "add":
                    add();
                    break;
                case "edit":
                    edit();
                    break;
                case "sort":
                    sort();
                    break;
                case "print":
                    printCalendar(calendar);
                    break;
                case "help":
                    System.out.println("Commands: add, edit, sort, print, help");
                    break;
                case "stop":
                    break;
                default:
                    System.out.println("Invalid response. Command does not exist.");
                    break;
            }
        }

    }

    // EXTENSIONS OF THE MAIN METHOD

    // What happens when the user uses the command "add".
    public static void add() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("What would you like to add? ");
            answer = user.next().toLowerCase();
            switch (answer) {
                case "event":
                    System.out.print("(unfinished)\n");
                    break;
                case "help":
                    System.out.println("Commands: event, help");
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Invalid response. Command does not exist.");
                    break;
            }
        }
    }

    // What happens when the user uses the command "edit".
    public static void edit() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("Which event would you like to edit? ");
            answer = user.next().toLowerCase();
            int index;
            try {
                index = Integer.parseInt(answer) - 1;
                eventEdit(index);
            }
            catch (NumberFormatException e) {
                if (!answer.equals("exit")) {
                    System.out.println("Please input an integer value.");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("That value is outside the range of events on the calendar.");
            }
        }
    }

    // Prompts the user to select options for sorting the calendar.
    public static void sort() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("How would you like to sort the calendar? ");
            answer = user.next().toLowerCase();
            String answer2 = "";
            switch (answer) {
                case "date":
                    System.out.print("Ascending or descending? ");
                    answer2 = user.next().toLowerCase();
                    switch (answer2) {
                        case "ascending":
                            calendar = sortDate(calendar, true);
                            break;
                        case "descending":
                            calendar = sortDate(calendar, false);
                            break;
                        case "":
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.println("Invalid response.");
                            }
                            break;
                    }
                    break;
                case "group":
                    System.out.print("Ascending or descending?");
                    answer2 = user.next().toLowerCase();
                    switch (answer2) {
                        case "ascending":
                            calendar = sortGroup(calendar, true);
                            break;
                        case "descending":
                            calendar = sortGroup(calendar, false);
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.println("Invalid response.");
                            }
                            break;
                    }
                    break;
                case "title":
                    System.out.print("Ascending or descending?");
                    answer2 = user.next().toLowerCase();
                    switch (answer2) {
                        case "ascending":
                            calendar = sortTitle(calendar, true);
                            break;
                        case "descending":
                            calendar = sortTitle(calendar, false);
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.println("Invalid response.");
                            }
                            break;
                    }
                    break;
                case "help":
                    System.out.println("Commands: date, group, title, help");
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Invalid response. Command does not exist.");
                    break;
            }
        }
    }

    // FUNCTIONS TO USE IN OTHER METHODS

    // Given the index of an event on the calendar, prompts the user to edit that event.
    public static void eventEdit(int index) {
        System.out.print("(unfinished)\n");
    }

    // Sorts the calendar (ArrayList<Event>) by date, then by time, ascending (true) or descending (false)
    public static ArrayList<Event> sortDate(ArrayList<Event> list, boolean ascending) {
        System.out.print("(unfinished)\n");
        return list;
    }

    // Sorts the calendar (ArrayList<Event>) by group, ascending (true) or descending (false)
    public static ArrayList<Event> sortGroup(ArrayList<Event> list, boolean ascending) {
        System.out.print("(unfinished)\n");
        return list;
    }

    // Sorts the calendar (ArrayList<Event>) by title in alphabetical order, ascending (true) or descending (false)
    public static ArrayList<Event> sortTitle(ArrayList<Event> list, boolean ascending) {
        System.out.print("(unfinished)\n");
        return list;
    }

    // Prints the calendar into "calendar.txt"
    public static void printCalendar(ArrayList<Event> list) {
        FileOutputStream myFile = null;
        try {
            myFile = new FileOutputStream("calendar.txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("Exception detected. Exiting program: " + e);
            System.exit(1);
        }
        PrintWriter writer = new PrintWriter(myFile);

        for (Event e : list) {
            String month;
            switch (e.getDate()[1]) {
                case 1:
                    month = "January";
                    break;
                case 2:
                    month = "February";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "September";
                    break;
                case 10:
                    month = "October";
                    break;
                case 11:
                    month = "November";
                    break;
                case 12:
                    month = "December";
                    break;
                default:
                    month = "Invalid Month";
                    break;
            }

            String print = "";
            print += "(" + e.getCategory() + "), " + e.getTitle() + ": "; // Category
            print += e.getDate()[0] + " " + month + ", " + e.getDate()[2]; // Date
            if (e.getTime()[0] != 0 && e.getTime()[1] != 0) { // Time (if exists)
                print += " " + e.getTime()[0] + ":" + e.getTime()[1];
            }
            if (e.getLocation() != null) { // Location (if exists)
                print += ", " + e.getLocation();
            }

            //Prints information from the current event in the for loop
            writer.println(print);
        }

        writer.flush();
        writer.close();
    }
}
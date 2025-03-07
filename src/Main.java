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

        /*
        //for testing purposes
        for (int i=0; i<1000; i++) {
            int randomDay = (int) (Math.random()*30)+1;
            int randomMonth = (int) (Math.random()*12)+1;
            int randomYear = (int) (Math.random()*50)+2000;
            calendar.add(new Event("Gaming Session", new int[]{randomDay,randomMonth,randomYear})); // placeholder for testing print method
        }
        */

        String answer = "";
        while (!answer.equals("stop")) {
            System.out.print("\nPlease input a command: ");
            answer = user.next().toLowerCase();
            String answer2 = "";
            switch (answer) {
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
                    System.out.print("\nPrint as txt or csv?");
                    answer2 = user.next().toLowerCase();
                    if (answer2.equals("txt") || answer2.equals("both")) {
                        printTXT(calendar);
                    }
                    if (answer2.equals("csv") || answer2.equals("both")) {
                        printCSV(calendar);
                    }
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
        System.out.println("Program concluded.");
    }

    // EXTENSIONS OF THE MAIN METHOD

    // What happens when the user uses the command "add".
    public static void add() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("\nWhat would you like to add? ");
            answer = user.next().toLowerCase();
            String answer2 = "";
            switch (answer) {
                case "event":
                    String category = "Uncategorized";
                    int group = 0;
                    String title = "Untitled";
                    int[] date = new int[3];
                    int[] time = new int[2];
                    String description = "";
                    ArrayList<String> location = new ArrayList<>();
                    int[] dateEnd = new int[3];
                    int[] timeEnd = new int[2];

                    //ask for category
                    System.out.print("\nEvent category (skip for none): ");
                    answer2 = user.next();
                    if (!answer2.equalsIgnoreCase("skip"))
                        category = answer2;

                    if (answer2.equals("exit")) break; //exit command

                    //ask for title
                    System.out.print("\nEvent title (skip for none): ");
                    answer2 = user.next();
                    if (!answer2.equalsIgnoreCase("skip"))
                        title = answer2;

                    if (answer2.equals("exit")) break; //exit command

                    //ask for year
                    while (true) {
                        System.out.print("\nEvent year: ");
                        answer2 = user.next();
                        if (answer2.equals("exit")) {
                            break;
                        }
                        else {
                            try {
                                date[2] = Integer.parseInt(answer2);
                                break;
                            }
                            catch (NumberFormatException e) {
                                System.out.print(" Invalid integer. ");
                            }
                        }
                    }

                    if (answer2.equals("exit")) break; //exit command

                    //ask for month
                    date[1] = -1;
                    while (date[1] <= 0 || date[1] > 12) {
                        System.out.print("\nEvent month: ");
                        answer2 = user.next().toLowerCase();
                        if (answer2.equals("exit")) {
                            break;
                        }
                        else {
                            try {
                                date[1] = Integer.parseInt(answer2);
                            }
                            catch (NumberFormatException e) {
                                date[1] = monthToInt(answer2);
                            }
                        }
                    }

                    if (answer2.equals("exit")) break; //exit command

                    //ask for day
                    while (true) {
                        System.out.print("\nEvent day: ");
                        answer2 = user.next();
                        if (answer2.equals("exit")) {
                            break;
                        }
                        else {
                            try {
                                date[0] = Integer.parseInt(answer2);
                                break;
                            }
                            catch (NumberFormatException e) {
                                System.out.print(" Invalid integer. ");
                            }
                        }
                    }

                    if (answer2.equals("exit")) break; //exit command

                    //ask to add optional information
                    boolean optional = false;
                    System.out.print("\nAdd optional information? (yes/no) ");
                    while (true) {
                        answer2 = user.next();
                        if (answer2.equals("yes") || answer2.equals("y")) {
                            optional = true;
                            break;
                        }
                        else if (answer2.equals("no") || answer2.equals("n") || answer2.equals("exit")) break; //exits while loop without changing optional to true
                        else System.out.print(" ");
                    }

                    if (answer2.equals("exit")) break; //exit command

                    //ask to add ending dates/times
                    boolean ends = false;
                    System.out.print("\nAdd ending dates/times? (yes/no) ");
                    while (true) {
                        answer2 = user.next();
                        if (answer2.equals("yes") || answer2.equals("y")) {
                            ends = true;
                            break;
                        }
                        else if (answer2.equals("no") || answer2.equals("n") || answer2.equals("exit")) break; //exits while loop without changing optional to true
                        else System.out.print(" ");
                    }

                    if (answer2.equals("exit")) break; //exit command

                    if (!optional && !ends) {
                        calendar.add(new Event(title, date));
                        System.out.println("Successfully created new event!\n" + calendar.getLast());
                        break;
                    }
                    if (optional) {
                        //ask for hour
                        while (true) {
                            System.out.print("\nEvent hour (24hr): ");
                            answer2 = user.next();
                            if (answer2.equals("exit")) {
                                break;
                            }
                            else {
                                try {
                                    time[0] = Integer.parseInt(answer2);
                                    if (time[0] >= 0 && time[0] < 24) {
                                        break;
                                    }
                                    else System.out.print(" Hour must be between 0 and 23.");
                                }
                                catch (NumberFormatException e) {
                                    System.out.print(" Invalid integer. ");
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command

                        //ask for minute
                        while (true) {
                            System.out.print("\nEvent time (min): ");
                            answer2 = user.next();
                            if (answer2.equals("exit")) {
                                break;
                            }
                            else {
                                try {
                                    time[1] = Integer.parseInt(answer2);
                                    if (time[1] >= 0 && time[1] < 60) {
                                        break;
                                    }
                                    else System.out.print(" Time must be between 0 and 59.");
                                }
                                catch (NumberFormatException e) {
                                    System.out.print(" Invalid integer. ");
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command

                        //ask for description
                        System.out.print("\nEvent description (skip for none): ");
                        answer2 = user.next();
                        if (!answer2.equalsIgnoreCase("skip"))
                            description = answer2;

                        if (answer2.equals("exit")) break; //exit command

                        //ask for locations
                        int count = 1;
                        while (true) {
                            System.out.print("\nEvent location " + count + " (skip to continue): ");
                            answer2 = user.next();
                            if (answer2.equalsIgnoreCase("skip") || answer2.equals("exit"))
                                break;
                            else {
                                location.add(answer2);
                                count++;
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command
                    }
                    if (ends) {
                        //ask for ending year
                        while (true) {
                            System.out.print("\nEvent ending year (skip for same as beginning): ");
                            answer2 = user.next();
                            if (answer2.equalsIgnoreCase("skip") || answer2.equals("exit")) {
                                dateEnd[2] = date[2];
                                break;
                            }
                            else {
                                try {
                                    dateEnd[2] = Integer.parseInt(answer2);
                                    break;
                                }
                                catch (NumberFormatException e) {
                                    System.out.print(" Invalid integer. ");
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command

                        //ask for ending month
                        dateEnd[1] = -1;
                        while (dateEnd[1] <= 0 || dateEnd[1] > 12) {
                            System.out.print("\nEvent ending month (skip for same as beginning): ");
                            answer2 = user.next().toLowerCase();
                            if (answer2.equalsIgnoreCase("skip") || answer2.equals("exit")) {
                                dateEnd[1] = date[1];
                                break;
                            }
                            else {
                                try {
                                    dateEnd[1] = Integer.parseInt(answer2);
                                }
                                catch (NumberFormatException e) {
                                    dateEnd[1] = monthToInt(answer2);
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command

                        //ask for ending day
                        while (true) {
                            System.out.print("\nEvent ending day (skip for same as beginning): ");
                            answer2 = user.next();
                            if (answer2.equalsIgnoreCase("skip") || answer2.equals("exit")) {
                                dateEnd[0] = date[0];
                                break;
                            }
                            else {
                                try {
                                    dateEnd[0] = Integer.parseInt(answer2);
                                    break;
                                }
                                catch (NumberFormatException e) {
                                    System.out.print(" Invalid integer. ");
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command
                    }

                    if (!ends) {
                        calendar.add(new Event(title, date, time, description, location, category, group));
                        System.out.println("Successfully created new event!\n" + calendar.getLast());
                        break;
                    }

                    if (optional && ends) {
                        //ask for ending hour
                        while (true) {
                            System.out.print("\nEvent ending hour (24hr): ");
                            answer2 = user.next();
                            if (answer2.equals("exit")) {
                                break;
                            }
                            else {
                                try {
                                    timeEnd[0] = Integer.parseInt(answer2);
                                    if (timeEnd[0] >= 0 && timeEnd[0] < 24) {
                                        break;
                                    }
                                    else System.out.print(" Hour must be between 0 and 23.");
                                }
                                catch (NumberFormatException e) {
                                    System.out.print(" Invalid integer. ");
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command

                        //ask for ending minute
                        while (true) {
                            System.out.print("\nEvent ending time (min): ");
                            answer2 = user.next();
                            if (answer2.equals("exit")) {
                                break;
                            }
                            else {
                                try {
                                    timeEnd[1] = Integer.parseInt(answer2);
                                    if (timeEnd[1] >= 0 && timeEnd[1] < 60) {
                                        break;
                                    }
                                    else System.out.print(" Time must be between 0 and 59.");
                                }
                                catch (NumberFormatException e) {
                                    System.out.print(" Invalid integer. ");
                                }
                            }
                        }

                        if (answer2.equals("exit")) break; //exit command

                        calendar.add(new Event(title, date, time, description, location, category, group, dateEnd, timeEnd));
                        System.out.println("Successfully created new event!\n" + calendar.getLast());
                    }
                    break;

                case "help":
                    System.out.print( "Commands: event, help");
                    break;

                case "exit":
                    break;

                default:
                    System.out.print(" Invalid response. Command does not exist.");
                    break;

            }
        }
    }

    // What happens when the user uses the command "edit".
    public static void edit() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("\nWhich event would you like to edit? ");
            answer = user.next().toLowerCase();
            int index;
            try {
                index = Integer.parseInt(answer) - 1;
                eventEdit(index);
            }
            catch (NumberFormatException e) {
                if (!answer.equals("exit")) {
                    System.out.print( "Please input an integer value.");
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.print( "That value is outside the range of events on the calendar.");
            }
        }
    }

    // Prompts the user to select options for sorting the calendar.
    public static void sort() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("\nHow would you like to sort the calendar? ");
            answer = user.next().toLowerCase();
            String answer2;
            switch (answer) {
                case "date":
                    System.out.print("\nAscending or descending? ");
                    answer2 = user.next().toLowerCase();
                    switch (answer2) {
                        case "ascending", "asc", "a", "+":
                            CalendarSort.date(calendar, true);
                            break;
                        case "descending", "desc", "d", "-":
                            CalendarSort.date(calendar, false);
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.print(" Invalid response.");
                            }
                            break;
                    }
                    break;
                case "group":
                    System.out.print("\nAscending or descending?");
                    answer2 = user.next().toLowerCase();
                    switch (answer2) {
                        case "ascending", "asc", "a", "+":
                            CalendarSort.group(calendar, true);
                            break;
                        case "descending", "desc", "d", "-":
                            CalendarSort.group(calendar, false);
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.print(" Invalid response.");
                            }
                            break;
                    }
                    break;
                case "title":
                    System.out.print("\nAscending or descending?");
                    answer2 = user.next().toLowerCase();
                    switch (answer2) {
                        case "ascending", "asc", "a", "+":
                            CalendarSort.title(calendar, true);
                            break;
                        case "descending", "desc", "d", "-":
                            CalendarSort.title(calendar, false);
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.print(" Invalid response.");
                            }
                            break;
                    }
                    break;
                case "help":
                    System.out.print("\nCommands: date, group, title, help");
                    break;
                case "exit":
                    break;
                default:
                    System.out.print("\nInvalid response. Command does not exist.");
                    break;
            }
        }
    }

    // Prints the calendar into "calendar.txt"
    public static void printTXT(ArrayList<Event> list) {
        FileOutputStream myFile;
        try {
            myFile = new FileOutputStream("calendar.txt");
        }
        catch (FileNotFoundException e) {
            System.out.print(" Exception detected. Print cancelled: " + e);
            return;
        }
        PrintWriter writer = new PrintWriter(myFile);

        // Enhanced switch generated by Intellij
        for (Event event : list) {
            //Prints information from the current event in the for loop
            writer.println(event.toString());
        }

        writer.flush();
        writer.close();
    }

    // Prints the calendar into "calendar.txt"
    public static void printCSV(ArrayList<Event> list) {
        FileOutputStream myFile;
        try {
            myFile = new FileOutputStream("calendar.csv");
        }
        catch (FileNotFoundException e) {
            System.out.print(" Exception detected. Print cancelled: " + e);
            return;
        }
        PrintWriter writer = new PrintWriter(myFile);

        writer.println("Category, Title, Date, Ending Date, Time, Ending Time, Locations");

        // Enhanced switch generated by Intellij
        for (Event event : list) {
            String month = intToMonth(event.getDate()[1]);
            String endMonth = Main.intToMonth(event.getDateEnd()[1]);

            String print = "";
            print += "(" + event.getCategory() + ")"; // Category
            print += ", " + event.getTitle(); // Title
            print += ", " + month + " " + event.getDate()[0] + " " + event.getDate()[2]; // Date
            print += ", " + endMonth + " " + event.getDateEnd()[0] + " " + event.getDateEnd()[2]; // Ending Date
            print += ", " + event.getTime()[0] + ":" + event.getTime()[1]; // Time
            print += ", " + event.getTimeEnd()[0] + ":" + event.getTimeEnd()[1]; // Ending Time
            for (String l : event.getLocation()) { // Location
                print += ", " + l;
            }

            //Prints information from the current event in the for loop
            writer.println(print);
        }

        writer.flush();
        writer.close();
    }
    // ==================================
    // FUNCTIONS TO USE IN OTHER METHODS
    // ==================================

    // Given the index of an event on the calendar, prompts the user to edit that event.
    public static void eventEdit(int index) {
        System.out.print(" (unfinished)");
    }

    public static int monthToInt(String m) {
        //enhanced switch generated by Intellij
        return switch (m) {
            case "january", "jan" -> 1;
            case "february", "feb" -> 2;
            case "march", "mar" -> 3;
            case "april", "apr" -> 4;
            case "may" -> 5;
            case "june", "jun" -> 6;
            case "july", "jul" -> 7;
            case "august", "aug" -> 8;
            case "september", "sep", "sept" -> 9;
            case "october", "oct" -> 10;
            case "november", "nov" -> 11;
            case "december", "dec" -> 12;
            default -> -1;
        };
    }

    public static String intToMonth(int n) {
        //enhanced switch generated by Intellij
        return switch (n) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Invalid Month";
        };
    }
}
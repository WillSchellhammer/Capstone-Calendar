import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner user = new Scanner(System.in);

    public static ArrayList<Event> calendar;
    public static Queue saves = new Queue();

    public static void main(String[] args) {
        System.out.println("\nCapstone Calendar: Preview Version 3 \nWarning! Do not exit with the stop button if you created a new save file (it will desync).\nPlease type \"help\" if you're not sure what to type.");
        //loads save file names onto saves Stack
        FileInputStream myFile = null;
        try {
            myFile = new FileInputStream("]save_files[.txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("Please create a file named \"]save_files[.txt\" in the Capstone Calendar folder. " + e);
            System.exit(1);
        }
        Scanner reader = new Scanner(myFile);
        while (reader.hasNextLine()) {
            saves.add(reader.nextLine());
        }

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
                        System.out.print("\nPrint as txt, csv, or command line? ");
                        answer2 = user.next().toLowerCase();
                        switch (answer2) {
                            case "txt":
                                printTXT();
                                break;
                            case "csv":
                                printCSV();
                                break;
                            case "cmd", "command", "command line":
                                for (Event event : calendar) {
                                    System.out.println(event);
                                }
                                break;
                            case "help":
                                System.out.println("Commands: txt, csv, cmd, help, exit");
                                break;
                            default:
                                System.out.println("Invalid response. Command does not exist.");
                                break;
                        }
                    break;
                case "save":
                    save();
                    break;
                case "load":
                    load();
                    break;
                case "help":
                    System.out.println("Commands: add, edit, sort, print, save, load, help, stop");
                    break;
                case "stop":
                    break;
                default:
                    System.out.println("Invalid response. Command does not exist.");
                    break;
            }
        }
        System.out.println("Saving file (type exit to skip saving)");
        save();
        FileOutputStream savesFile = null;
        try {
            savesFile = new FileOutputStream("]save_files[.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Please create a file named \"]save_files[.txt\" in the Capstone Calendar folder. " + e);
        }
        PrintWriter writer = new PrintWriter(savesFile);
        while (!saves.isEmpty()) {
            writer.println(saves.remove());
        }
        writer.flush();
        writer.close();
        System.out.println("Program concluded.");
    }

    // EXTENSIONS OF THE MAIN METHOD

    // What happens when the user uses the command "add".
    public static void add() {
        String answer = "";
        String category = "Uncategorized";
        int group = 0;
        String title = "Untitled";
        int[] date = new int[]{-1,-1,-1};
        int[] time = new int[]{-1,-1};
        String description = "";
        ArrayList<String> location = new ArrayList<>();
        int[] dateEnd = new int[]{-1,-1,-1};
        int[] timeEnd = new int[]{-1,-1};

        //ask for category
        System.out.print("\nEvent category (skip for none): ");
        answer = user.next();
        if (!answer.equalsIgnoreCase("skip"))
            category = answer;

        if (answer.equals("exit")) return; //exit command

        //ask for title
        System.out.print("Event title (skip for none): ");
        answer = user.next();
        if (!answer.equalsIgnoreCase("skip"))
            title = answer;

        if (answer.equals("exit")) return; //exit command

        //ask for year
        while (true) {
            System.out.print("Event year: ");
            answer = user.next();
            if (answer.equals("exit")) {
                break;
            } else {
                try {
                    date[2] = Integer.parseInt(answer);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print(" Invalid integer. ");
                }
            }
        }

        if (answer.equals("exit")) return; //exit command

        //ask for month
        date[1] = -1;
        while (date[1] <= 0 || date[1] > 12) {
            System.out.print("Event month: ");
            answer = user.next().toLowerCase();
            if (answer.equals("exit")) {
                break;
            } else {
                try {
                    date[1] = Integer.parseInt(answer);
                } catch (NumberFormatException e) {
                    date[1] = monthToInt(answer);
                }
            }
        }

        if (answer.equals("exit")) return; //exit command

        //ask for day
        while (true) {
            System.out.print("Event day: ");
            answer = user.next();
            if (answer.equals("exit")) {
                break;
            } else {
                try {
                    date[0] = Integer.parseInt(answer);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print(" Invalid integer. ");
                }
            }
        }

        if (answer.equals("exit")) return; //exit command

        //ask to add optional information
        boolean optional = false;
        System.out.print("Add optional information? That includes time, description, and locations. (yes/no) ");
        while (true) {
            answer = user.next();
            if (answer.equals("yes") || answer.equals("y")) {
                optional = true;
                break;
            } else if (answer.equals("no") || answer.equals("n") || answer.equals("exit"))
                break; //exits while loop without changing optional to true
            else System.out.print(" ");
        }

        if (answer.equals("exit")) return; //exit command

        //ask to add ending dates/times
        boolean ends = false;
        System.out.print("Add ending dates/times? (yes/no) ");
        while (true) {
            answer = user.next();
            if (answer.equals("yes") || answer.equals("y")) {
                ends = true;
                break;
            } else if (answer.equals("no") || answer.equals("n") || answer.equals("exit"))
                break; //exits while loop without changing optional to true
            else System.out.print(" ");
        }

        if (answer.equals("exit")) return; //exit command

        if (!optional && !ends) {
            calendar.add(new Event(category, title, date));
            System.out.println("Successfully created new event!\n" + calendar.getLast());
            return;
        }
        if (optional) {
            //ask for hour
            while (true) {
                System.out.print("Event hour (24hr): ");
                answer = user.next();
                if (answer.equals("exit") || answer.equals("skip")) {
                    break;
                } else {
                    try {
                        time[0] = Integer.parseInt(answer);
                        if (time[0] >= 0 && time[0] < 24) {
                            break;
                        } else System.out.print(" Hour must be between 0 and 23.");
                    } catch (NumberFormatException e) {
                        System.out.print(" Invalid integer. ");
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command

            //ask for minute
            while (true) {
                System.out.print("Event time (min): ");
                answer = user.next();
                if (answer.equals("exit") || answer.equals("skip")) {
                    break;
                } else {
                    try {
                        time[1] = Integer.parseInt(answer);
                        if (time[1] >= 0 && time[1] < 60) {
                            break;
                        } else System.out.print(" Time must be between 0 and 59.");
                    } catch (NumberFormatException e) {
                        System.out.print(" Invalid integer. ");
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command

            //ask for description
            System.out.print("Event description (skip for none): ");
            answer = user.next();
            if (!answer.equalsIgnoreCase("skip"))
                description = answer;

            if (answer.equals("exit")) return; //exit command

            //ask for locations
            int count = 1;
            while (true) {
                System.out.print("Event location " + count + " (skip to continue): ");
                answer = user.next();
                if (answer.equalsIgnoreCase("skip") || answer.equals("exit"))
                    break;
                else {
                    location.add(answer);
                    count++;
                }
            }

            if (answer.equals("exit")) return; //exit command
        }
        if (ends) {
            //ask for ending year
            while (true) {
                System.out.print("Event ending year (skip for same as beginning): ");
                answer = user.next();
                if (answer.equalsIgnoreCase("skip") || answer.equals("exit")) {
                    dateEnd[2] = date[2];
                    break;
                } else {
                    try {
                        dateEnd[2] = Integer.parseInt(answer);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.print(" Invalid integer. ");
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command

            //ask for ending month
            dateEnd[1] = -1;
            while (dateEnd[1] <= 0 || dateEnd[1] > 12) {
                System.out.print("Event ending month (skip for same as beginning): ");
                answer = user.next().toLowerCase();
                if (answer.equalsIgnoreCase("skip") || answer.equals("exit")) {
                    dateEnd[1] = date[1];
                    break;
                } else {
                    try {
                        dateEnd[1] = Integer.parseInt(answer);
                    } catch (NumberFormatException e) {
                        dateEnd[1] = monthToInt(answer);
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command

            //ask for ending day
            while (true) {
                System.out.print("Event ending day (skip for same as beginning): ");
                answer = user.next();
                if (answer.equalsIgnoreCase("skip") || answer.equals("exit")) {
                    dateEnd[0] = date[0];
                    break;
                } else {
                    try {
                        dateEnd[0] = Integer.parseInt(answer);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.print(" Invalid integer. ");
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command
        }

        if (!ends) {
            calendar.add(new Event(title, date, time, description, location, category, group));
            System.out.println("Successfully created new event!\n" + calendar.getLast());
            return;
        }

        if (optional && ends) {
            //ask for ending hour
            while (true) {
                System.out.print("Event ending hour (24hr): ");
                answer = user.next();
                if (answer.equals("exit") || answer.equals("skip")) {
                    break;
                } else {
                    try {
                        timeEnd[0] = Integer.parseInt(answer);
                        if (timeEnd[0] >= 0 && timeEnd[0] < 24) {
                            break;
                        } else System.out.print(" Hour must be between 0 and 23. ");
                    } catch (NumberFormatException e) {
                        System.out.print(" Invalid integer. ");
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command

            //ask for ending minute
            while (true) {
                System.out.print("Event ending time (min): ");
                answer = user.next();
                if (answer.equals("exit") || answer.equals("skip")) {
                    break;
                } else {
                    try {
                        timeEnd[1] = Integer.parseInt(answer);
                        if (timeEnd[1] >= 0 && timeEnd[1] < 60) {
                            break;
                        } else System.out.print(" Time must be between 0 and 59. ");
                    } catch (NumberFormatException e) {
                        System.out.print(" Invalid integer. ");
                    }
                }
            }

            if (answer.equals("exit")) return; //exit command
        }
        calendar.add(new Event(title, date, time, description, location, category, group, dateEnd, timeEnd));
        System.out.println("Successfully created new event!\n" + calendar.getLast());
    }

    // What happens when the user uses the command "edit".
    public static void edit() {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("\nWhich event would you like to edit? Please input an integer of the index in the list. ");
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
        String answer = "help";
        while (answer.equals("help")) {
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
                    System.out.print("\nAscending or descending? ");
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
                    System.out.print("\nAscending or descending? ");
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
    public static void printTXT() {
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
        for (Event event : calendar) {
            //Prints information from the current event in the for loop
            writer.println(event.toString());
        }

        writer.flush();
        writer.close();
    }

    // Prints the calendar into "calendar.txt"
    public static void printCSV() {
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
        for (Event event : calendar) {
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

    // Stores calendar data in a txt file of the user's choosing with the data delimited by /.
    public static void save() {
        System.out.print("Save files loaded:\n      ");
        saves.print();
        System.out.print("Please enter file name: ");
        String answer = user.next().toLowerCase();
        if (answer.equals("exit")) {
            return;
        }

        FileOutputStream myFile = null;
        try {
            myFile = new FileOutputStream("saves/" + answer + ".txt");
        }
        catch (FileNotFoundException e) {
            System.out.print(" Exception detected. Save failed: " + e);
            return;
        }
        PrintWriter writer = new PrintWriter(myFile);

        // Enhanced switch generated by Intellij
        for (Event event : calendar) {
            String print = ""; // String Delimiter: /.
            print += event.getTitle() + "/."; // Title
            print += event.getDate()[0] + "/." + event.getDate()[1] + "/." + event.getDate()[2] + "/."; // Date
            print += event.getTime()[0] + "/." + event.getTime()[1] + "/."; // Time
            print += event.getDescription() + "/."; // Description
            for (String l : event.getLocation()) { // Location
                print += l + ":, "; // Second delimiter: :,
            }
            print += "/.";
            print += event.getCategory() + "/."; // Category
            print += event.getGroup() + "/."; // Group
            print += event.getDateEnd()[0] + "/." + event.getDateEnd()[1] + "/." + event.getDateEnd()[2] + "/."; // Ending Date
            print += event.getTimeEnd()[0] + "/." + event.getTimeEnd()[1] + "/."; // Ending Time

            writer.println(print); //Prints information from the current event in the for loop
        }

        writer.flush();
        writer.close();

        // Adds the name of the file to the list of file names if it doesn't exist already
        boolean hasFile = false;
        Queue temp = new Queue();
        while (!saves.isEmpty()) {
            String s = saves.remove();
            hasFile = s.equals(answer);
            temp.add(s);
        }
        saves = temp;
        if (!hasFile) {
            saves.add(answer);
        }

        System.out.println("Save complete.");
    }

    public static void load() {
        Queue temp = new Queue();
        String file = null;
        System.out.print("Which file do you want to load?\n      ");
        saves.print();
        while (file == null) {
            String answer = user.next();
            while (!saves.isEmpty()){
                String s = saves.remove();
                if (s.equals(answer)) {
                    file = answer;
                }
                temp.add(s);
            }
            if (answer.equals("exit")) {
                saves = temp;
                return;
            }
            else if (file == null) {
                System.out.println("Please input a valid file name.");
                saves = temp;
                temp = new Queue();
            }
        }
        saves = temp;

        FileInputStream myFile = null;
        try {
            myFile = new FileInputStream("saves/" + file + ".txt");
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Mismatch between ]saves_files[ and file names in saves folder. " + e);
        }
        Scanner reader = new Scanner(myFile);
        calendar = new ArrayList<>();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split("/.");
            calendar.add(new Event(
                    line[0], // Title
                    new int[]{Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])}, // Date: dd,mm,yy
                    new int[]{Integer.parseInt(line[4]), Integer.parseInt(line[5])}, //Time: mm,hh
                    line[6], // Description
                    new ArrayList<String>(List.of(line[7].split(":,"))),
                    line[8],
                    Integer.parseInt(line[9]),
                    new int[]{Integer.parseInt(line[10]), Integer.parseInt(line[11]), Integer.parseInt(line[12])},
                    new int[]{Integer.parseInt(line[13]), Integer.parseInt(line[14])}
                    )); // Adds the elements of the line from the save to the calendar. This is an absolute mess to read.
            calendar.getLast().getLocation().removeLast();
        }
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
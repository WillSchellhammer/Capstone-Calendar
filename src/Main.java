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
        System.out.println("\nCapstone Calendar: Version 4 \nWarning! Do not exit with the stop button if you created a new save file (it will desync).\nPlease type \"help\" if you're not sure what to type.");
        //loads save file names onto saves Stack
        FileInputStream myFile = null;
        try {
            myFile = new FileInputStream("]save_files[.txt");
        } catch (FileNotFoundException e) {
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
            user.nextLine(); // Clear input buffer
            String answer2;
            switch (answer) {
                case "add":
                    add();
                    break;
                case "edit":
                    edit();
                    break;
                case "remove":
                    remove();
                    break;
                case "sort":
                    sort();
                    break;
                case "print":
                    System.out.print("\nPrint as txt, csv, or command line? ");
                    answer2 = user.next().toLowerCase();
                    user.nextLine(); // Clear input buffer
                    switch (answer2) {
                        case "txt":
                            printTXT();
                            break;
                        case "csv":
                            printCSV();
                            break;
                        case "cmd", "command", "command line":
                            printCMD();
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
                    System.out.println("Commands: add, edit, remove, sort, print, save, load, help, stop");
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
        String answer;
        String category = "Uncategorized";
        String title = "Untitled";
        int[] date = new int[]{-1, -1, -1};
        int[] time = new int[]{-1, -1};
        String description = "";
        ArrayList<String> location = new ArrayList<>();
        int[] dateEnd = new int[]{-1, -1, -1};
        int[] timeEnd = new int[]{-1, -1};

        //ask for category
        System.out.print("\nEvent category (skip for none): ");
        answer = user.nextLine();
        if (!answer.isBlank()) category = answer;

        if (answer.equals("exit")) return; //exit command

        //ask for title
        System.out.print("Event title (skip for none): ");
        answer = user.nextLine();
        if (!answer.isBlank()) title = answer;

        if (answer.equals("exit")) return; //exit command

        //ask for date
        while (true) {
            System.out.print("Event date (MM/DD/YYYY): ");
            answer = user.nextLine();
            if (answer.equals("exit") || answer.isBlank()) {
                break;
            }
            String[] dateString = answer.split("/");
            try {
                date[0] = Integer.parseInt(dateString[1]); //Day
                date[1] = Integer.parseInt(dateString[0]); //Month
                date[2] = Integer.parseInt(dateString[2]); //Year
                if (!dateCheck(date[0], date[1], date[2])) throw new IndexOutOfBoundsException();
                break;
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(" Invalid date.");
            }
            /* Legacy code
            else {
                try {
                    date[2] = Integer.parseInt(answer);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print(" Invalid integer. ");
                }
            }*/
        }

        if (answer.equals("exit")) return; //exit command

        /* Legacy codee
        //ask for month
        date[1] = -1;
        while (date[1] <= 0 || date[1] > 12) {
            System.out.print("Event month: ");
            answer = user.next().toLowerCase();
            user.nextLine(); // Clear input buffer
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
            user.nextLine(); // Clear input buffer
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
        */

        //ask to add optional information
        boolean optional = false;
        System.out.print("Add optional information? That includes time, description, and locations. (yes/no) ");
        while (true) {
            answer = user.next();
            user.nextLine(); // Clear input buffer
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
            user.nextLine(); // Clear input buffer
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
            //ask for time
            while (true) {
                System.out.print("Event time (HH:MM): ");
                answer = user.nextLine();
                if (answer.equals("exit") || answer.isBlank()) {
                    break;
                }
                String[] timeString = answer.split(":");
                try {
                    time[0] = Integer.parseInt(timeString[1]); //Minute
                    time[1] = Integer.parseInt(timeString[0]); //Hour
                    if (!timeCheck(time)) throw new IndexOutOfBoundsException();
                    break;
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println(" Invalid time.");
                }
            }

            if (answer.equals("exit")) return; //exit command

            /*//ask for hour
            while (true) {
                System.out.print("Event hour (24hr): ");
                answer = user.next();
                user.nextLine(); // Clear input buffer
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
                user.nextLine(); // Clear input buffer
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

            if (answer.equals("exit")) return; //exit command */

            //ask for description
            System.out.print("Event description (skip for none): ");
            answer = user.nextLine();
            description = answer;

            if (answer.equals("exit")) return; //exit command

            //ask for locations
            int count = 1;
            while (true) {
                System.out.print("Event location " + count + " (skip to continue): ");
                answer = user.nextLine();
                if (answer.equalsIgnoreCase("exit") || answer.isBlank())
                    break;
                else {
                    location.add(answer);
                    count++;
                }
            }

            if (answer.equals("exit")) return; //exit command
        }
        if (ends) {
            //ask for ending date
            while (true) {
                System.out.print("Event end date (MM/DD/YYYY): ");
                answer = user.nextLine();
                if (answer.equals("exit") || answer.isBlank()) {
                    break;
                }
                String[] dateString = answer.split("/");
                try {
                    dateEnd[0] = Integer.parseInt(dateString[1]); //End Day
                    dateEnd[1] = Integer.parseInt(dateString[0]); //End Month
                    dateEnd[2] = Integer.parseInt(dateString[2]); //End Year
                    if (!dateCheck(dateEnd[0], dateEnd[1], dateEnd[2])) throw new IndexOutOfBoundsException();
                    break;
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println(" Invalid date.");
                }
            }

            /*
            //ask for ending year
            while (true) {
                System.out.print("Event ending year (skip for same as beginning): ");
                answer = user.next();
                user.nextLine(); // Clear input buffer
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
                user.nextLine(); // Clear input buffer
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
                user.nextLine(); // Clear input buffer
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
            }*/

            if (answer.equals("exit")) return; //exit command
        }

        if (!ends) {
            calendar.add(new Event(title, date, time, description, location, category));
            System.out.println("Successfully created new event!\n" + calendar.getLast());
            return;
        }

        if (optional && ends) {
            //ask for ending time
            while (true) {
                System.out.print("Event ending time (HH:MM): ");
                answer = user.nextLine();
                if (answer.equals("exit") || answer.isBlank()) {
                    break;
                }
                String[] timeEndString = answer.split(":");
                try {
                    timeEnd[0] = Integer.parseInt(timeEndString[1]); //Minute
                    timeEnd[1] = Integer.parseInt(timeEndString[0]); //Hour
                    if (!timeCheck(timeEnd)) throw new IndexOutOfBoundsException();
                    break;
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println(" Invalid time.");
                }
            }

            if (answer.equals("exit")) return; //exit command

            /*//ask for ending hour
            while (true) {
                System.out.print("Event ending hour (24hr): ");
                answer = user.next();
                user.nextLine(); // Clear input buffer
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
                user.nextLine(); // Clear input buffer
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

            if (answer.equals("exit")) return; //exit command*/
        }
        calendar.add(new Event(title, date, time, description, location, category, dateEnd, timeEnd));
        System.out.println("Successfully created new event!\n" + calendar.getLast());
    }

    // What happens when the user uses the command "edit".
    public static void edit() {
        String answer = "";
        while (!answer.equals("exit")) {
            printCMD();
            System.out.print("\nWhich event would you like to edit? Please enter the title. ");
            CalendarSearch search = new CalendarSearch();
            boolean repeat = true;
            int index = -1;
            while (repeat) {
                answer = user.nextLine();
                index = search.searchIndex(calendar, answer, 1);
                if (answer.equalsIgnoreCase("exit")) {
                    return;
                } else if (index >= 0) { //Search for title
                    repeat = false;
                } else {
                    System.out.println(" Invalid title.");
                }
            }
            eventEdit(index);
        }
    }

    // What happens when the user uses the command "remove".
    public static void remove() {
        String answer = "";
        while (!answer.equals("exit")) {
            printCMD();
            System.out.print("\nWhich event would you like to remove? Please enter the title. ");
            CalendarSearch search = new CalendarSearch();
            boolean repeat = true;
            int index = -1;
            while (repeat) {
                answer = user.nextLine();
                index = search.searchIndex(calendar, answer, 1);
                if (answer.equalsIgnoreCase("exit")) {
                    return;
                } else if (index >= 0) { //Search for title
                    repeat = false;
                } else {
                    System.out.println(" Invalid title.");
                }
            }
            calendar.remove(index);
            System.out.println("Successfully removed event!\n");
        }
    }

    // Given the index of an event on the calendar, prompts the user to edit that event.
    public static void eventEdit(int index) {
        String answer = "";
        while (!answer.equals("exit")) {
            System.out.print("\nWhich aspect would you like to edit? ");
            answer = user.next().toLowerCase();
            user.nextLine(); // Clear input buffer
            String answer2;
            String answer3;
            switch (answer) {

                case "title":
                    System.out.println(calendar.get(index).getTitle());
                    System.out.print("\nEnter new title. ");
                    answer2 = user.nextLine();
                    if (answer2.equals("exit")) break;
                    if (answer2.isBlank()) {
                        calendar.get(index).setTitle("Untitled");
                    }
                    else {
                        calendar.get(index).setTitle(answer2);
                    }
                    System.out.println("Edit successful!");
                    break;

                case "date":
                    int[] date = calendar.get(index).getDate();
                    int[] dateEnd = calendar.get(index).getDateEnd();
                    if (date[0] == -1) {
                        System.out.println("No date is currently saved.");
                    }
                    else if (dateEnd[0] == -1) {
                        System.out.println(intToMonth(date[1]) + " " + date[0] + ", " + date[2]);
                    }
                    else {
                        System.out.println(intToMonth(date[1]) + " " + date[0] + ", " + date[2] + " - " + intToMonth(dateEnd[1]) + dateEnd[0] + ", " + dateEnd[2]);
                    }

                    while (true) {
                        date = new int[3];
                        dateEnd = new int[3];
                        System.out.print("\nStart or end date? ");
                        answer2 = user.next().toLowerCase();
                        user.nextLine(); // Clear input buffer
                        if (answer2.equals("exit")) break;
                        switch (answer2) {

                            case "start", "s":
                                while (true) {
                                    System.out.print("\nNew start date (MM/DD/YYYY): ");
                                    answer3 = user.next();
                                    user.nextLine(); // Clear input buffer
                                    if (answer3.equals("exit")) break;
                                    //if (answer3.contains("/")) {
                                    String[] dateString = answer3.split("/");
                                    try {
                                        date[0] = Integer.parseInt(dateString[1]); //Day
                                        date[1] = Integer.parseInt(dateString[0]); //Month
                                        date[2] = Integer.parseInt(dateString[2]); //Year
                                        if (!dateCheck(date[0], date[1], date[2]))
                                            throw new IndexOutOfBoundsException();
                                        calendar.get(index).setDate(date);
                                        System.out.println("Edit successful!");
                                        break;
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println(" Invalid date.");
                                    }
                                    //}
                            /*else { //legacy code
                                try {
                                    date[2] = Integer.parseInt(answer3);
                                    System.out.print("\nEnter a month. ");
                                    answer3 = user.next().toLowerCase();
                                    if (monthToInt(answer3) != -1) {
                                        date[1] = monthToInt(answer3);
                                    }
                                    else {
                                        date[1] = Integer.parseInt(answer3);
                                    }
                                    System.out.print("\nEnter a day. ");
                                    answer3 = user.next();
                                    date[0] = Integer.parseInt(answer3);
                                    calendar.get(index).setDate(date);
                                }
                                catch (NumberFormatException e) {
                                    if (!answer3.equals("exit")) System.out.println(" Invalid date.");
                                }
                            }*/
                                }
                                break;

                            case "end", "e":
                                while (true) {
                                    System.out.print("\nNew end date (MM/DD/YYYY): ");
                                    answer3 = user.next();
                                    user.nextLine(); // Clear input buffer
                                    if (answer3.equals("exit")) break;
                                    //if (answer3.contains("/")) {
                                    String[] dateEndString = answer3.split("/");
                                    try {
                                        dateEnd[0] = Integer.parseInt(dateEndString[1]); //Day
                                        dateEnd[1] = Integer.parseInt(dateEndString[0]); //Month
                                        dateEnd[2] = Integer.parseInt(dateEndString[2]); //Year
                                        if (!dateCheck(dateEnd[0], dateEnd[1], dateEnd[2]))
                                            throw new IndexOutOfBoundsException();
                                        calendar.get(index).setDateEnd(dateEnd);
                                        System.out.println("Edit successful!");
                                        break;
                                    } catch (IndexOutOfBoundsException e) {
                                        System.out.println(" Invalid date.");
                                    }
                                }
                                break;
                            case "help":
                                System.out.println("\nCommands: start, end, exit, help");
                                break;
                            default:
                                System.out.println(" Invalid command.");
                                break;
                        }
                    }
                    break;

                case "time":
                    int[] time = calendar.get(index).getTime();
                    int[] timeEnd = calendar.get(index).getTimeEnd();

                    String timeDispString = "" + time[0];
                    if (time[0]/10 == 0) {
                        timeDispString = "0" + timeDispString;
                    }
                    String timeEndDispString = "" + timeEnd[0];
                    if (timeEnd[0]/10 == 0) {
                        timeEndDispString = "0" + timeEndDispString;
                    }

                    if (time[0] == -1) {
                        System.out.println("No time is currently saved.");
                    }
                    else if (timeEnd[0] == -1) {
                        System.out.println(time[1] + ":" + timeDispString);
                    }
                    else {
                        System.out.println(time[1] + ":" + timeDispString + " - " + timeEnd[1] + ":" + timeEndDispString);
                    }

                    while (true) {
                        time = new int[2];
                        timeEnd = new int[2];
                        System.out.print("\nStart or end time? ");
                        answer2 = user.next().toLowerCase();
                        user.nextLine(); // Clear input buffer
                        if (answer2.equals("exit")) break;

                        switch (answer2) {

                            case "start", "s":
                                while (true) {
                                    System.out.print("New time (HH:MM): ");
                                    answer3 = user.next();
                                    user.nextLine(); // Clear input buffer
                                    if (answer3.equals("exit")) break;
                                    String[] timeString = answer3.split(":");
                                    try {
                                        time[0] = Integer.parseInt(timeString[1]); //Minute
                                        time[1] = Integer.parseInt(timeString[0]); //Hour
                                        if (!timeCheck(time)) throw new IndexOutOfBoundsException();
                                        calendar.get(index).setTime(time);
                                        System.out.println("Edit successful!");
                                        break;
                                    }
                                    catch (IndexOutOfBoundsException e) {
                                        System.out.println(" Invalid time.");
                                    }
                                }
                                break;

                            case "end", "e":
                                while (true) {
                                    System.out.print("New ending time (HH:MM): ");
                                    answer3 = user.next();
                                    if (answer3.equals("exit")) break;
                                    String[] timeEndString = answer3.split(":");
                                    try {
                                        timeEnd[0] = Integer.parseInt(timeEndString[1]); //Minute
                                        timeEnd[1] = Integer.parseInt(timeEndString[0]); //Hour
                                        if (!timeCheck(timeEnd)) throw new IndexOutOfBoundsException();
                                        calendar.get(index).setTimeEnd(timeEnd);
                                        System.out.println("Edit successful!");
                                        break;
                                    }
                                    catch (IndexOutOfBoundsException e) {
                                        System.out.println(" Invalid date.");
                                    }
                                }
                                break;

                            case "help":
                                System.out.println("\nCommands: start, end, exit, help");
                                break;
                            default:
                                System.out.println(" Invalid command.");
                                break;
                        }
                    }
                    break;

                case "description":
                    if (calendar.get(index).getDescription().isBlank()) {
                        System.out.println("No description currently saved.");
                    }
                    else {
                        System.out.println(calendar.get(index).getDescription());
                    }
                    System.out.print("\nEnter new description. ");
                    answer2 = user.nextLine();
                    if (answer2.equals("exit")) break;
                    calendar.get(index).setDescription(answer2);
                    System.out.println("Edit successful!");
                    break;

                case "location":
                    if (calendar.get(index).getLocation().isEmpty()) {
                        System.out.println("No location currently saved.");
                        break;
                    }
                    System.out.println(calendar.get(index).getLocation());
                    int index2 = -1;
                    while (true) {
                        System.out.print("\nEnter the index of the location to edit. ");
                        answer2 = user.next();
                        user.nextLine(); // Clear input buffer
                        if (answer2.equals("exit")) break;
                        try {
                            index2 = Integer.parseInt(answer2) - 1;
                            if (index2 < 0 || index2 >= calendar.get(index).getLocation().size()) throw new NumberFormatException();
                            break;
                        }
                        catch (NumberFormatException e) {
                            System.out.println(" Invalid index.");
                        }
                    }
                    while (!answer2.equals("exit")) {
                        System.out.print("\nChange or remove location " + calendar.get(index).getLocation().get(index2) + "? ");
                        answer2 = user.next().toLowerCase();
                        user.nextLine(); // Clear input buffer
                        if (answer2.equals("exit")) break;
                        switch (answer2) {
                            case "change", "c":
                                System.out.print("\nEnter new location. ");
                                answer3 = user.nextLine();
                                if (answer3.equals("exit")) break;
                                calendar.get(index).getLocation().set(index2, answer3);
                                System.out.println("Edit successful!");
                                answer2 = "exit";
                                break;
                            case "remove", "r":
                                calendar.get(index).getLocation().remove(index2);
                                System.out.println("Removal successful!");
                                answer2 = "exit";
                                break;
                            default:
                                System.out.println(" Invalid command.");
                                break;
                        }
                    }
                    break;

                case "category":
                    if (calendar.get(index).getCategory().isBlank()) {
                        System.out.println("No category currently saved.");
                    }
                    else {
                        System.out.println(calendar.get(index).getCategory());
                    }
                    System.out.print("\nEnter new category. ");
                    answer2 = user.nextLine();
                    if (answer2.equals("exit")) break;
                    if (answer2.isBlank()) {
                        calendar.get(index).setCategory("Uncategorized");
                    }
                    else {
                        calendar.get(index).setCategory(answer2);
                    }
                    System.out.println("Edit successful!");
                    break;

                case "exit":
                    break;

                case "help":
                    System.out.println("\nCommands: title, date, time, description, location, category, exit, help");
                    break;

                default:
                    System.out.println(" Invalid command.");
                    break;
            }
        }
    }

    // Prompts the user to select options for sorting the calendar.
    public static void sort() {
        printCMD();
        String answer = "help";
        while (answer.equals("help")) {
            System.out.print("\nHow would you like to sort the calendar? ");
            answer = user.next().toLowerCase();
            user.nextLine(); // Clear input buffer
            String answer2;
            switch (answer) {
                case "date":
                    System.out.print("\nAscending or descending? ");
                    answer2 = user.next().toLowerCase();
                    user.nextLine(); // Clear input buffer
                    switch (answer2) {
                        case "ascending", "asc", "a", "+":
                            CalendarSort.date(calendar, new ArrayList<Event>(), 0, calendar.size() - 1, true);
                            printCMD();
                            break;
                        case "descending", "desc", "d", "-":
                            CalendarSort.date(calendar, new ArrayList<Event>(), 0, calendar.size() - 1, false);
                            printCMD();
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.print(" Invalid response.");
                            }
                            break;
                    }
                    break;
                case "category":
                    System.out.print("\nAscending or descending? ");
                    answer2 = user.next().toLowerCase();
                    user.nextLine(); // Clear input buffer
                    switch (answer2) {
                        case "ascending", "asc", "a", "+":
                            CalendarSort.category(calendar, new ArrayList<Event>(), 0, calendar.size() - 1, true);
                            printCMD();
                            break;
                        case "descending", "desc", "d", "-":
                            CalendarSort.category(calendar, new ArrayList<Event>(), 0, calendar.size() - 1, false);
                            printCMD();
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
                    user.nextLine(); // Clear input buffer
                    switch (answer2) {
                        case "ascending", "asc", "a", "+":
                            CalendarSort.title(calendar, new ArrayList<Event>(), 0, calendar.size() - 1, true);
                            printCMD();
                            break;
                        case "descending", "desc", "d", "-":
                            CalendarSort.title(calendar, new ArrayList<Event>(), 0, calendar.size() - 1, true);
                            printCMD();
                            break;
                        default:
                            if (!answer2.equals("exit")) {
                                System.out.print(" Invalid response.");
                            }
                            break;
                    }
                    break;
                case "help":
                    System.out.println("Commands: date, category, title, help");
                    break;
                case "exit":
                    break;
                default:
                    System.out.print(" Invalid response. Command does not exist. ");
                    break;
            }
        }
    }

    // Prints the calendar into "calendar.txt"
    public static void printTXT() {
        FileOutputStream myFile;
        try {
            myFile = new FileOutputStream("calendar.txt");
        } catch (FileNotFoundException e) {
            System.out.print(" Exception detected. Print cancelled: " + e);
            return;
        }
        PrintWriter writer = new PrintWriter(myFile);

        // Enhanced switch generated by Intellij
        for (Event e : calendar) {
            //Prints information from the current event in the for loop
            writer.println(e);
        }

        writer.flush();
        writer.close();
    }

    // Prints the calendar into "calendar.txt"
    public static void printCSV() {
        FileOutputStream myFile;
        try {
            myFile = new FileOutputStream("calendar.csv");
        } catch (FileNotFoundException e) {
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

    //Prints the calendar into the command line
    public static void printCMD() {
        for (Event e : calendar) {
            System.out.println(e);
        }
    }

    // Stores calendar data in a txt file of the user's choosing with the data delimited by /.
    public static void save() {
        System.out.print("Save files loaded:\n      ");
        saves.print();
        System.out.print("Please enter file name: ");
        String answer = user.next().toLowerCase();
        user.nextLine(); // Clear input buffer
        if (answer.equals("exit")) {
            return;
        }

        FileOutputStream myFile;
        try {
            myFile = new FileOutputStream("saves/" + answer + ".txt");
        } catch (FileNotFoundException e) {
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
                print += l + ":,"; // Second delimiter: :,
            }
            print += "/.";
            print += event.getCategory() + "/."; // Category
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
            user.nextLine(); // Clear input buffer
            while (!saves.isEmpty()) {
                String s = saves.remove();
                if (s.equals(answer)) {
                    file = answer;
                }
                temp.add(s);
            }
            if (answer.equals("exit")) {
                saves = temp;
                return;
            } else if (file == null) {
                System.out.println("Please input a valid file name.");
                saves = temp;
                temp = new Queue();
            }
        }
        saves = temp;

        FileInputStream myFile = null;
        try {
            myFile = new FileInputStream("saves/" + file + ".txt");
        } catch (FileNotFoundException e) {
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
                    new ArrayList<String>(List.of(line[7].split(":,",-1))), //location
                    line[8], //category
                    new int[]{Integer.parseInt(line[9]), Integer.parseInt(line[10]), Integer.parseInt(line[11])},
                    new int[]{Integer.parseInt(line[12]), Integer.parseInt(line[13])}
            )); // Adds the elements of the line from the save to the calendar. This is an absolute mess to read.
            calendar.getLast().getLocation().removeLast();
        }
        printCMD();
    }

    // ==================================
    // FUNCTIONS TO USE IN OTHER METHODS
    // ==================================

    //Converts a String month name into an integer 1-12 corresponding to the month number
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

    //Converts an integer 1-12 into the String representing the month name
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

    //Returns true if the date is a valid date in the calendar, or if all values are -1
    public static boolean dateCheck(int day, int month, int year) {
        if (day == -1 && month == -1 && year == -1) return true;
        if (day < 1 || day > 31 || month < 1 || month > 12) return false;
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12: //31 days
                return true; //no if statement necessary because this section wouldn't run if day wasn't <= 31
            case 4, 6, 9, 11: //30 days
                if (day <= 30) return true;
                break;
            case 2: //28 or 29 days
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) { //check for leap year
                    if (day <= 29) return true;
                } else {
                    if (day <= 28) return true;
                }
                break;
        }
        return false;
    }

    //Returns true if the time is a valid time of day (24 hour clock), or if both values are -1
    public static boolean timeCheck(int[] time) {
        return (time[0] == -1 && time[1] == -1) || (time[0] >= 0 && time[0] < 60 && time[1] >= 0 && time[1] < 24);
    }
}
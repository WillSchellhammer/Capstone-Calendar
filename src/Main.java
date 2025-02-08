import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public Scanner input;

    public static ArrayList<Event> calendar;

    public static void main(String[] args) {
        calendar = new ArrayList<>();

        for (int i=0; i<1000; i++) {
            int randomDay = (int) (Math.random()*31);
            int randomMonth = (int) (Math.random()*12 + 1);
            int randomYear = (int) (Math.random()*2030);
            calendar.add(new Event("Gaming Session", new int[]{randomDay,randomMonth,randomYear})); // placeholder for testing print method
        }
        printCalendar(calendar);
    }

    // Sorts the calendar (ArrayList<Event>) by date, then by time, ascending (true) or descending (false)
    public static ArrayList<Event> sortDate(ArrayList<Event> list, boolean ascending) {
        return list;
    }

    // Sorts the calendar (ArrayList<Event>) by group, ascending (true) or descending (false)
    public static ArrayList<Event> sortGroup(ArrayList<Event> list, boolean ascending) {
        return list;
    }

    // Sorts the calendar (ArrayList<Event>) by title in alphabetical order, ascending (true) or descending (false)
    public static ArrayList<Event> sortTitle(ArrayList<Event> list, boolean ascending) {
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
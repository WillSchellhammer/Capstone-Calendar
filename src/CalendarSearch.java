import java.util.ArrayList;

public class CalendarSearch {

    //Find the first data equivalent to "query" of type "type" in Event list "list"
    public int searchIndex(ArrayList<Event> list, String query, int type) {
        switch (type) {
            case 0: // Category
                return category(list, query, 0);
            case 1: // Title
                return title(list, query, 0);
            case 2: // Date (dd,mm,yyyy)
                return date(list, query, 0);
            case 3: // Time (mm,hh)
                return time(list, query, 0);
            case 4: // Description
                return description(list, query, 0);
            case 5: // Location
                return location(list, query, 0);
            case 6: // Ending Date (dd,mm,yyyy)
                return dateEnd(list, query, 0);
            case 7: // Ending Time (mm,hh)
                return timeEnd(list,query, 0);
        }
        return -1;
    }

    //Case 0
    //Recursive method to search for first instance of category "query" in Event list "list"
    private int category(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }
        if (e.getCategory().equals(query)) { // Base case 2
            return index;
        }
        else {
            return category(list, query, index++); // Recursive case
        }
    }

    //Case 1
    //Recursive method to search for first instance of title "query" in Event list "list"
    private int title(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        if (e.getTitle().equals(query)) { // Base case 2
            return index;
        }
        else {
            return title(list, query, index+1); // Recursive case
        }
    }

    //Case 2
    //Recursive method to search for first instance of date "query" in Event list "list"
    private int date(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        int[] d = e.getDate();
        String date = Main.intToMonth(d[1]) + " " + d[0] + ", " + d[2];
        if (date.equals(query)) { // Base case 2
            return index;
        }
        else {
            return date(list, query, index+1); // Recursive case
        }
    }

    //Case 3
    //Recursive method to search for first instance of time "query" in Event list "list"
    private int time(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        int[] t = e.getTime();
        String time = t[0] + ":" + t[1];
        if (time.equals(query)) { // Base case 2
            return index;
        }
        else {
            return time(list, query, index+1); // Recursive case
        }
    }

    //Case 4
    //Recursive method to search for first instance of description "query" in Event list "list"
    private int description(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        if (e.getDescription().equals(query)) { // Base case 2
            return index;
        }
        else {
            return description(list, query, index+1); // Recursive case
        }
    }

    //Case 5
    //Recursive method to search for first instance of location "query" in Event list "list"
    private int location(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        ArrayList<String> l = e.getLocation();
        boolean check = false;
        for (String location : l) {
            if (location.equals(query)) {
                check = true;
                break;
            }
        }
        if (check) { // Base case 2
            return index;
        }
        else {
            return location(list, query, index+1); // Recursive case
        }
    }

    //Case 6
    //Recursive method to search for first instance of dateEnd "query" in Event list "list"
    private int dateEnd(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        int[] d = e.getDateEnd();
        String date = Main.intToMonth(d[1]) + " " + d[0] + ", " + d[2];
        if (date.equals(query)) { // Base case 2
            return index;
        }
        else {
            return dateEnd(list, query, index+1); // Recursive case
        }
    }

    //Case 7
    //Recursive method to search for first instance of timeEnd "query" in Event list "list"
    private int timeEnd(ArrayList<Event> list, String query, int index) {
        Event e = null;
        try {
            e = list.get(index);
        }
        catch (IndexOutOfBoundsException a) { // Base case 1
            return -1;
        }

        int[] t = e.getTimeEnd();
        String time = t[0] + ":" + t[1];
        if (time.equals(query)) { // Base case 2
            return index;
        }
        else {
            return timeEnd(list, query, index+1); // Recursive case
        }
    }
}

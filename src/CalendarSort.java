import java.util.ArrayList;

public class CalendarSort {
    // Sorts the calendar (ArrayList<Event>) by date, then by time, ascending (true) or descending (false)
    public static void date(ArrayList<Event> list, boolean ascending) { //no need to return the arraylist since we're just passing the memory location
        if (ascending) { //swap when d1 > d2
            for (int i=0; i<list.size(); i++) {
                for (int j = 0; j < list.size()-1; j++) {
                    Event a = list.get(j);
                    Event b = list.get(j + 1);
                    int[] da = a.getDate();
                    int[] db = b.getDate();
                    int d1 = da[2]*10000 + da[1]*100 + da[0]; //format yyyymmdd
                    int d2 = db[2]*10000 + db[1]*100 + db[0]; //format yyyymmdd
                    //System.out.print(d1 + ", " + d2 + "; "); //testing
                    if (d1 > d2) {
                        list.set(j, b);
                        list.set(j + 1, a);
                    }
                }
            }
        }
        else { //swap when d1 < d2
            for (int i=0; i<list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    Event a = list.get(i);
                    Event b = list.get(i + 1);
                    int[] da = a.getDate();
                    int[] db = b.getDate();
                    int d1 = da[2]*10000 + da[1]*100 + da[0]; //format yyyymmdd
                    int d2 = db[2]*10000 + db[1]*100 + db[0]; //format yyyymmdd
                    if (d1 < d2) {
                        list.set(i, b);
                        list.set(i + 1, a);
                    }
                }
            }
        }
    }

    // Sorts the calendar (ArrayList<Event>) by group, ascending (true) or descending (false)
    public static void group(ArrayList<Event> list, boolean ascending) {
        System.out.print("(unfinished)\n");
    }

    // Sorts the calendar (ArrayList<Event>) by title in alphabetical order, ascending (true) or descending (false)
    public static void title(ArrayList<Event> list, boolean ascending) {
        System.out.print("(unfinished)\n");
    }
}

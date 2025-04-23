import java.util.ArrayList;

public class CalendarSort {
    //==================================================================================================================
    //BUBBLE SORT

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
                for (int j = 0; j < list.size()-1; j++) {
                    Event a = list.get(j);
                    Event b = list.get(j + 1);
                    int[] da = a.getDate();
                    int[] db = b.getDate();
                    int d1 = da[2]*10000 + da[1]*100 + da[0]; //format yyyymmdd
                    int d2 = db[2]*10000 + db[1]*100 + db[0]; //format yyyymmdd
                    if (d1 < d2) {
                        list.set(j, b);
                        list.set(j + 1, a);
                    }
                }
            }
        }
    }

    // Sorts the calendar (ArrayList<Event>) by category in alphabetical order, ascending (true) or descending (false)
    public static void category(ArrayList<Event> list, boolean ascending) {
        if (ascending) { //swap when a > b
            for (int i=0; i<list.size(); i++) {
                for (int j = 0; j < list.size()-1; j++) {
                    Event a = list.get(j);
                    Event b = list.get(j + 1);
                    //System.out.print(d1 + ", " + d2 + "; "); //testing
                    if (categoryCompare(a, b) > 0) {
                        list.set(j, b);
                        list.set(j + 1, a);
                    }
                }
            }
        }
        else { //swap when a < b
            for (int i=0; i<list.size(); i++) {
                for (int j = 0; j < list.size()-1; j++) {
                    Event a = list.get(j);
                    Event b = list.get(j + 1);
                    if (categoryCompare(a, b) < 0) {
                        list.set(j, b);
                        list.set(j + 1, a);
                    }
                }
            }
        }
    }

    // Sorts the calendar (ArrayList<Event>) by title in alphabetical order, ascending (true) or descending (false)
    public static void title(ArrayList<Event> list, boolean ascending) {
        if (ascending) { //swap when a > b
            for (int i=0; i<list.size(); i++) {
                for (int j = 0; j < list.size()-1; j++) {
                    Event a = list.get(j);
                    Event b = list.get(j + 1);
                    //System.out.print(d1 + ", " + d2 + "; "); //testing
                    if (titleCompare(a, b) > 0) {
                        list.set(j, b);
                        list.set(j + 1, a);
                    }
                }
            }
        }
        else { //swap when a < b
            for (int i=0; i<list.size(); i++) {
                for (int j = 0; j < list.size()-1; j++) {
                    Event a = list.get(j);
                    Event b = list.get(j + 1);
                    if (titleCompare(a, b) < 0) {
                        list.set(j, b);
                        list.set(j + 1, a);
                    }
                }
            }
        }
    }

    //==================================================================================================================
    //MERGE SORT
    public static void date(ArrayList<Event> list, ArrayList<Event> tmp, int left, int right, boolean ascending){
        if (left < right) {
            int middle = left + (right-left)/2;
            date(list, tmp, left, middle, ascending);
            date(list, tmp, middle+1, right, ascending);
            dateMerge(list, tmp, left, middle, right, ascending);
        }
    }

    public static void dateMerge(ArrayList<Event> list, ArrayList<Event> tmp, int left, int middle, int right, boolean ascending){
        int i = left;
        int j = middle+1;
        tmp.clear();

        while (i <= middle && j <= right) {
            if (ascending) {
                if (dateCompare(list.get(i), list.get(j)) <= 0) {
                    tmp.add(list.get(i));
                    i++;
                }
                else {
                    tmp.add(list.get(j));
                    j++;
                }
            }
            else {
                if (dateCompare(list.get(i), list.get(j)) >= 0) {
                    tmp.add(list.get(i));
                    i++;
                }
                else {
                    tmp.add(list.get(j));
                    j++;
                }
            }
        }
        while (i <= middle) {
            tmp.add(list.get(i));
            i++;
        }
        while (j <= right) {
            tmp.add(list.get(j));
            j++;
        }
        for (int x=0; x<tmp.size(); x++) {
            list.set(x+left, tmp.get(x));
        }
    }

    public static void category(ArrayList<Event> list, ArrayList<Event> tmp, int left, int right, boolean ascending){
        if (left < right) {
            int middle = left + (right-left)/2;
            category(list, tmp, left, middle, ascending);
            category(list, tmp, middle+1, right, ascending);
            categoryMerge(list, tmp, left, middle, right, ascending);
        }
    }

    public static void categoryMerge(ArrayList<Event> list, ArrayList<Event> tmp, int left, int middle, int right, boolean ascending){
        int i = left;
        int j = middle+1;
        tmp.clear();

        while (i <= middle && j <= right) {
            if (ascending) {
                if (categoryCompare(list.get(i), list.get(j)) <= 0) {
                    tmp.add(list.get(i));
                    i++;
                }
                else {
                    tmp.add(list.get(j));
                    j++;
                }
            }
            else {
                if (categoryCompare(list.get(i), list.get(j)) >= 0) {
                    tmp.add(list.get(i));
                    i++;
                }
                else {
                    tmp.add(list.get(j));
                    j++;
                }
            }
        }
        while (i <= middle) {
            tmp.add(list.get(i));
            i++;
        }
        while (j <= right) {
            tmp.add(list.get(j));
            j++;
        }
        for (int x=0; x<tmp.size(); x++) {
            list.set(x+left, tmp.get(x));
        }
    }

    public static void title(ArrayList<Event> list, ArrayList<Event> tmp, int left, int right, boolean ascending){
        if (left < right) {
            int middle = left + (right-left)/2;
            title(list, tmp, left, middle, ascending);
            title(list, tmp, middle+1, right, ascending);
            titleMerge(list, tmp, left, middle, right, ascending);
        }
    }

    public static void titleMerge(ArrayList<Event> list, ArrayList<Event> tmp, int left, int middle, int right, boolean ascending){
        int i = left;
        int j = middle+1;
        tmp.clear();

        while (i <= middle && j <= right) {
            if (ascending) {
                if (titleCompare(list.get(i), list.get(j)) <= 0) {
                    tmp.add(list.get(i));
                    i++;
                }
                else {
                    tmp.add(list.get(j));
                    j++;
                }
            }
            else {
                if (titleCompare(list.get(i), list.get(j)) >= 0) {
                    tmp.add(list.get(i));
                    i++;
                }
                else {
                    tmp.add(list.get(j));
                    j++;
                }
            }
        }
        while (i <= middle) {
            tmp.add(list.get(i));
            i++;
        }
        while (j <= right) {
            tmp.add(list.get(j));
            j++;
        }
        for (int x=0; x<tmp.size(); x++) {
            list.set(x+left, tmp.get(x));
        }
    }

    //==================================================================================================================
    //COMPARISONS
    public static int dateCompare(Event a, Event b) {
        int[] da = a.getDate();
        int[] db = b.getDate();
        int d1 = da[2]*10000 + da[1]*100 + da[0]; //format yyyymmdd
        int d2 = db[2]*10000 + db[1]*100 + db[0]; //format yyyymmdd
        if (d1 < d2) {
            return -1;
        }
        else if (d1 == d2) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public static int categoryCompare(Event a, Event b) {
        return a.getCategory().compareTo(b.getCategory());
    }

    public static int titleCompare(Event a, Event b) {
        return a.getTitle().compareTo(b.getTitle());
    }
}
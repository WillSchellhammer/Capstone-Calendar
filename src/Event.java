public class Event {
    // Required information
    private String title;
    private int[] date; // Format: dd,mm,yyyy

    // Optional information
    private int[] time; // Format: mm,hh
    private String description;
    private String[] location; // Event can have multiple locations saved.
    private String category = "Uncategorized"; // Name of the category which the event is part of
    private int group; // Priority of the category which the event is part of (for sorting)

    // 2D Event information
    private int[] dateEnd; // Format: dd,mmy,yyy
    private int[] timeEnd; // Format: mm,hh

    // Minimal information constructor
    public Event(String title, int[] date) {
        this.title = title;
        this.date = date;
        time = new int[]{0,0};
    }

    // All 1D information constructor
    public Event(String title, int date[], int time[], String description, String[] location, String category, int group) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.category = category;
        this.group = group;
    }

    // 2D Event constructor
    public Event(String title, int date[], int time[], String description, String[] location, String category, int group, int dateEnd[], int timeEnd[]) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
        this.category = category;
        this.group = group;
        this.dateEnd = dateEnd;
        this.timeEnd = timeEnd;
    }

    // Methods


    // Getters and Setters (generated by Intellij)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getDate() {
        return date;
    }

    public void setDate(int[] date) {
        this.date = date;
    }

    public int[] getTime() {
        return time;
    }

    public void setTime(int[] time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int[] getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(int[] dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int[] getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(int[] timeEnd) {
        this.timeEnd = timeEnd;
    }
}

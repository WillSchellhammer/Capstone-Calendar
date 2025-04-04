// Node class for bi-directional LinkedList of Strings
public class Node {

    public String data;
    public Node next;
    public Node prev;

    public Node(){
        this.data = new String();
        next = null;
        prev = null;
    }

    public Node(String data){
        this.data = data;
        next = null;
        prev = null;
    }
}
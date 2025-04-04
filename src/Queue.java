// LinkedList copied over from Lab 6 and adjusted for Strings
public class Queue {

    public Node head;
    public Node tail;

    Queue(){
        head = null;
    }

    // add String at the beginning of the queue
    public void add(String data) {
        if (head == null) {
            //set head and tail to one new Node
            head = new Node(data);
            tail = head;
        }
        else {
            Node h = new Node(data); //shallow copy
            head.prev = h; //point original head's prev to new Node
            h.next = head; //point new Node's head to original head
            head = h; //reassign tail to the new Node
        }
    }

    // remove a String from the beginning of the queue
    public String remove() {
        if (head == null) {
            System.out.println("Head of Stack is null. Aborting remove operation.");
            return null;
        }
        else {
            Node h = head; //shallow copy
            head = h.next; //head points to next node
            if (head != null)
                head.prev = null; //remove pointer to h
            return h.data; //return String at h, h is deleted from memory since there's no pointers to it
        }
    }

    public String peek() {
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // print the list (cheating how queue works oops)
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            System.out.print(curr.data);
            if(curr.next != null)
                System.out.print(" -->  ");

            if (i % 7 == 0) System.out.println();
            i = i + 1;
            curr = curr.next;
        }
        System.out.println();
    }
}
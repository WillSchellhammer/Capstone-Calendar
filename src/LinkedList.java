// LinkedList copied over from Lab 6 and adjusted for Strings
public class LinkedList {

    public Node head;
    public Node tail;
    public int size;

    LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }
    
    // remove a String from a specific index
    public String remove_from_index(int index) {
        if (index == 0) {
            return remove_from_head();
        }
        else if (index == size-1) {
            return remove_from_tail();
        }
        else if (index < size-1) {
            Node curr = head; //shallow copy
            for (int i=0; i<size-1; i++) {
                if (i == index) {
                    curr.prev.next = curr.next;
                    curr.next.prev = curr.prev;
                    size--;
                    return curr.data;
                }
                else {
                    curr = curr.next;
                }
            }
            System.out.println("Something is very wrong with remove_from_index.");
            return null; //won't ever run, exists to avoid compiler error
        }
        else {
            System.out.println("Index out of bounds of LinkedList. Aborting insert operation.");
            return null;
        }
    }

    // insert a String at a specific index
    public void insert_at_index(String x, int index) {
        if (index == 0) {
            add_at_head(x);
        }
        else if (index == size) {
            add_at_tail(x);
        }
        else if (index < size) {
            Node curr = head; //shallow copy
            for (int i=0; i<size; i++) {
                if (i == index) {
                    Node t = new Node(x);
                    t.prev = curr.prev;
                    t.next = curr;
                    curr.prev.next = t;
                    curr.prev = t;
                    break;
                }
                else {
                    curr = curr.next;
                }
            }
            size++;
        }
        else {
            System.out.println("Index out of bounds of LinkedList. Aborting insert operation.");
        }
    }

    // swap two Strings in the list at the specific indices
    public void swap(int index1, int index2) {
        if (index1 > size-1 || index2 > size-1) {
            System.out.println("Index out of bounds of LinkedList. Aborting swap operation.");
        }
        else {
            //removes the farthest index first, then adds the closest index first
            if (index1 < index2) {
                String b = remove_from_index(index2);
                String a = remove_from_index(index1);
                insert_at_index(b, index1);
                insert_at_index(a, index2);
            }
            else if (index2 < index1) {
                String a = remove_from_index(index1);
                String b = remove_from_index(index2);
                insert_at_index(a, index2);
                insert_at_index(b, index1);
            }
            else {
                System.out.println("Indices of swap are equal. Aborting swap operation.");
            }
        }
    }

    // add String at the beginning of the list
    public void add_at_head(String data) {
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
        size++;
    }

    // add String at the end of the list
    public void add_at_tail(String data) {
        if (head == null) {
            //set head and tail to one new Node
            head = new Node(data);
            tail = head;
        }
        else {
            Node t = new Node(data); //shallow copy
            tail.next = t; //point original tail to new Node
            t.prev = tail; //point new Node's prev to original tail
            tail = t; //reassign tail to the new Node
        }
        size++; //increase list size
    }

    // remove a String from the beginning of the list
    public String remove_from_head() {
        if (head == null) {
            System.out.println("Head of LinkedList is null. Aborting remove operation.");
            return null;
        }
        else {
            Node h = head; //shallow copy
            head = h.next; //head points to next node
            if (head != null)
                head.prev = null; //remove pointer to h
            size--;
            return h.data; //return String at h, h is deleted from memory since there's no pointers to it
        }
    }

    // remove a String from the end of the list
    public String remove_from_tail() {
        if (head == null) {
            System.out.println("Head of LinkedList is null. Aborting remove operation.");
            return null;
        }
        else {
            Node t = tail; //shallow copy
            tail = t.prev; //head points to previous node
            if (tail != null)
                tail.next = null; //remove pointer to t
            size--;
            return t.data; //return String at t, t is deleted from memory since there's no pointers to it
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    // print the list
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            System.out.print(curr.data);
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println();
            i = i + 1;
            curr = curr.next;
        }
        System.out.println();
    }
}
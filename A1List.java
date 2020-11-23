// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    public A1List  next; // Next Node
    public A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel
        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }


    public A1List Insert(int address, int size, int key)
    {
        if(this == null) return null;
        A1List node = new A1List(address, size, key);
        if(this.next != null){
            node.next = this.next;
            node.prev = this;
            node.next.prev = node;
            this.next = node;
            return node;
        }
        return null;
    }

    public boolean Delete(Dictionary d)
    {
        if(d == null) return false;
        if(this == null) return false;
        for(A1List curr = this.getFirst(); curr != null; curr = curr.getNext()){
            if(curr.key == d.key && curr.size == d.size && curr.address == d.address){
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                curr.next = null;
                curr.prev = null;
                curr = null;
                return true;
            }
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
        if(this == null) return null;
        for(A1List curr = this.getFirst(); curr != null; curr = curr.getNext()){
            if(curr.key == k && exact) return curr;
            if(curr.key >= k && !exact) return curr;
        }
        return null;
    }

    public A1List getFirst()
    {
        if(this == null) return null;
        if(this.prev == null && this.next == null) return null;
        if(this.prev == null && this.next.next == null) return null;
        if(this.next == null && this.prev.prev == null) return null;
        if(this.prev == null) return this.next;
        A1List curr = this;
        while(curr.prev.prev != null) curr = curr.prev;
        return curr;
    }
    
    public A1List getNext() 
    {
        if(this == null) return null;
        if(this.next == null) return null;
        if(this.next.next == null) return null;
        return this.next;
    }

    public boolean sanity()
    {

        // Check cycle with slow and fast method
        A1List slow = this;
        A1List fast = this;

        // Checks for cycle in next pointer
        while(fast != null && slow != null){
            if(fast.next == null) break;
            slow = slow.next;
            fast = fast.next;
            if(fast == null) break;
            fast = fast.next;
            if(fast == slow) return false;
        }
        fast = this;
        slow = this;

        // Checks for cycle in prev pointer
        while(fast != null && slow != null){
            if(fast.prev == null) break;
            slow = slow.prev;
            fast = fast.prev;
            if(fast == null) break;
            fast = fast.prev;
            if(fast == slow) return false;
        }


        // Check head and tail nodes
        A1List head = this;
        while(head.prev != null) head = head.prev;
        if(head.key != -1 || head.address != -1 || head.size != -1) return false; // Checks value of head
        A1List tail = this;
        while(tail.next != null) tail = tail.next;
        if(tail.key != -1 || tail.address != -1 || tail.size != -1) return false; // Checks value of tail
        if(head.next == null) return false; // Case of single node
        if(tail.prev == null) return false; // Case of single node


        // Check for continuity of list
        A1List curr = head;
        while(curr.next.next != null){
            if(curr.next.prev != curr) return false;
            curr = curr.next;
        }
        if(curr.next != tail) return false;

        curr = tail;
        while(curr.prev.prev != null){
            if(curr.prev.next != curr) return false;
            curr = curr.prev;
        }
        if(curr.prev != head) return false;

        return true;
    }
    public void print(){
        for(A1List curr = this.getFirst();curr!= null;curr = curr.getNext()){
            System.out.println("Address: "+curr.address+" Size: "+curr.size+" Key: "+curr.key);
        }
        System.out.println();
    }
}
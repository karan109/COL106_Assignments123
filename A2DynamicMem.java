// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {
        if(this.freeBlk == null) return;
        Tree address; Tree init;
        if(type == 2) address = new BSTree();
        else address = new AVLTree();
        int count = 0;
        for(Dictionary curr = this.freeBlk.getFirst();curr != null; curr = curr.getNext()){
            address.Insert(curr.address, curr.size, curr.address);
            count++;
        }
        if(count == 0) return;
        init = address.getFirst();
        for(Tree curr = init.getNext();curr != null; curr = curr.getNext()){
            if(init.address+init.size == curr.address){
                init.key = init.size;
                curr.key = curr.size;
                int addr = init.address, size = init.size+curr.size;
                this.freeBlk.Delete(init);
                this.freeBlk.Delete(curr);
                init.size = size;
                init.key = size;
                init.address = addr;
                this.freeBlk.Insert(init.address, init.size, init.size);
            }
            else init = curr;
        }
        init = null;
        address = null;
        return ;
    }
}
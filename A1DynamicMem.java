// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize){
        if(blockSize <= 0) return -1;
        Dictionary target = this.freeBlk.Find(blockSize, false);
        if(target == null) return -1;
        int address = target.address, size = target.size;
        this.freeBlk.Delete(target);
        this.allocBlk.Insert(address, blockSize, address);
        if(size > blockSize) this.freeBlk.Insert(address+blockSize, size-blockSize, size-blockSize);
        target = null;
        return address;
    }
    
    public int Free(int startAddr){
        Dictionary target = this.allocBlk.Find(startAddr, true);
        if(target == null) return -1;
        int address = target.address, size = target.size;
        this.freeBlk.Insert(address, size, size);
        this.allocBlk.Delete(target);
        target = null;
        return 0;
    }
}
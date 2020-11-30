// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key);
    }

    // Helper Functions
    private BSTree getRoot(){
        BSTree curr = this;
        while(curr.parent != null) curr = curr.parent;
        return curr;
    }
    private int compare(Dictionary x, Dictionary y){
        if(x.key<y.key) return 1;
        if(x.key == y.key && x.address < y.address) return 1;
        if(x.key == y.key && x.address == y.address && x.size < y.size) return 1;
        if(x.key == y.key && x.address == y.address && x.size == y.size) return 2;
        return 3;
    }
    private BSTree insertHelp(BSTree curr, BSTree node){
        int comp = compare(node, curr);
        if(comp == 1){
            if(curr.left != null) return insertHelp(curr.left, node);
            else{
                curr.left = node;
                node.parent = curr;
                return node;
            }
        }
        else if(comp == 3){
            if(curr.right != null) return insertHelp(curr.right, node);
            else{
                curr.right = node;
                node.parent = curr;
                return node;
            }
        }
        else return null;
    }
    private BSTree findNode(BSTree curr, Dictionary e, boolean ref){
        if(curr == null) return null;
        if(curr.key == e.key && curr == e) return curr;
        int comp = compare(e, curr);
        if(comp == 2 && !ref) return curr;
        else if(comp == 1) return findNode(curr.left, e, ref);
        else if(comp == 3) return findNode(curr.right, e, ref);
        return null;
    }
    private BSTree findMin(BSTree curr){if(curr==null)return null;if(curr.left != null) return findMin(curr.left);return curr;}
    private BSTree findGreater(BSTree curr, int key){
        if(curr == null) return null;
        if(curr.key < key) return findGreater(curr.right, key);
        BSTree temp = findGreater(curr.left, key);
        if(temp == null) return curr;
        return temp;
    }
    private BSTree findExactKey(BSTree curr, int key){
        if(curr == null) return curr;
        if(curr.key < key) return findExactKey(curr.right, key);
        if(curr.key > key) return findExactKey(curr.left, key);
        BSTree temp = findExactKey(curr.left, key);
        if(temp == null) return curr;
        return temp;
    }
    private boolean deleteEndNode(BSTree target){
        boolean right = true;
        if(target.parent.right != target) right = false;
        if(target.right == null && target.left == null){
            if(right) target.parent.right = null;
            else target.parent.left = null;
            target = null;
            return true;
        }
        if(target.right == null){
            if(right) target.parent.right = target.left;
            else target.parent.left = target.left;
            target.left.parent = target.parent;
            target.left = null;target = null;
            return true;
        }
        if(target.left == null){
            if(right) target.parent.right = target.right;
            else target.parent.left = target.right;
            target.right.parent = target.parent;
            target.right = null;target = null;
            return true;
        }
        return false;
    }
    private boolean checkBST(BSTree root, Dictionary minn, Dictionary maxx){
        if(root == null) return true;
        if(compare(root, minn) != 3 || compare(root, maxx) != 1) return false;
        return checkBST(root.left, minn, root) && checkBST(root.right, root, maxx);
    }
    private boolean dfs(BSTree root){
        if(root == null) return true;
        if(root.left != null){
            if(root.left.parent != root) return false;
            if(dfs(root.left) == false) return false;
        }
        if(root.right != null){
            if(root.right.parent != root) return false;
            if(dfs(root.right) == false) return false;
        }
        return true;
    }
    // End Helpers


    public BSTree Insert(int address, int size, int key)
    {
        if(this == null) return null;
        BSTree curr = this.getRoot();
        BSTree node = new BSTree(address, size, key);
        if(curr.parent == null && curr.right == null){curr.right = node; node.parent = curr; return node;}
        if(curr.parent == null) curr = curr.right;
        return insertHelp(curr, node);
    }
    public boolean Delete(Dictionary e)
    {
        if(e == null) return false;
        if(this == null) return false;
        if(this.parent == null && this.right == null) return false;
        BSTree curr = this.getRoot(), root = curr;
        if(curr.right == null) return false;
        if(curr.parent == null) curr = curr.right;
        BSTree target = findNode(curr, e, false);
        if(target == null) return false;
        if(deleteEndNode(target)){
            if(target != this) target.parent = null;
            return true;
        }
        BSTree succ = findMin(target.right);
        target.key = succ.key;target.address = succ.address; target.size = succ.size;
        return deleteEndNode(succ);
    }
    public BSTree Find(int key, boolean exact)
    {
        if(this == null) return null;
        if(this.parent == null && this.right == null) return null;
        BSTree curr = this.getRoot();
        if(curr.right == null) return null;
        if(curr.parent == null) curr = curr.right;
        if(!exact) return findGreater(curr, key);
        else return findExactKey(curr, key);
    }

    public BSTree getFirst()
    {
        if(this == null) return null;
        if(this.parent == null && this.right == null) return null;
        BSTree curr = this.getRoot();
        if(curr.right == null) return null;
        if(curr.parent == null) curr = curr.right;
        if(curr != null) return findMin(curr);
        return null;
    }

    public BSTree getNext()
    { 
        if(this == null) return null;
        if(this.parent == null) return null;
        if(this.right != null) return findMin(this.right);
        BSTree curr = this;
        while(curr.parent != null){
            if(curr.parent.left == curr) return curr.parent;
            curr = curr.parent;
        }
        return null;
    }
    public boolean sanity()
    {
        BSTree slow = this;
        BSTree fast = this;
        // Checks for cycle in parent pointer
        while(fast != null && slow != null){
            if(fast.parent == null) break;
            slow = slow.parent;
            fast = fast.parent;
            if(fast == null) break;
            fast = fast.parent;
            if(fast == slow) return false;
        }


        BSTree curr = this.getRoot(); // Gets the sentinel node
        if(curr.key != -1 || curr.address != -1 || curr.size != -1) return false; // Sentinel node values are checked
        if(curr.left != null) return false; // Sentinel node has no left pointer
        if(curr.right == null) return true; // Return true if tree is empty
        if(dfs(curr) == false) return false; // Cycle detection and checks if parent pointer of child is same as node
        curr = curr.right;
        Dictionary minn = new BSTree(-2147483648, -2147483648, -2147483648); // Set initial minimum to the minimum integer possible
        Dictionary maxx = new BSTree(2147483647, 2147483647, 2147483647); // Set initial maximum to the maximum integer possible
        if(checkBST(curr, minn, maxx) == false) return false; // Checks for the BST property and duplicates
        return true;
    }
}
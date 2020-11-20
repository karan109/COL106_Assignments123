// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    public AVLTree left, right;     // Children. 
    public AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    

    // Helper Functions
    private AVLTree getRoot(){AVLTree curr = this;while(curr.parent != null) curr = curr.parent;return curr;}
    private int compare(Dictionary x, Dictionary y){
        if(x.key<y.key) return 1;
        if(x.key == y.key && x.address < y.address) return 1;
        if(x.key == y.key && x.address == y.address && x.size < y.size) return 1;
        if(x.key == y.key && x.address == y.address && x.size == y.size) return 2;
        return 3;
    }
    private AVLTree insertHelp(AVLTree curr, AVLTree node){
        int comp = compare(node, curr);
        if(comp == 1){
            if(curr.left != null) return insertHelp(curr.left, node);
            else{curr.left = node;node.parent = curr;return node;}
        }
        else if(comp == 3){
            if(curr.right != null) return insertHelp(curr.right, node);
            else{curr.right = node;node.parent = curr;return node;}
        }
        else return null;
    }
    private AVLTree leftRotate(AVLTree curr){
        AVLTree temp = curr.left.right;
        if(curr.parent.right == curr) curr.parent.right = curr.left;
        else curr.parent.left = curr.left;
        curr.left.parent = curr.parent;curr.left.right = curr;curr.parent = curr.left;curr.left = temp;
        if(temp != null) temp.parent = curr;
        return curr.parent;
    }
    private AVLTree rightRotate(AVLTree curr){
        AVLTree temp = curr.right.left;
        if(curr.parent.right == curr) curr.parent.right = curr.right;
        else curr.parent.left = curr.right;
        curr.right.parent = curr.parent;curr.right.left = curr;curr.parent = curr.right;curr.right = temp;
        if(temp != null) temp.parent = curr;
        return curr.parent;
    }
    private AVLTree findMin(AVLTree curr){if(curr.left != null) return findMin(curr.left);return curr;}
    private AVLTree findGreater(AVLTree curr, int key){
        if(curr == null) return null;
        if(curr.key < key) return findGreater(curr.right, key);
        AVLTree temp = findGreater(curr.left, key);
        if(temp == null) return curr;
        return temp;
    }
    private AVLTree findExactKey(AVLTree curr, int key){
        if(curr == null) return curr;
        if(curr.key < key) return findExactKey(curr.right, key);
        if(curr.key > key) return findExactKey(curr.left, key);
        AVLTree temp = findExactKey(curr.left, key);
        if(temp == null) return curr;
        return temp;
    }
    private AVLTree findNode(AVLTree curr, Dictionary e, boolean ref){
        if(curr == null) return null;
        if(curr.key == e.key && curr == e) return curr;
        int comp = compare(e, curr);
        if(comp == 2 && !ref) return curr;
        else if(comp == 1) return findNode(curr.left, e, ref);
        else if(comp == 3) return findNode(curr.right, e, ref);
        return null;
    }
    private int getHeight(AVLTree curr){if(curr == null) return -1; return curr.height;}
    private AVLTree propagate(AVLTree curr){
        while(curr.parent != null){
            int h1 = getHeight(curr.left), h2 = getHeight(curr.right);
            if(h1-h2>1 || h1-h2<-1) return curr;
            if(h1>h2) curr.height = h1+1;
            else curr.height = h2+1;
            curr = curr.parent;
        }
        return null;
    }
    private boolean deleteEndNode(AVLTree target){
        boolean right = true;
        if(target.parent.right != target) right = false;
        if(target.right == null && target.left == null){
            if(right) target.parent.right = null;
            else target.parent.left = null;
            target.parent = null;
            return true;
        }
        if(target.right == null){
            if(right) target.parent.right = target.left;
            else target.parent.left = target.left;
            target.left.parent = target.parent;target.left = null;target.parent = null;
            return true;
        }
        if(target.left == null){
            if(right) target.parent.right = target.right;
            else target.parent.left = target.right;
            target.right.parent = target.parent;target.right = null;target.parent = null;
            return true;
        }
        return false;
    }
    private boolean checkBST(AVLTree root, Dictionary minn, Dictionary maxx){
        if(root == null) return true;
        if(compare(root, minn) != 3 || compare(root, maxx) != 1) return false;
        return checkBST(root.left, minn, root) && checkBST(root.right, root, maxx);
    }
    private boolean checkHeight(AVLTree root){
        if(root == null) return true;
        int h1 = getHeight(root.left);
        int h2 = getHeight(root.right);
        int x = h1;
        if(x<h2) x = h2;
        if(root.height != x+1) return false;
        if(h1-h2>1 || h2-h1>1) return false;
        return checkHeight(root.left) && checkHeight(root.right);
    }
    private boolean dfs(AVLTree root){
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


    public AVLTree Insert(int address, int size, int key)
    {
        if(this == null) return null;AVLTree root = this.getRoot();
        AVLTree node = new AVLTree(address, size, key);
        if(root.right == null){root.right = node; node.parent = root;return node;}
        AVLTree curr = insertHelp(root.right, node);
        if(curr == null) return curr;
        curr = propagate(curr);
        if(curr == null) return node;
        if(compare(node, curr) == 1 && compare(node, curr.left) == 1){
            curr.height--; leftRotate(curr);
        }
        else if(compare(node, curr) == 3 && compare(node, curr.right) == 3){
            curr.height--; rightRotate(curr);
        }
        else if(compare(node, curr) == 1 && compare(node, curr.left) == 3){
            curr.height--; curr.left.height--; curr.left.right.height++;rightRotate(curr.left); leftRotate(curr);
        }
        else if(compare(node, curr) == 3 && compare(node, curr.right) == 1){
            curr.height--; curr.right.height--; curr.right.left.height++;leftRotate(curr.right); rightRotate(curr);
        }
        return node;
    }
    public boolean Delete(Dictionary e)
    {
        if(e == null) return false;if(this == null) return false;if(this.parent == null && this.right == null) return false;
        AVLTree curr = this.getRoot();if(curr.parent == null) curr = curr.right;
        AVLTree target = findNode(curr, e, false);
        if(target == null) return false;
        curr = target.parent;
        if(!deleteEndNode(target)){
            AVLTree succ = findMin(target.right);
            target.key = succ.key;target.address = succ.address; target.size = succ.size;
            curr = succ.parent;deleteEndNode(succ);succ = null;
        }
        else target = null;
        curr = propagate(curr);
        while(curr != null){
            boolean left1 = true, left2 = true;
            if(getHeight(curr.left)<getHeight(curr.right)) left1 = false;
            if(left1 && getHeight(curr.left.left)<getHeight(curr.left.right)) left2 = false;
            if(!left1 && getHeight(curr.right.left)<=getHeight(curr.right.right)) left2 = false;
            if(left1 && left2){
                curr = leftRotate(curr);
                curr.right.height = getHeight(curr.right.left)+1; 
                curr.height = curr.right.height+1;
            }
            else if(!left1 && !left2){
                curr = rightRotate(curr);
                curr.left.height = getHeight(curr.left.right)+1; 
                curr.height = curr.left.height+1;
            }
            else if(left1 && !left2){
                rightRotate(curr.left); 
                curr = leftRotate(curr);
                curr.height++; 
                curr.left.height--; 
                curr.right.height -= 2;
            }
            else{
                leftRotate(curr.right); 
                curr = rightRotate(curr);
                curr.height++; 
                curr.right.height--; 
                curr.left.height -= 2;
            }
            curr = propagate(curr);
        }
        return true;
    }
        
    public AVLTree Find(int k, boolean exact)
    {
        if(this == null) return null;if(this.parent == null && this.right == null) return null;
        AVLTree curr = this.getRoot();if(curr.parent == null) curr = curr.right;
        if(!exact) return findGreater(curr, k); else return findExactKey(curr, k);
    }

    public AVLTree getFirst()
    {
        if(this == null) return null;if(this.parent == null && this.right == null) return null;
        AVLTree curr = this.getRoot();if(curr.parent == null) curr = curr.right;
        return findMin(curr);
    }

    public AVLTree getNext()
    {
        if(this == null) return null;if(this.parent == null) return null;
        if(this.right != null) return findMin(this.right);
        AVLTree curr = this;
        while(curr.parent != null){if(curr.parent.left == curr) return curr.parent;curr = curr.parent;}
        return null;
    }
    public boolean sanity()
    {
        AVLTree slow = this;
        AVLTree fast = this;
        // Checks for cycle in parent pointer
        while(fast != null && slow != null){
            if(fast.parent == null) break;
            slow = slow.parent;
            fast = fast.parent;
            if(fast == null) break;
            fast = fast.parent;
            if(fast == slow) return false;
        }

        AVLTree curr = this.getRoot(); // Gets the sentinel node
        if(curr.key != -1 || curr.address != -1 || curr.size != -1) return false; // Sentinel node values are checked
        if(curr.left != null) return false; // Sentinel node has no left pointer
        if(curr.right == null) return true; // Return true if tree is empty
        if(dfs(curr) == false) return false; // Cycle detection
        curr = curr.right;
        Dictionary minn = new AVLTree(-2147483648, -2147483648, -2147483648); // Set initial minimum to the minimum integer possible
        Dictionary maxx = new AVLTree(2147483647, 2147483647, 2147483647); // Set initial maximum to the maximum integer possible
        if(checkBST(curr, minn, maxx) == false) return false; // Checks for the BST property and duplicates
        if(checkHeight(curr) == false) return false; // Checks height difference in siblings and also height invariant for parents and children
        return true;
    }
}
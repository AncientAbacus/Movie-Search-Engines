import java.util.*;

/**
 * Binary search tree implementation.
 *
 * @author Gino Angelici
 * @since  ${11/7/22}
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    /**
     * Binary Search Tree Node inner class implementation.
     *
     * @author Gino Angelici
     * @since ${11/7/22}
     */
    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            this.key = key;
            this.dataList = dataList;
            this.left = left;
            this.right = right;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            this.key = key;
            this.dataList = new LinkedList<>();
            this.left = left;
            this.right = right;
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            return this.key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {
            return this.left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {
            return this.right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {
            return this.dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setleft(BSTNode newleft) {
            this.left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setright(BSTNode newright) {
            this.right = newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            this.dataList = newData;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            this.dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            return this.dataList.remove(data);
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        this.root = null;
        this.nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        if (this.getSize()==0) {
            return null;
        }
        return this.root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        return this.nelems;
    }

    /**
     * Insert a key into BST
     * 
     * @param key key to be inserted
     * @return true if insertion is successful and false otherwise
     */
    public boolean insert(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (this.findKey(key)) {
            return false;
        }
        if (this.getRoot() == null) {
            this.root = new BSTNode(null, null, key);
            this.nelems++;
            return true;
        }
        this.nelems++;
        return addHelper(this.getRoot(), key);
    }

    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return containsHelper(this.getRoot(), key);
    }

    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If either key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {
        if (key == null||data == null) {
            throw new NullPointerException();
        }
        if (!this.findKey(key)) {
            throw new IllegalArgumentException();
        }
        findNodeHelper(this.getRoot(), key).dataList.add(data);
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {
        if (key == null) {
             throw new NullPointerException();
        }
        if (!this.findKey(key)) {
            throw new IllegalArgumentException();
        }
        return findNodeHelper(this.getRoot(), key).getDataList();
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        if (this.getSize() == 0) {
            return -1;
        }
        return findHeightHelper(root);
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root) {
        if (root == null) {
            return -1;
        }
        int leftHeight = findHeightHelper(root.left);
        int rightHeight = findHeightHelper(root.right);
        if (leftHeight>rightHeight) {
            return 1 + leftHeight;
        }
        else
            return 1 + rightHeight;
    }

    /**
     * Helper method that finds whether key is in the BST
     *
     * @param currRoot root of current BST
     * @param toFind key to locate
     * @return whether of the node whose key value is 'key'
     */
    private boolean containsHelper(BSTNode currRoot, T toFind){
        if (currRoot == null) {
            return false;
        }
        if (toFind.compareTo(currRoot.getKey()) < 0) {
            return containsHelper(currRoot.getLeft(), toFind);
        } else if (toFind.compareTo(currRoot.getKey()) > 0) {
            return containsHelper(currRoot.getRight(), toFind);
        } else return true;
    }

    /**
     * Helper function that finds Node whose key is the specified key
     *
     * @param currRoot root of current BST
     * @param toFind key of Node to locate
     * @return Node whose key is toFind
     */
    private BSTNode findNodeHelper(BSTNode currRoot, T toFind){
        if (toFind.compareTo(currRoot.getKey()) < 0) {
            return findNodeHelper(currRoot.getLeft(), toFind);
        } else if (toFind.compareTo(currRoot.getKey()) > 0) {
            return findNodeHelper(currRoot.getRight(), toFind);
        } else return currRoot;
    }

    /**
     * Helper method to insert a key into BST
     *
     * @param currRoot current root
     * @param toAdd key to add
     * @return true if insertion is successful and false otherwise
     */
    private boolean addHelper(BSTNode currRoot, T toAdd){
        int value = toAdd.compareTo(currRoot.getKey());
        if (value == 0) {
            return false;
        }
        if (value < 0) {
            if (currRoot.getLeft() == null) {
                currRoot.setleft(new BSTNode(null, null, toAdd));
            } else {
                addHelper(currRoot.getLeft(), toAdd);
            }
        } else {
            if (currRoot.getRight() == null) {
                currRoot.setright(new BSTNode(null, null, toAdd));
            } else {
                addHelper(currRoot.getRight(), toAdd);
            }
        }
        return true;
    }

    /* * * * * BST Iterator * * * * */

    /**
     * Binary Search Tree Iterator inner class implementation.
     *
     * @author Gino Angelici
     * @since ${11/7/22}
     */
    public class BSTree_Iterator implements Iterator<T> {
        Stack<T> leftPath;

        /**
         * Initializes a Stack with the leftPath of the root
         */
        public BSTree_Iterator() {
            leftPath = new Stack<>();
            BSTNode cur = getRoot();
            while (cur != null) {
                leftPath.push(cur.getKey());
                cur = cur.getLeft();
            }
        }

        /**
         * Checks if the Stack is empty or not
         *
         * @return whether Stack is empty
         */
        public boolean hasNext() {
            if (leftPath.isEmpty()) {
                return false;
            }
            return true;
        }

        /**
         * Returns next item of BST
         *
         * @return next item in the BST
         * @throws NoSuchElementException if there is no next item
         */
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            BSTNode cur = findNodeHelper(getRoot(), leftPath.peek()).getRight();
            T to_return = leftPath.pop();
            while (cur != null) {
                leftPath.push(cur.getKey());
                cur = cur.getLeft();
            }
            return to_return;
        }
    }

    /**
     * Iterator inner class implementation.
     *
     * @author Gino Angelici
     * @since ${11/7/22}
     */
    public Iterator<T> iterator() {
        return new BSTree_Iterator();
    }
}

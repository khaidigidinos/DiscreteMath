import java.util.*;

public class BST<E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;
    /** A list contains all the nodes that in the searched path */
    protected ArrayList<TreeNode<E>> searchPath = null;
    /** Create a default binary tree */
    public BST() {
    }

    List<E> subList(E fromElement, E toElement) {
        List<E> list = new ArrayList<>();
        subList(fromElement, toElement, root, list);
        return list;
    }

    SortedSet<E> tailSet(E e)
    {
        TreeSet<E> list = new TreeSet<E>();
        tailSet(e, root, list);
        return list;
    }
    void tailSet(E e, TreeNode<E> root, SortedSet<E> list)
    {
        if(root == null) return;
        if(root.element.compareTo(e) < 1) list.add(root.element);
        tailSet(e, root.right, list);
        tailSet(e, root.left, list);
    }

    SortedSet<E> headSet(E e)
    {
        TreeSet<E> list = new TreeSet<E>();
        tailSet(e, root, list);
        return list;
    }
    void headSet(E e, TreeNode<E> root, SortedSet<E> list)
    {
        if(root == null) return;
        if(root.element.compareTo(e) == 1) list.add(root.element);
        headSet(e, root.right, list);
        headSet(e, root.left, list);
    }

    void subList(E fromElement, E toElement, TreeNode<E> root, List<E> list) {
        if (root == null)return;
        else if (root.element.compareTo(fromElement) < 0)
            subList(fromElement, toElement, root.right, list);
        else if (root.element.compareTo(toElement) >= 0)
            subList(fromElement, toElement, root.left, list);
        else {
            subList(fromElement, toElement, root.left, list);
            list.add(root.element);subList(fromElement, toElement, root.right, list);
        }
    }

    public E first() {
        TreeNode<E> currrent = root;
        if(root == null) return null;
        else
        {
            return findFirst(currrent);
        }
    }

    E findFirst(TreeNode<E> currentNode)
    {
        if(currentNode.left == null)
        {
            return currentNode.element;
        }
        else
        {
            return findFirst(currentNode.left);
        }
    }

    public E last() {
        TreeNode<E> currrent = root;
        if(root == null) return null;
        else
        {
            return findLast(currrent);
        }
    }

    E findLast(TreeNode<E> currentNode)
    {
        if(currentNode.right == null)
        {
            return currentNode.element;
        }
        else
        {
            return findLast(currentNode.right);
        }
    }

    /** Create a binary tree from an array of objects */
    public BST(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    @Override /** Returns true if the element is in the tree */
    public boolean search(E e) {
        TreeNode<E> current = root;

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                return true; // e is found
        }

        return false;
    }

    @Override /** Insert element o into the binary tree
     * Return true if the element is inserted successfully */
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e);
        else {
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null)
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false;

            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }

        size++;
        return true;
    }

    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    @Override /** Inorder traversal from the root */
    public void inorder() {
        inorder(root);
    }

    /** Inorder traversal from a subtree */
    protected void inorder(TreeNode<E> root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }

    @Override /** Postorder traversal from the root */
    public void postorder() {
        postorder(root);
    }

    /** Postorder traversal from a subtree */
    protected void postorder(TreeNode<E> root) {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    @Override /** Preorder traversal from the root */
    public void preorder() {
        preorder(root);
    }

    /** Preorder traversal from a subtree */
    protected void preorder(TreeNode<E> root) {
        if (root == null) return;
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    /** This inner class is static, because it does not access
     any instance members defined in its outer class */
    public static class TreeNode<E> {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    @Override /** Get the number of nodes in the tree */
    public int getSize() {
        return size;
    }

    /** Returns the root of the tree */
    public TreeNode<E> getRoot() {
        return root;
    }

    /** Returns a path from the root leading to the specified element */
    public ArrayList<TreeNode<E>> path(E e) {
        ArrayList<TreeNode<E>> list =
                new ArrayList<>();
        TreeNode<E> current = root;

        while (current != null) {
            list.add(current);
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }
        searchPath = list;
        return searchPath;
    }

    @Override /** Delete an element from the binary tree.
     * Return true if the element is deleted successfully
     * Return false if the element is not in the tree */
    public boolean delete(E e) {
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break;
        }

        if (current == null)
            return false;

        
        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            }
            else {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else {
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }

            current.element = rightMost.element;

            
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true;
    }

    @Override /** Obtain an iterator. Use inorder. */
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    public java.util.Iterator<E> iterator(int index) {
        return new InorderIterator(index);
    }

    private class InorderIterator implements java.util.Iterator<E> {
        private ArrayList<E> list =
                new ArrayList<>();
        private int current = 0;

        public InorderIterator() {
            inorder();
        }

        public InorderIterator(int index) {
            if (index < 0 || index > size())
                throw new IndexOutOfBoundsException();
            inorder();
            current = index;
        }

        /** Inorder traversal from the root*/
        private void inorder() {
            inorder(root);
        }

        /** Inorder traversal from a subtree */
        private void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override /** More elements for traversing? */
        public boolean hasNext() {
            return current < list.size();
        }

        @Override /** Get the current element and move to the next */
        public E next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return list.get(current++);
        }

        @Override /** Remove the element most recently returned */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /** Get the list of inorder traversal of the whole tree */
    public List<E> inorderList(){
        List<E> list = new ArrayList<E>();
        inorderList(root, list);
        return list;
    }

    /** Get the list of preorder traversal of the whole tree*/
    public List<E> preorderList(){
        List<E> list = new ArrayList<E>();
        preorderList(root, list);
        return list;
    }

    /** Get the list of postorder traversal of the whole tree*/
    public List<E> postorderList(){
        List<E> list = new ArrayList<E>();
        postorderList(root, list);
        return list;
    }

    /** Get the list of breadth-first traversal of the whole tree */
    public List<E> breadthFirstOrderList() {
        List<E> list = new ArrayList<E>();
        Queue<TreeNode<E>> queue = new LinkedList<TreeNode<E>>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode<E> node = queue.poll();
            list.add(node.element);
            if(node.left != null){
                queue.add(node.left);
            }
            if(node.right != null){
                queue.add(node.right);
            }
        }
        return list;
    }

    /** Get the height of the tree from the root */
    public int height()
    {
        return height(root);
    }

    /** Get height of the tree from the given node root */
    private int height(TreeNode<E> root){
        if(root != null)
        {
            return  1 + Math.max(height(root.left), height(root.right));
        }
        else {
            return -1; /** Base case */
        }
    }

    /** A recursive call to create a list of inorder with a given node in the tree */
    private void inorderList(TreeNode<E> root, List<E> list){
        if(root.left != null) inorderList(root.left, list);
        list.add(root.element);
        if(root.right != null) inorderList(root.right, list);
    }

    /** A recursive call to create a list of preorder with a given node in the tree */
    private void preorderList(TreeNode<E> root, List<E> list){
        list.add(root.element);
        if(root.left != null) preorderList(root.left, list);
        if(root.right != null) preorderList(root.right, list);
    }

    /** A recursive call to create a list of postorder with a given node in the tree */
    private void postorderList(TreeNode<E> root, List<E> list){
        if(root.left != null) postorderList(root.left, list);
        if(root.right != null) postorderList(root.right, list);
        list.add(root.element);
    }

    @Override /** Remove all elements from the tree */
    public void clear() {
        root = null;
        size = 0;
    }
}

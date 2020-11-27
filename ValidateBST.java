import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


/**
 * 
 */
class TreeNode {

    // **** class members ****
    int val;
    TreeNode left;
    TreeNode right;

    // **** constructor(s) ****
    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    // **** ****
    @Override
    public String toString() {
        return "" + this.val;
    }
}


/**
 * LeetCode 98. Validate Binary Search Tree
 * https://leetcode.com/problems/validate-binary-search-tree/
 */
public class ValidateBST {


    /**
     * Enumerate which child in the node at the head of the queue 
     * (see populateTree function) should be updated.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    enum Child {
        LEFT,
        RIGHT
    }
    
    
    // **** child turn to insert on node at head of queue ****
    static Child  insertChild = Child.LEFT;


    /**
     * Generate the number of nodes in the BT at the specified level.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    static int nodesInLevel(int level) {
        if (level < 1)
            return 0;
        else
            return (int)Math.pow(2.0, level - 1);
    }


    /**
     * Populate the specified level in the specified binary tree.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    static TreeNode populateBTLevel(TreeNode root, int level, String[] subArr, Queue<TreeNode> q) {
    
        // **** populate binary tree root (if needed) ****
        if (root == null) {
            root = new TreeNode(Integer.parseInt(subArr[0]));
            q.add(root);
            return root;
        }
    
        // **** ****
        TreeNode child = null;
    
        // **** traverse the sub array of node values ****
        for (int i = 0; (i < subArr.length) && (subArr[i] != null); i++) {
    
            // **** child node ****
            child = null;
    
            // **** create and attach child node (if needed) ****
            if (!subArr[i].equals("null"))
                child = new TreeNode(Integer.parseInt(subArr[i]));
    
            // **** add child to the queue ****
            q.add(child);
    
            // **** attach child node (if NOT null) ****
            if (child != null) {
                if (insertChild == Child.LEFT)
                    q.peek().left = child;
                else
                    q.peek().right = child;
            }
    
            // **** remove node from the queue (if needed) ****
            if (insertChild == Child.RIGHT) {    
                q.remove();
            }
    
            // **** toggle insert for next child ****
            insertChild = (insertChild == Child.LEFT) ? Child.RIGHT : Child.LEFT;
        }
    
        // **** return root of binary tree ****
        return root;
    }


    /**
     * Populate binary tree using the specified array of integer and null values.
     * !!! PART OF TEST SCAFFOLDING!!!
     */
    static TreeNode populateBT(String[] arr) {
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();
    
        // **** root for the binary tree ****
        TreeNode root = null;

        // **** start with the left child ****
        insertChild = Child.LEFT;
    
        // **** begin and end of substring to process ****
        int b   = 0;
        int e   = 0;
    
        // **** loop once per binary tree level ****
        for (int level = 1; b < arr.length; level++) {
    
            // **** count of nodes at this level ****
            int count = nodesInLevel(level);
    
            // **** compute e ****
            e = b + count;
    
            // **** generate sub array of strings ****
            String[] subArr = Arrays.copyOfRange(arr, b, e);
    
            // **** populate the specified level in the binary tree ****
            root = populateBTLevel(root, level, subArr, q);
    
            // **** update b ****
            b = e;
        }
    
        // **** return populated binary tree ****
        return root;
    }


    /**
     * Recursive call
     */
    static void traverse0(TreeNode node, List<Integer> vals) {

        // **** visit left sub tree (if needed) ****
        if (node.left != null)
            traverse0(node.left, vals);

        // **** add node value to list ****
        vals.add(node.val);

        // **** visit right sub tree (if needed) ****
        if (node.right != null)
            traverse0(node.right, vals);
    }


    /**
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     * 
     * Runtime: 4 ms, faster than 5.72% of Java online submissions.
     * Memory Usage: 41.4 MB, less than 5.01% of Java online submissions.
     */
    static boolean isValidBST0(TreeNode root) {

        // **** sanity checks ****
        if (root == null)
            return true;

        // **** initialization ****
        List<Integer> vals  = new ArrayList<Integer>();

        // **** in order tree traversal ****
        traverse0(root, vals);

        // **** sanity check ****
        if (vals.size() == 1)
            return true;

        // **** traverse list of vals ****
        for (int i = 1; i < vals.size(); i++) {
            if (vals.get(i - 1) >= vals.get(i))
                return false;
        }

        // **** valid BST ****
        return true;
    }


    /**
     * Recursive call.
     */
    static void traverse(TreeNode node, Integer[] prevVal, boolean[] isValid) {
        if (node != null) {

            // **** traverse left sub tree ****
            traverse(node.left, prevVal, isValid);

            // **** set flag to false (if needed) ****
            if (prevVal[0] != null && prevVal[0] >= node.val) {
                isValid[0] = false;
            }

            // **** save this value ****
            prevVal[0] = node.val;

            // **** traverse right sub tree ****
            traverse(node.right, prevVal, isValid);
        }
    }


    /**
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     * 
     * Runtime: 0 ms, faster than 100.00% of Java online submissions.
     * Memory Usage: 39.3 MB, less than 5.83% of Java online submissions.
     */
    static boolean isValidBST(TreeNode root) {

        // **** is valid flag ****
        boolean[] isValid = new boolean[] {true};

        // **** traverse tree ****
        traverse(root, new Integer[] {null}, isValid);
        
        // **** return result ****
        return isValid[0];
    }


    /**
     * Display node values in a binary tree in order traversal.
     * Recursive approach.
     * !!! NOT PART OF SOLUTION !!!
     */
    static void inOrder(TreeNode root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.toString() + " ");
            inOrder(root.right);
        }
    }


    /**
     * Display node values in a binary tree in order traversal.
     * Iterative approach.
     * !!! NOT PART OF SOLUTION !!!
     */
    static void inOrderIterative(TreeNode root) {

        // **** sanity check(s) ****
        if (root == null)
            return;

        // **** initialization ****
        Stack<TreeNode> stack   = new Stack<>();
        TreeNode node           = root;

        // **** traverse the binary tree ****
        while (node != null || stack.size() > 0) {

            // **** save node and left children of node ****
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            // **** pop next node ****
            node = stack.pop();

            // **** display node value ****
            System.out.print(node.val + " ");

            // **** visit right subtree ****
            node = node.right;
        }
    }


    /**
     * Recursive call.
     * Makes use of stack.
     * 
     * Runtime: 1 ms, faster than 29.59% of Java online submissions.
     * Memory Usage: 38.7 MB, less than 46.91% of Java online submissions.
     */
    static boolean isValidBST1(TreeNode root) {

        // **** sanity check(s) ****
        if (root == null)
            return true;

        // **** initializion ****
        Stack<TreeNode> stack   = new Stack<>();
        TreeNode prev           = null;

        // **** loop thorugh the binary tree ****
        while (root != null || !stack.isEmpty()) {

            // **** push all left nodes into the stack ****
            while (root != null) {

                // ???? ????
                System.out.println("<<< push root: " + root.toString());

                // **** ****
                stack.push(root);

                // **** ****
                root = root.left;
            }

            // ???? ????
            System.out.println("<<< stack bottom -> " + stack.toString());

            // **** pop element ****
            root = stack.pop();

            // ???? ????
            System.out.println("<<< pop root: " + root.toString());

            // **** check if invalid root value ****
            if (prev != null && root.val <= prev.val) {

                // ???? ????
                System.out.println("<<< root: " + root.toString() + " <= prev: " + prev.toString() + " !!!");

                // **** invalid BST ****
                return false;
            }

            // **** set previous node ****
            prev = root;

            // ???? ????
            System.out.println("<<< prev: " + prev.toString());

            // **** visit right child ****
            root = root.right;     
        }

        // **** valid BST ****
        return true;
    }


    /**
     * Recursive call
     * 
     * Runtime: 267 ms, faster than 5.72% of Java online submissions.
     * Memory Usage: 41.9 MB, less than 5.01% of Java online submissions.
     */
    static boolean traverse2(TreeNode node, List<Integer> vals) {

        // **** ****
        boolean retVal = true;

        // **** visit left sub tree (if needed) ****
        if (node.left != null) {
            retVal = traverse2(node.left, vals);
        }

        // **** add node value to list ****
        vals.add(node.val);

        // **** ****
        if (vals.size() > 1) {

            // **** get indices ****
            int firstIndx   = vals.indexOf(node.val);
            int lastIndx    = vals.lastIndexOf(node.val);

            // **** duplicate value  ****
            if (firstIndx != lastIndx) {
                return false;
            }

            // **** value out of order ****
            if (vals.get(firstIndx - 1) >= node.val) {
                return false;
            }
        }

        // **** visit right sub tree (if needed) ****
        if (retVal == true && node.right != null) {
            retVal = traverse2(node.right, vals);
        }

        // **** ****
        return retVal;
    }


    /**
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     * 
     */
    static boolean isValidBST2(TreeNode root) {

        // **** sanity check(s) ****
        if (root == null)
            return true;

        if (root.left == null && root.right == null)
            return true;

        // **** initialization ****
        List<Integer> vals  = new ArrayList<Integer>();

        // **** in order tree traversal ****
        return traverse2(root, vals);
    }


    /**
     * Test scaffolding
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // **** open buffered stream ****
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        // **** read and split input line ****
        String[] arr = input.readLine().split(",");

        // **** close buffered stream ****
        input.close();

        // **** populate the binary tree ****
        TreeNode root = populateBT(arr);

        // ???? inOrder binary tree traversal (recursive approach) ????
        System.out.print("main <<<          inOrder: ");
        inOrder(root);
        System.out.println();

        // ???? inOrder binary tree traversal (iterative approach) ????
        System.out.print("main <<< inOrderIterative: ");
        inOrderIterative(root);
        System.out.println();

        // **** determine if a valid BST ****
        System.out.println("main <<< isValidBST1: " + isValidBST1(root));

        // **** determine if a valid BST ****
        System.out.println("main <<< isValidBST2: " + isValidBST2(root));

        // **** determine if a valid BST ****
        System.out.println("main <<< isValidBST0: " + isValidBST0(root));

        // **** determine if a valid BST ****
        System.out.println("main <<<  isValidBST: " + isValidBST(root));
    }
}
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
     * Runtime: 1 ms, faster than 29.86% of Java online submissions.
     * Memory Usage: 39 MB, less than 16.71% of Java online submissions.
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
     * Memory Usage: 38.4 MB, less than 78.20% of Java online submissions.
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

        // **** determine if valid BST ****
        System.out.println("main <<< isValidBST0: " + isValidBST0(root));

        // **** determine if valid BST ****
        System.out.println("main <<<  isValidBST: " + isValidBST(root));
    }
}
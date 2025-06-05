package matrix;

import java.util.*;

public class TreeBuilder {

    public static TreeNode buildTreeFromAdjacency(int[][] matrix) {
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        Queue<TreeNode> queue = new LinkedList<>();

        TreeNode root = new TreeNode(0);
        queue.add(root);
        visited[0] = true;

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            List<TreeNode> children = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if (matrix[current.value][j] == 1 && !visited[j]) {
                    TreeNode child = new TreeNode(j);
                    children.add(child);
                    queue.add(child);
                    visited[j] = true;
                }
            }

          
            if (children.size() > 0) current.left = children.get(0);
            if (children.size() > 1) current.right = children.get(1);
        }

        return root;
    }

    public static void printPreorder(TreeNode node) {
        if (node == null) return;
        System.out.print((node.value + 1) + " "); 
        printPreorder(node.left);
        printPreorder(node.right);
    }
    public static void printInorder(TreeNode node) {
    if (node == null) return;
    printInorder(node.left);
    System.out.print((node.value + 1) + " "); 
    printInorder(node.right);
}

public static void printPostorder(TreeNode node) {
    if (node == null) return;
    printPostorder(node.left);
    printPostorder(node.right);
    System.out.print((node.value + 1) + " "); 
}
}

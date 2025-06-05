package matrix;

import java.util.ArrayList;
import java.util.List;

public class ConsoleTreePrinter {
    public static void printTreeWithStats(TreeNode root) {
        if (root == null) {
            System.out.println("El árbol está vacío");
            return;
        }

       
        printTreeVisual(root);

       
        printTreeStatistics(root);
    }

    private static void printTreeVisual(TreeNode root) {
        System.out.println("\nRepresentación visual del árbol:");
        System.out.println("            " + (root.value + 1));
        
        if (root.left != null || root.right != null) {
            System.out.println("        /       \\");
            System.out.print("      ");
            System.out.print(root.left != null ? (root.left.value + 1) : " ");
            System.out.print("         ");
            System.out.println(root.right != null ? (root.right.value + 1) : " ");
            
            if ((root.left != null && (root.left.left != null || root.left.right != null)) ||
                (root.right != null && (root.right.left != null || root.right.right != null))) {
                
                System.out.println("    /   \\     /   \\");
                
                System.out.print("  ");
                System.out.print(root.left != null && root.left.left != null ? (root.left.left.value + 1) : " ");
                System.out.print("     ");
                System.out.print(root.left != null && root.left.right != null ? (root.left.right.value + 1) : " ");
                
                System.out.print("   ");
                System.out.print(root.right != null && root.right.left != null ? (root.right.left.value + 1) : " ");
                System.out.print("     ");
                System.out.println(root.right != null && root.right.right != null ? (root.right.right.value + 1) : " ");
                
                if (root.left != null && root.left.right != null) {
                    System.out.println("  /       /");
                    System.out.print(" ");
                    System.out.print(root.left.right.left != null ? (root.left.right.left.value + 1) : " ");
                    System.out.print("       ");
                    System.out.println(root.right != null && root.right.left != null && root.right.left.left != null ? 
                                     (root.right.left.left.value + 1) : " ");
                }
            }
        }
    }

    private static void printTreeStatistics(TreeNode root) {
        System.out.println("\nEstadísticas del árbol:");
        
      
        List<List<Integer>> levels = new ArrayList<>();
        calculateLevels(root, 0, levels);
        
    
        System.out.println("\nNodos por nivel:");
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("Nivel " + i + ": " + levels.get(i) + 
                             " (Total: " + levels.get(i).size() + ")");
        }
        
       
        int height = calculateHeight(root);
        System.out.println("\nAltura del árbol: " + height);
        
      
        List<Integer> leaves = new ArrayList<>();
        countLeaves(root, leaves);
        System.out.println("Número de hojas: " + leaves.size());
        System.out.println("Hojas: " + leaves);
        
        
        List<Integer> nonLeaves = new ArrayList<>();
        countNonLeaves(root, nonLeaves);
        System.out.println("Número de nodos hijos: " + nonLeaves.size());
        System.out.println("Nodos hijos: " + nonLeaves);
    }

    private static void calculateLevels(TreeNode node, int level, List<List<Integer>> levels) {
        if (node == null) return;
        
        if (levels.size() <= level) {
            levels.add(new ArrayList<>());
        }
        
        levels.get(level).add(node.value + 1);
        
        calculateLevels(node.left, level + 1, levels);
        calculateLevels(node.right, level + 1, levels);
    }

    private static int calculateHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    private static void countLeaves(TreeNode node, List<Integer> leaves) {
        if (node == null) return;
        
        if (node.left == null && node.right == null) {
            leaves.add(node.value + 1);
        } else {
            countLeaves(node.left, leaves);
            countLeaves(node.right, leaves);
        }
    }

    private static void countNonLeaves(TreeNode node, List<Integer> nonLeaves) {
        if (node == null) return;
        
        if (node.left != null || node.right != null) {
            nonLeaves.add(node.value + 1);
            countNonLeaves(node.left, nonLeaves);
            countNonLeaves(node.right, nonLeaves);
        }
    }
}
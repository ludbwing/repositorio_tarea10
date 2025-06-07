package matrix;

import java.util.ArrayList;
import java.util.List;

public class ConsoleTreePrinter {
    private static final String INDENT = "    ";
    
    public static void printTreeWithStats(TreeNode root) {
        if (root == null) {
            System.out.println("El árbol está vacío");
            return;
        }

        System.out.println("\n=== REPRESENTACIÓN DEL ÁRBOL ===");
        printTreeVisual(root);
        printTreeStatistics(root);
    }

    private static void printTreeVisual(TreeNode root) {
        System.out.println("\nEstructura visual del árbol:");
        printTreeRecursive(root, 0);
    }

    private static void printTreeRecursive(TreeNode node, int level) {
        if (node == null) return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(INDENT);
        }
        
        sb.append("└── ").append(node.value + 1);
        System.out.println(sb.toString());

        printTreeRecursive(node.left, level + 1);
        printTreeRecursive(node.right, level + 1);
    }

    private static void printTreeStatistics(TreeNode root) {
        System.out.println("\n=== ESTADÍSTICAS DEL ÁRBOL ===");
        
        List<List<Integer>> levels = new ArrayList<>();
        calculateLevels(root, 0, levels);
        
        System.out.println("\nNodos por nivel:");
        for (int i = 0; i < levels.size(); i++) {
            System.out.printf("Nivel %d: %s (Total: %d)\n", 
                i, levels.get(i), levels.get(i).size());
        }
        
        int height = calculateHeight(root);
        System.out.printf("\nAltura del árbol: %d\n", height);
        
        List<Integer> leaves = new ArrayList<>();
        countLeaves(root, leaves);
        System.out.printf("Número de hojas: %d\n", leaves.size());
        System.out.println("Hojas: " + leaves);
        
        List<Integer> nonLeaves = new ArrayList<>();
        countNonLeaves(root, nonLeaves);
        System.out.printf("Número de nodos internos: %d\n", nonLeaves.size());
        System.out.println("Nodos internos: " + nonLeaves);
        
        System.out.println("\nRecorridos:");
        System.out.print("Preorden: ");
        TreeBuilder.printPreorder(root);
        System.out.print("\nInorden: ");
        TreeBuilder.printInorder(root);
        System.out.print("\nPostorden: ");
        TreeBuilder.printPostorder(root);
        System.out.println();
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
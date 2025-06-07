package matrix;

import java.util.*;

public class TreeBuilder {
    public static TreeNode buildTreeFromAdjacency(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }

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

            if (!children.isEmpty()) {
                current.left = children.get(0);
                if (children.size() > 1) {
                    current.right = children.get(1);
                }
            }
        }

        return root;
    }

    public static TreeNode buildTreeFromIncidence(int[][] incidenceMatrix) {
        if (incidenceMatrix == null || incidenceMatrix.length == 0) {
            return null;
        }

        int nodeCount = incidenceMatrix.length;
        int edgeCount = incidenceMatrix[0].length;
        
        // Crear lista de adyacencia
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }

        // Procesar cada arista (columna)
        for (int j = 0; j < edgeCount; j++) {
            int parentNode = -1;
            int childNode = -1;
            
            for (int i = 0; i < nodeCount; i++) {
                if (incidenceMatrix[i][j] == -1) {
                    parentNode = i;
                } else if (incidenceMatrix[i][j] == 1) {
                    childNode = i;
                }
            }
            
            if (parentNode != -1 && childNode != -1) {
                adjacencyList.get(parentNode).add(childNode);
            }
        }

        // Construir árbol usando BFS
        boolean[] visited = new boolean[nodeCount];
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(0);
        queue.add(root);
        visited[0] = true;

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            List<Integer> children = adjacencyList.get(current.value);
            
            // Ordenar para consistencia
            Collections.sort(children);
            
            // Asignar hijos (máximo 2 para árbol binario)
            if (!children.isEmpty()) {
                current.left = new TreeNode(children.get(0));
                queue.add(current.left);
                visited[children.get(0)] = true;
                
                if (children.size() > 1) {
                    current.right = new TreeNode(children.get(1));
                    queue.add(current.right);
                    visited[children.get(1)] = true;
                }
            }
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
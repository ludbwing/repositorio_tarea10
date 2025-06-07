package matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class MatrixReader {
    private MatrixReader() {}

    public static int[][] readAdjacency(String path) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new File(path))) {
            int n = sc.nextInt();
            int[][] matrix = new int[n][n];
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = sc.nextInt();
                }
            }
            
            return matrix;   
        }
    }

    public static int[][] readIncidence(String path) throws FileNotFoundException {
    try (Scanner sc = new Scanner(new File(path))) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] matrix = new int[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        
        // Validar que sea un árbol binario completo
        if (n != m + 1) {
            throw new IllegalArgumentException("La matriz no representa un árbol binario completo");
        }
        
        return matrix;
    }
}

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.printf("%2d ", value);
            }
            System.out.println();
        }
    }

    public static int[][] convertIncidenceToAdjacency(int[][] incidenceMatrix) {
        int n = incidenceMatrix.length;
        int m = incidenceMatrix[0].length; 
        int[][] adjacencyMatrix = new int[n][n];
        
        for (int edge = 0; edge < m; edge++) {
            List<Integer> connectedNodes = new ArrayList<>();
            
            for (int node = 0; node < n; node++) {
                if (incidenceMatrix[node][edge] == 1) {
                    connectedNodes.add(node);
                }
            }
            
            // Conectar todos los nodos de esta arista
            for (int i = 0; i < connectedNodes.size(); i++) {
                for (int j = i + 1; j < connectedNodes.size(); j++) {
                    int from = connectedNodes.get(i);
                    int to = connectedNodes.get(j);
                    adjacencyMatrix[from][to] = 1;
                    adjacencyMatrix[to][from] = 1;
                }
            }
        }
        
        return adjacencyMatrix;
    }
}
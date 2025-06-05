package matrix;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Demo interactivo: permite al estudiante cargar y mostrar matrices
 * de adyacencia o incidencia y generar un árbol binario a partir de la matriz de adyacencia.
 */
public class MatrixDemo {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        do {
            System.out.println("\n=== DEMO MATRICES ===");
            System.out.println("1. Mostrar adyacencia.txt de ejemplo");
            System.out.println("2. Mostrar incidencia.txt de ejemplo");
            System.out.println("3. Leer archivo de adyacencia por ruta");
            System.out.println("4. Leer archivo de incidencia por ruta");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            option = SC.nextInt();

            switch (option) {
                case 1 -> showAdj("src/matrices/adyacencia.txt");
                case 2 -> showInc("src/matrices/incidencia.txt");
                case 3 -> {
                    SC.nextLine();
                    System.out.print("Ruta adyacencia: ");
                    showAdj(SC.nextLine());
                }
                case 4 -> {
                    SC.nextLine();
                    System.out.print("Ruta incidencia: ");
                    showInc(SC.nextLine());
                }
                case 0 -> System.out.println("Fin demo.");
                default -> System.out.println("Opción inválida.");
            }
        } while (option != 0);
    }

    private static void showAdj(String path) {
    try {
        int[][] mat = MatrixReader.readAdjacency(path);
        System.out.println("Matriz de adyacencia:");
        MatrixReader.printMatrix(mat);
        TreeNode root = TreeBuilder.buildTreeFromAdjacency(mat);
        
        // Reemplazar la llamada anterior con:
        ConsoleTreePrinter.printTreeWithStats(root);
        
        TreeVisualizer.show(root); 
    } catch (FileNotFoundException e) {
        System.out.println("Archivo no encontrado: " + path);
    }
}



    private static void showInc(String path) {
        try {
            int[][] mat = MatrixReader.readIncidence(path);
            System.out.println("Matriz de incidencia:");
            MatrixReader.printMatrix(mat);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + path);
        }
    }
}

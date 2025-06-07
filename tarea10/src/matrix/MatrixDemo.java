package matrix;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class MatrixDemo {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        do {
            printMenu();
            option = readIntInput("Opción: ");
            
            switch (option) {
                case 1 -> showAdj("src/matrices/adyacencia.txt");
                case 2 -> showInc("src/matrices/incidencia.txt");
                case 3 -> showAdj(readPath("Ruta del archivo de adyacencia: "));
                case 4 -> showInc(readPath("Ruta del archivo de incidencia: "));
                case 5 -> enterMatrixManually();
                case 6 -> testID3Algorithm();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (option != 0);
    }

    private static void printMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Mostrar matriz de adyacencia de ejemplo");
        System.out.println("2. Mostrar matriz de incidencia de ejemplo");
        System.out.println("3. Cargar matriz de adyacencia desde archivo");
        System.out.println("4. Cargar matriz de incidencia desde archivo");
        System.out.println("5. Ingresar matriz manualmente");
        System.out.println("6. Probar algoritmo ID3 (Árbol de decisión)");
        System.out.println("0. Salir");
    }

    private static String readPath(String message) {
        SC.nextLine(); // Limpiar buffer
        System.out.print(message);
        return SC.nextLine();
    }

    private static int readIntInput(String message) {
        System.out.print(message);
        while (!SC.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor ingrese un número.");
            SC.next();
            System.out.print(message);
        }
        return SC.nextInt();
    }

    private static void enterMatrixManually() {
        System.out.println("\n=== INGRESO MANUAL DE MATRIZ ===");
        System.out.println("1. Matriz de adyacencia");
        System.out.println("2. Matriz de incidencia");
        int type = readIntInput("Seleccione el tipo de matriz: ");
        
        if (type == 1) {
            enterAdjacencyMatrix();
        } else if (type == 2) {
            enterIncidenceMatrix();
        } else {
            System.out.println("Opción inválida");
        }
    }

    private static void enterAdjacencyMatrix() {
        int n = readIntInput("Número de nodos: ");
        System.out.println("Ingrese la matriz de adyacencia (filas separadas por enter, valores por espacios):");
        
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = readIntInput("Valor [" + i + "][" + j + "]: ");
            }
        }
        
        processMatrix(matrix);
    }

    private static void enterIncidenceMatrix() {
    int n = readIntInput("Número de nodos: ");
    int m = readIntInput("Número de aristas: ");
    System.out.println("Ingrese la matriz de incidencia (filas separadas por enter, valores por espacios):");
    
    int[][] matrix = new int[n][m];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            matrix[i][j] = readIntInput("Valor [" + i + "][" + j + "]: ");
        }
    }
    
    System.out.println("\nMatriz de incidencia ingresada:");
    MatrixReader.printMatrix(matrix);
    
    // Construir árbol directamente desde la matriz de incidencia
    TreeNode root = TreeBuilder.buildTreeFromIncidence(matrix);
    ConsoleTreePrinter.printTreeWithStats(root);
    TreeVisualizer.show(root);
}

    private static void processMatrix(int[][] matrix) {
        System.out.println("\nMatriz ingresada:");
        MatrixReader.printMatrix(matrix);
        
        TreeNode root = TreeBuilder.buildTreeFromAdjacency(matrix);
        ConsoleTreePrinter.printTreeWithStats(root);
        TreeVisualizer.show(root);
    }

    private static void showAdj(String path) {
        try {
            int[][] mat = MatrixReader.readAdjacency(path);
            System.out.println("\nMatriz de adyacencia cargada:");
            MatrixReader.printMatrix(mat);
            
            TreeNode root = TreeBuilder.buildTreeFromAdjacency(mat);
            ConsoleTreePrinter.printTreeWithStats(root);
            TreeVisualizer.show(root);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado - " + path);
        }
    }


    private static void showInc(String path) {
    try {
        int[][] mat = MatrixReader.readIncidence(path);
        System.out.println("\nMatriz de incidencia cargada:");
        MatrixReader.printMatrix(mat);
        
        // Construir árbol con estructura específica
        TreeNode root = TreeBuilder.buildTreeFromIncidence(mat);
        
        System.out.println("\nÁrbol binario construido:");
        ConsoleTreePrinter.printTreeWithStats(root);
        TreeVisualizer.show(root);
        
    } catch (FileNotFoundException e) {
        System.out.println("Error: Archivo no encontrado - " + path);
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    private static void testID3Algorithm() {
        SC.nextLine(); // Limpiar buffer
        
        System.out.println("\n=== CONSTRUCCIÓN DE ÁRBOL DE DECISIÓN ID3 ===");
        
        // Solicitar atributos
        System.out.print("Ingrese los nombres de los atributos (separados por comas): ");
        List<String> attributes = Arrays.stream(SC.nextLine().split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
        
        if (attributes.isEmpty()) {
            System.out.println("Error: Debe ingresar al menos un atributo");
            return;
        }
        
        // Solicitar atributo objetivo
        System.out.print("Ingrese el nombre del atributo objetivo: ");
        String targetAttribute = SC.nextLine().trim();
        
        if (targetAttribute.isEmpty()) {
            System.out.println("Error: Debe ingresar un atributo objetivo");
            return;
        }
        
        // Solicitar ejemplos
        List<Map<String, String>> examples = new ArrayList<>();
        System.out.println("\nIngrese los ejemplos (formato: valor1,valor2,...,valorN,decision)");
        System.out.println("Deje vacío para terminar");
        
        int exampleNum = 1;
        while (true) {
            System.out.print("Ejemplo " + exampleNum++ + ": ");
            String input = SC.nextLine().trim();
            
            if (input.isEmpty()) {
                break;
            }
            
            String[] values = input.split(",");
            if (values.length != attributes.size() + 1) {
                System.out.println("Error: Debe ingresar " + (attributes.size() + 1) + " valores");
                exampleNum--;
                continue;
            }
            
            Map<String, String> example = new LinkedHashMap<>();
            for (int i = 0; i < attributes.size(); i++) {
                example.put(attributes.get(i), values[i].trim());
            }
            example.put(targetAttribute, values[values.length - 1].trim());
            examples.add(example);
        }
        
        if (examples.isEmpty()) {
            System.out.println("Error: No se ingresaron ejemplos");
            return;
        }
        
        // Construir y mostrar el árbol
        buildAndDisplayDecisionTree(examples, attributes, targetAttribute);
    }

    private static void buildAndDisplayDecisionTree(List<Map<String, String>> examples, 
                                                  List<String> attributes, 
                                                  String targetAttribute) {
        System.out.println("\n=== RESULTADOS ===");
        System.out.println("Total de ejemplos: " + examples.size());
        System.out.println("Atributos: " + attributes);
        System.out.println("Atributo objetivo: " + targetAttribute);
        
        ID3Algoritm.DecisionTreeNode root = ID3Algoritm.buildDecisionTree(
            examples, attributes, targetAttribute);
        
        System.out.println("\n=== ÁRBOL DE DECISIÓN ===");
        ID3Algoritm.printVisualDecisionTree(root);
        
        System.out.println("\nProfundidad del árbol: " + 
            ID3Algoritm.calculateTreeDepth(root));
    }
}
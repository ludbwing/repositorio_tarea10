package matrix;

import java.util.*;
import java.util.stream.Collectors;

public class ID3Algoritm {
    public static class DecisionTreeNode {
        String attribute;
        String decision;
        Map<String, DecisionTreeNode> children;
        
        public DecisionTreeNode(String attribute) {
            this.attribute = attribute;
            this.children = new LinkedHashMap<>();
        }
        
        public void setDecision(String decision) {
            this.decision = decision;
        }
        
        public boolean isLeaf() {
            return decision != null;
        }
    }

    public static DecisionTreeNode buildDecisionTree(List<Map<String, String>> examples, 
                                                   List<String> attributes, 
                                                   String targetAttribute) {
        // Caso base 1: Todos los ejemplos son de la misma clase
        if (allExamplesSameClass(examples, targetAttribute)) {
            DecisionTreeNode leaf = new DecisionTreeNode(null);
            leaf.setDecision(examples.get(0).get(targetAttribute));
            return leaf;
        }

        // Caso base 2: No hay más atributos para dividir
        if (attributes.isEmpty()) {
            DecisionTreeNode leaf = new DecisionTreeNode(null);
            leaf.setDecision(getMajorityDecision(examples, targetAttribute));
            return leaf;
        }

        // Seleccionar el mejor atributo para dividir
        String bestAttr = selectBestAttribute(examples, attributes, targetAttribute);
        DecisionTreeNode node = new DecisionTreeNode(bestAttr);
        List<String> remainingAttrs = new ArrayList<>(attributes);
        remainingAttrs.remove(bestAttr);

        // Procesar cada valor del atributo
        List<String> attrValues = getAttributeValues(examples, bestAttr).stream()
            .sorted()
            .collect(Collectors.toList());

        for (String value : attrValues) {
            List<Map<String, String>> subset = filterExamplesByAttribute(examples, bestAttr, value);
                
            if (subset.isEmpty()) {
                DecisionTreeNode leaf = new DecisionTreeNode(null);
                leaf.setDecision(getMajorityDecision(examples, targetAttribute));
                node.children.put(value, leaf);
            } else {
                node.children.put(value, 
                    buildDecisionTree(subset, remainingAttrs, targetAttribute));
            }
        }

        return node;
    }

    public static void printVisualDecisionTree(DecisionTreeNode root) {
        if (root == null) {
            System.out.println("El árbol está vacío");
            return;
        }

        System.out.println("\n=== REPRESENTACIÓN VISUAL DEL ÁRBOL DE DECISIÓN ===");
        printTreeVisual(root, "", true);
        printNodeLegend(root);
    }

    private static void printTreeVisual(DecisionTreeNode node, String prefix, boolean isTail) {
        String nodeStr = node.isLeaf() ? 
            "Decisión: " + capitalizeFirstLetter(node.decision) :
            "Atributo: " + capitalizeFirstLetter(node.attribute);
        
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeStr);

        List<Map.Entry<String, DecisionTreeNode>> entries = new ArrayList<>(node.children.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            String value = entries.get(i).getKey();
            DecisionTreeNode child = entries.get(i).getValue();
            
            System.out.println(prefix + (isTail ? "    " : "│   ") + "    " + 
                capitalizeFirstLetter(value) + ":");
            
            printTreeVisual(child, prefix + (isTail ? "    " : "│   ") + "        ", 
                i == entries.size() - 1);
        }
    }

    private static void printNodeLegend(DecisionTreeNode root) {
        System.out.println("\n=== LEYENDA DEL ÁRBOL ===");
        printDecisionTree(root, "");
    }

    private static boolean allExamplesSameClass(List<Map<String, String>> examples, String targetAttribute) {
        if (examples.isEmpty()) return true;
        String firstDecision = examples.get(0).get(targetAttribute);
        return examples.stream().allMatch(e -> e.get(targetAttribute).equals(firstDecision));
    }

    private static List<Map<String, String>> filterExamplesByAttribute(
        List<Map<String, String>> examples, String attribute, String value) {
        return examples.stream()
            .filter(e -> e.get(attribute).equals(value))
            .collect(Collectors.toList());
    }

    private static String getMajorityDecision(List<Map<String, String>> examples, String targetAttribute) {
        Map<String, Long> decisionCounts = examples.stream()
            .collect(Collectors.groupingBy(
                e -> e.get(targetAttribute), 
                Collectors.counting()));
        
        return Collections.max(decisionCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static String selectBestAttribute(List<Map<String, String>> examples, 
                                           List<String> attributes, 
                                           String targetAttribute) {
        return attributes.stream()
            .max(Comparator.comparingDouble(
                attr -> calculateInformationGain(examples, attr, targetAttribute)))
            .orElse(null);
    }

    private static double calculateInformationGain(List<Map<String, String>> examples, 
                                                String attribute, 
                                                String targetAttribute) {
        double entropyBefore = calculateEntropy(examples, targetAttribute);
        double entropyAfter = 0;
        
        for (String value : getAttributeValues(examples, attribute)) {
            List<Map<String, String>> subset = filterExamplesByAttribute(examples, attribute, value);
            double weight = (double) subset.size() / examples.size();
            entropyAfter += weight * calculateEntropy(subset, targetAttribute);
        }
        
        return entropyBefore - entropyAfter;
    }

    private static double calculateEntropy(List<Map<String, String>> examples, String targetAttribute) {
        if (examples.isEmpty()) return 0;
        
        Map<String, Long> decisionCounts = examples.stream()
            .collect(Collectors.groupingBy(
                e -> e.get(targetAttribute), 
                Collectors.counting()));
        
        double entropy = 0;
        double total = examples.size();
        
        for (long count : decisionCounts.values()) {
            if (count > 0) {
                double probability = count / total;
                entropy -= probability * log2(probability);
            }
        }
        
        return entropy;
    }

    private static List<String> getAttributeValues(List<Map<String, String>> examples, String attribute) {
        return examples.stream()
            .map(e -> e.get(attribute))
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static void printDecisionTree(DecisionTreeNode node, String indent) {
        if (node.isLeaf()) {
            System.out.println(indent + "Decisión: " + capitalizeFirstLetter(node.decision));
            return;
        }
        
        System.out.println(indent + "Atributo: " + capitalizeFirstLetter(node.attribute));
        node.children.forEach((value, child) -> {
            System.out.println(indent + "  Valor: " + capitalizeFirstLetter(value));
            printDecisionTree(child, indent + "    ");
        });
    }

    public static int calculateTreeDepth(DecisionTreeNode node) {
        if (node == null) return 0;
        if (node.isLeaf()) return 1;
        
        return 1 + node.children.values().stream()
            .mapToInt(ID3Algoritm::calculateTreeDepth)
            .max()
            .orElse(0);
    }
}
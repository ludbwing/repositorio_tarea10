package matrix;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizer extends JPanel {
    private final TreeNode root;
    private final int NODE_RADIUS = 20;
    private final int HORIZONTAL_GAP = 50;
    private final int VERTICAL_GAP = 70;

    public TreeVisualizer(TreeNode root) {
        this.root = root;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root != null) {
            drawTree(g, getWidth() / 2, 30, root, HORIZONTAL_GAP * getTreeDepth(root));
        }
    }

    private void drawTree(Graphics g, int x, int y, TreeNode node, int xOffset) {
        g.setColor(Color.BLUE);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
        
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        String label = String.valueOf(node.value + 1);
        g.drawString(label, x - fm.stringWidth(label) / 2, y + fm.getAscent() / 2);

        if (node.left != null) {
            int childX = x - xOffset / 2;
            int childY = y + VERTICAL_GAP;
            g.setColor(Color.BLACK);
            g.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawTree(g, childX, childY, node.left, xOffset / 2);
        }

        if (node.right != null) {
            int childX = x + xOffset / 2;
            int childY = y + VERTICAL_GAP;
            g.setColor(Color.BLACK);
            g.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawTree(g, childX, childY, node.right, xOffset / 2);
        }
    }

    private int getTreeDepth(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getTreeDepth(node.left), getTreeDepth(node.right));
    }

    public static void show(TreeNode root) {
        JFrame frame = new JFrame("Visualización del Árbol");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        TreeVisualizer panel = new TreeVisualizer(root);
        frame.add(new JScrollPane(panel));
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
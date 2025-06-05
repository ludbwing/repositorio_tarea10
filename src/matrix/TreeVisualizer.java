package matrix;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizer extends JPanel {
    private final TreeNode root;
    private final int NODE_SIZE = 30;
    private final int LEVEL_HEIGHT = 60;

    public TreeVisualizer(TreeNode root) {
        this.root = root;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, root, getWidth() / 2, 40, getWidth() / 4);
    }

    private void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
        if (node == null) return;

        g.setColor(Color.BLACK);
        g.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(node.value + 1), x - 6, y + 5);

        if (node.left != null) {
            int childX = x - xOffset;
            int childY = y + LEVEL_HEIGHT;
            g.setColor(Color.BLACK);
            g.drawLine(x, y, childX, childY);
            drawTree(g, node.left, childX, childY, xOffset / 2);
        }

        if (node.right != null) {
            int childX = x + xOffset;
            int childY = y + LEVEL_HEIGHT;
            g.setColor(Color.BLACK);
            g.drawLine(x, y, childX, childY);
            drawTree(g, node.right, childX, childY, xOffset / 2);
        }
    }

    public static void show(TreeNode root) {
        JFrame frame = new JFrame("Árbol Binario");
        TreeVisualizer panel = new TreeVisualizer(root);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

package matrix;

public class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    public TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value + 1);
    }
}
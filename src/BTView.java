import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BTView extends Pane {
    private BST<Integer> tree = new BST<>();
    private double radius = 15;
    private double vGap = 50;


    BTView(BST<Integer> tree) {
        this.tree = tree;
        setStatus("Tree is empty");
    }

    public void setStatus(String msg) {
        this.getChildren().add(new Text(20, 20, msg));
    }

    /**
     * Handle the displayed message when an exception occurs
     * @param exception the handled exception
     */
    public void errorMessage(Exception exception){
        String exSimpleName = exception.getClass().getSimpleName();
        if(exSimpleName.equals("NumberFormatException")) {
            this.getChildren().add(new Text(20, 20, "Key must be an integer!"));
        }
        else{
            this.getChildren().add(new Text(20, 20, "There is something wrong. Please try again!"));
        }
    }

    public void displayTree() {
        this.getChildren().clear();
        if (tree.getRoot() != null) {
            displayTree(tree.getRoot(), getWidth() / 2, vGap,
                    getWidth() / 4);
        }
    }

    /** Display a subtree rooted at position (x, y) */
    private void displayTree(BST.TreeNode<Integer> root,
                             double x, double y, double hGap) {
        if (root.left != null) {
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
            displayTree(root.left, x - hGap, y + vGap, hGap / 2);
        }

        if (root.right != null) {
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            displayTree(root.right, x + hGap, y + vGap, hGap / 2);
        }

        
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.WHITE);
        if(this.tree.searchPath != null) {
            if (this.tree.searchPath.contains(root)) {
                circle.setFill(Color.ORANGE);
            }
        }
        circle.setStroke(Color.BLACK);
        this.getChildren().addAll(circle,
                new Text(x - 4, y + 4, root.element.toString()));
    }
}

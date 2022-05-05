import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.*;

public class BSTAnimation extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        BST<Integer> tree = new BST<>(); // Create a tree

        BorderPane pane = new BorderPane();
        BTView view = new BTView(tree); // Create a View
        pane.setCenter(view);

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        // Create all the possible buttons
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");
        Button btSearch = new Button("Search");
        Button btInorder = new Button("Inorder");
        Button btPreorder = new Button("Preorder");
        Button btPostorder = new Button("Postorder");
        Button btBreadthFirst = new Button("Breadth-first");
        Button btHeight = new Button("Height");
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(new Label("Enter a key: "),
                tfKey, btInsert, btDelete, btSearch, btInorder, btPreorder, btPostorder, btBreadthFirst, btHeight);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        /** Action : Insert */
        btInsert.setOnAction(e -> {
            try {
                int key = Integer.parseInt(tfKey.getText());
                if (tree.search(key)) { // key is in the tree already
                    view.displayTree();   // Clears the old status message
                    view.setStatus(key + " is already in the tree");
                } else {
                    tree.insert(key); // Insert a new key
                    view.displayTree();
                    view.setStatus(key + " is inserted in the tree");
                }
            }
            catch (Exception ex){
                view.displayTree();
                view.errorMessage(ex);;
            }
        });

        /** Action : Delete */
        btDelete.setOnAction(e -> {
            try {
                int key = Integer.parseInt(tfKey.getText());
                if (!tree.search(key)) { // key is not in the tree
                    view.displayTree();    // Clears the old status message
                    view.setStatus(key + " is not in the tree");
                } else {
                    if (tree.searchPath != null) {
                        // Keep track that if the key existed in the search path
                        boolean isFoundInSearchPath = false;
                        int lengthSearchPath = tree.searchPath.size();
                        for(int i = 0; i < lengthSearchPath; i++){
                            if(tree.searchPath.get(i).element.equals(key)){
                                isFoundInSearchPath = true;
                                break;
                            }
                        }
                        // If the key exists in the search path, clear search path before delete the key
                        if(isFoundInSearchPath){
                            tree.searchPath = null;
                        }
                    }
                    tree.delete(key); // Delete a key
                    view.displayTree();
                    view.setStatus(key + " is deleted from the tree");
                }
            }
            catch (Exception ex){
                view.displayTree();
                view.errorMessage(ex);;
            }
        });

        /** Action : Search */
        btSearch.setOnAction(e -> {
            try {
                int key = Integer.parseInt(tfKey.getText());
                if(tree.search(key)){
                    tree.path(key);
                    view.displayTree();
                    view.setStatus("Found " + key + " in tree");
                }
                else{
                    view.displayTree();
                    view.setStatus(key + " is not in the tree");
                }
            }
            catch (Exception ex){
                view.displayTree();
                view.errorMessage(ex);;
            }
        });

        /** Action : Inorder */
        btInorder.setOnAction(e -> {
            if(tree.size() <= 0){
                view.displayTree();
                view.setStatus("Tree is empty");
            }
            else {
                List<Integer> inorderList = tree.inorderList();
                String sMsg = "Inorder traversal: " + inorderList.toString();
                view.displayTree();
                view.setStatus(sMsg);
            }
        });

        /** Action : Preorder */
        btPreorder.setOnAction(e -> {
            if(tree.size() <= 0){
                view.displayTree();
                view.setStatus("Tree is empty");
            }
            else {
                List<Integer> preorderList = tree.preorderList();
                String sMsg = "Preorder traversal: " + preorderList.toString();
                view.displayTree();
                view.setStatus(sMsg);
            }
        });

        /** Action : Postorder */
        btPostorder.setOnAction(e -> {
            if(tree.size() <= 0){
                view.displayTree();
                view.setStatus("Tree is empty");
            }
            else {
                List<Integer> postorderList = tree.postorderList();
                String sMsg = "Postorder traversal: " + postorderList.toString();
                view.displayTree();
                view.setStatus(sMsg);
            }
        });

        /** Action : Breadth-first*/
        btBreadthFirst.setOnAction(e -> {
            if(tree.size() <= 0){
                view.displayTree();
                view.setStatus("Tree is empty");
            }
            else {
                List<Integer> breadthFirstList = tree.breadthFirstOrderList();
                String sMsg = "Breadth-first traversal: [";
                int lengthBreadthFirstList = breadthFirstList.size();
                for (int i = 0; i < lengthBreadthFirstList; i++) {
                    if (i != lengthBreadthFirstList - 1) {
                        sMsg += breadthFirstList.get(i) + ", ";
                    } else {
                        sMsg += breadthFirstList.get(i) + "]";
                    }
                }
                view.displayTree();
                view.setStatus(sMsg);
            }
        });

        /** Action : Height */
        btHeight.setOnAction(e -> {
            view.displayTree();
            view.setStatus("Tree height is " + tree.height());
        });

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 900, 500);
        primaryStage.setTitle("BSTAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


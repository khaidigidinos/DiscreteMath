import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DFS extends Application {
	Pane center = new Pane();
	BorderPane pane = new BorderPane();
	Text infoInit = new Text(20, 20, "Please input size for the map!");
	Text log = new Text(500, 100, "DFS: we will try every path in a proper manner to find the maximum sum.");
	List<Circle> path = new ArrayList<>();
	int maxSum = Integer.MIN_VALUE;
	
	Circle[][] circles;
	Map<Circle, Text> map = new HashMap<>();
	
	int row, col;
	{
		pane.getChildren().add(infoInit);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		init();
		
		Scene scene = new Scene(pane);
		primaryStage.setFullScreen(true);
        primaryStage.setTitle("Map");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void init() {
		Label lRow = new Label("Enter number of rows (max 8): ");
		Label lCol = new Label("Enter number of columns (max 8): ");
		
		TextField tRow = new TextField("");
		TextField tCol = new TextField("");
		
		Button start = new Button("Start creating map!");
		start.setOnAction(e -> {
			try {
				row = Integer.parseInt(tRow.getText());
				col = Integer.parseInt(tCol.getText());
				pane.getChildren().clear();
				
				infoInit.setText("");
				pane.getChildren().add(infoInit);
				initMap();
			} catch (NumberFormatException ex) {
				infoInit.setText("Please enter integers only!");
			}
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(lRow, tRow, lCol, tCol, start);
		
		pane.setBottom(hb);
	}
	
	public void initMap() {
		circles = new Circle[row][col];
		pane.setCenter(center);
		
		Label lRow = new Label("Row: ");
		Label lCol = new Label("Col: ");
		Label lValue = new Label("Value (from 0 to 9 for simplicity): ");
		
		TextField tRow = new TextField();
		TextField tCol = new TextField();
		TextField tValue = new TextField();
		
		Button update = new Button("Update");
		Button dfsMax = new Button("Find max");
		Button reset = new Button("Reset");
		
		int x = 100, y = 100, radius = 15;
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				Circle circle = new Circle(y, x, radius);
				circle.setFill(Color.WHITE);
				circle.setStroke(Color.BLACK);
				circles[i][j] = circle;
				
				Text text = new Text(y - 4, x + 4, "0");
				center.getChildren().addAll(circle, text);
				map.put(circle, text);
				
				y += 50;
			}
			x += 50;
			y = 100;
		}
		
		dfsMax.setOnAction(e -> {
			path = new ArrayList<>();
			maxSum = Integer.MIN_VALUE;
			for(Circle[] cc : circles) {
				for(Circle c : cc) c.setFill(Color.WHITE);
			}
			
			List<Circle> list = new ArrayList<>();
			list.add(circles[0][0]);
			dfs(0, 0 , list, Integer.parseInt(map.get(circles[0][0]).getText()));
			
			for(Circle c : path) {
				c.setFill(Color.ORANGE);
			}
			
			infoInit.setText("Max sum: " + maxSum);
		});
		
		update.setOnAction(e -> {
			try {
				int nr = Integer.parseInt(tRow.getText());
				int nc = Integer.parseInt(tCol.getText());
				int nv = Integer.parseInt(tValue.getText());
				
				map.get(circles[nr][nc]).setText("" + nv);
			} catch (Exception ex) {
				infoInit.setText(ex.getMessage());
			}
		});
		
		reset.setOnAction(e -> {
			log.setText("");
			infoInit.setText("");
			for(Circle[] cc : circles) {
				for(Circle c : cc) {
					 map.get(c).setText("0");
					 c.setFill(Color.WHITE);
				}
			}
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(lRow, tRow, lCol, tCol, lValue, tValue, update, dfsMax, reset);
		
		pane.setBottom(hb);
		pane.getChildren().add(log);
	}
	
	private void dfs(int i, int j, List<Circle> list, int sum) {
		if(i == row - 1 && j == col - 1) {
			if(sum > maxSum) {
				maxSum = sum;
				path = new ArrayList<>(list);
			}
			return;
		}
		
		if(i + 1 < row) {
			list.add(circles[i + 1][j]);
			dfs(i + 1, j, list, sum + Integer.parseInt(map.get(circles[i + 1][j]).getText()));
			list.remove(list.size() - 1);
		}
		
		if(j + 1 < col) {
			list.add(circles[i][j + 1]);
			dfs(i, j + 1, list, sum + Integer.parseInt(map.get(circles[i][j + 1]).getText()));
			list.remove(list.size() - 1);
		}
	}
	
	public static void main(String... args) {
		launch(args);
	}
}

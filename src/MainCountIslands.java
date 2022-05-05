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

public class MainCountIslands extends Application {
	Pane center = new Pane();
	BorderPane pane = new BorderPane();
	Text infoInit = new Text(20, 20, "Please input size for the map!");
	Text log = new Text(500, 100, "");
	Circle[][] map;
	boolean[][] visit;
	int row, col, count;
	private int[][] dirs = new int[][] {
		{1, 0},
		{-1, 0},
		{0, 1},
		{0, -1}
	};
	
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
		pane.setCenter(center);
		
		map = new Circle[row][col];
		visit = new boolean[row][col];
		
		int x = 100, y = 100, radius = 15;
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				Circle water = new Circle(y, x, radius);
				water.setFill(Color.CORNFLOWERBLUE);
				water.setStroke(Color.BLACK);
				center.getChildren().add(water);
				map[i][j] = water;
				y += 50;
				
				water.setOnMouseClicked(e -> {
					water.setFill((Color)water.getFill() == Color.CORNFLOWERBLUE ? Color.GREENYELLOW : Color.CORNFLOWERBLUE);
				});
			}
			x += 50;
			y = 100;
		}
		
		Button dfsCountIslands = new Button("Count islands");
		Button reset = new Button("Reset");
		
		dfsCountIslands.setOnAction(e -> {
			try {
				countIslands();
				infoInit.setText("Number of islands: " + count);
			} catch (Exception ex) {}
		});
		
		reset.setOnAction(e -> {
			log.setText("");
			infoInit.setText("");
			visit = new boolean[row][col];
			count = 0;
			for(Circle[] c : map) {
				for(Circle w : c) {
					w.setFill(Color.CORNFLOWERBLUE);
				}
			}
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(dfsCountIslands, reset);
		
		pane.setBottom(hb);
		pane.getChildren().add(log);
	}
	
	private void countIslands() throws Exception {
		count = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(!visit[i][j] && map[i][j].getFill() == Color.GREENYELLOW) {
					count++;
					log.setText(log.getText() + "\nWe have found a new island starting at (" + i + ", " + j + ").");
					visit[i][j] = true;
					dfs(i, j);
					log.setText(log.getText() + "\nWe have explored the whole island!\n");
				}
			}
		}
		
		log.setText(log.getText() + "\nWe have explored the whole area!\nClick reset to build new map or you will get error!");
	}
	
	private void dfs(int i, int j) {
		map[i][j].setFill(Color.SANDYBROWN);
		log.setText(log.getText() + "\nWe are on (" + i + ", " + j + ").");
		for(int c = 0; c < 4; c++) {
			int ni = i + dirs[c][0];
			int nj = j + dirs[c][1];
			if(ni >= 0 && nj >= 0 && ni < row && nj < col && !visit[ni][nj] && map[ni][nj].getFill() == Color.GREENYELLOW) {
				visit[ni][nj] = true;
				dfs(ni, nj);
			} 
		}
	}
	
	public static void main(String... args) {
		launch(args);
	}
}

import java.util.*;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BFS extends Application {
	Pane center = new Pane();
	BorderPane pane = new BorderPane();
	Text infoInit = new Text(20, 20, "Please input size for the map!");
	Text log = new Text(500, 100, "You can click on each square to turn it into: black (obstacle), white (empty space) or yellow (starting point).\nThere will be only one starting point. You can only set the goal point through below textboxes and buttons.\nOnly one goal is allowed. After you try to find the shortest way, please click 'Reset' to start over or there\nwill be some errors.");
	int row, col;
	
	Rectangle[][] rects;
	int[] start = new int[] {-1, -1}, goal = new int[] {-1, -1};
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		init();
		
		Scene scene = new Scene(pane);
		primaryStage.setFullScreen(true);
        primaryStage.setTitle("Map");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	@Override
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
		rects = new Rectangle[row][col];
		pane.setCenter(center);
		
		Label lRow = new Label("Row: ");
		Label lCol = new Label("Col: ");
		
		Button setGoal = new Button("Set goal");
		Button bfs = new Button("Find path");
		Button reset = new Button("Reset");
		
		TextField tRow = new TextField();
		TextField tCol = new TextField();
		
		int x = 100, y = 100;
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				Rectangle rec = new Rectangle(y, x, 50, 50);
				rec.setFill(Color.WHITE);
				rec.setStroke(Color.BLACK);
				center.getChildren().addAll(rec);
				
				int pi = i;
				int pj = j;
				rec.setOnMouseClicked(e -> {
					Color original = (Color)rec.getFill();
					if(original == Color.WHITE) {
						rec.setFill(Color.BLACK);
					} else if(original == Color.BLACK) {
						rec.setFill(Color.YELLOW);
						start[0] = pi;
						start[1] = pj;
						for(Rectangle[] rr : rects) {
							for(Rectangle r : rr) {
								if(!r.equals(rec) && (Color)r.getFill() == Color.YELLOW) {
									r.setFill(Color.WHITE);
								}
							}
						}
					} else if(original == Color.YELLOW){
						rec.setFill(Color.WHITE);
					} else {}
				});
				rects[i][j] = rec;
				y += 50;
			}
			x += 50;
			y = 100;
		}
		
		setGoal.setOnAction(e -> {
			try {
				int ir = Integer.parseInt(tRow.getText());
				int ic = Integer.parseInt(tCol.getText());
				
				rects[ir][ic].setFill(Color.INDIANRED);
				
				Rectangle rec = rects[ir][ic];
				
				for(Rectangle[] rr : rects) {
					for(Rectangle r : rr) {
						if(!r.equals(rec) && (Color)r.getFill() == Color.INDIANRED) {
							r.setFill(Color.WHITE);
						}
					}
				}
				
				goal[0] = ir;
				goal[1] = ic;
				
				if(goal[0] == start[0] && goal[1] == start[1]) {
					start = new int[] { -1, -1 };
				}
			} catch (Exception ex) {
				infoInit.setText(ex.getMessage());
			}
		});
		
		bfs.setOnAction(e -> {
			infoInit.setText("");
			if(start[0] == -1 || start[1] == -1) {
				infoInit.setText("Please specify starting point");
				return;
			}
			
			if(goal[0] == -1 || goal[1] == -1) {
				infoInit.setText("Please specify goal point");
				return;
			}
			
			bfs();
		});
		
		reset.setOnAction(e -> {
			infoInit.setText("");
			start = new int[] {-1, -1};
			goal = new int[] {-1, -1};
			for(Rectangle[] rr : rects) {
				for(Rectangle r : rr) {
					r.setFill(Color.WHITE);
				}
			}
		});
		
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(lRow, tRow, lCol, tCol, setGoal, bfs, reset);
		
		pane.setBottom(hb);
		pane.getChildren().add(log);
	}
	
	private void bfs() {
		boolean reach = false;
		boolean[][] v = new boolean[row][col];
		v[start[0]][start[1]] = true;
		
		int[][] dirs = new int[][] {
			{1, 0}, {0, 1}, {-1, 0}, {0, -1}
		};
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { start[0], start[1] });
		List<List<int[]>> list = new ArrayList<>();
		begin : while(!q.isEmpty()) {
			int size = q.size();
			list.add(new ArrayList<>());
			for(int i = 0; i < size; i++) {
				list.get(list.size() - 1).add(q.poll());
			}
			
			for(int[] arr : list.get(list.size() - 1)) {
				if(arr[0] == goal[0] && arr[1] == goal[1]) {
					reach = true;
					break begin;
				} else {
					for(int i = 0; i < 4; i++) {
						int ni = arr[0] + dirs[i][0];
						int nj = arr[1] + dirs[i][1];
						
						if(ni >= 0 && nj >= 0 && ni < row && nj < col && !v[ni][nj] && ((Color)rects[ni][nj].getFill() == Color.WHITE || (Color)rects[ni][nj].getFill() == Color.INDIANRED)) {
							v[ni][nj] = true;
							q.add(new int[] { ni, nj });
						}
					}
				}
			}
		}
		
		if(reach) {
			int[] last = new int[] { goal[0], goal[1] };
			for(int i = list.size() - 2; i > 0; i--) {
				inner: for(int j = 0; j < list.get(i).size(); j++) {
					int r = list.get(i).get(j)[0];
					int c = list.get(i).get(j)[1];
					Rectangle curr = rects[r][c];
					
					for(int k = 0; k < 4; k++) {
						int nr = dirs[k][0] + r;
						int nc = dirs[k][1] + c;
						if(nr >= 0 && nc >= 0 && nr < row && nc < col && last[0] == nr && last[1] == nc) {
							last = new int[] { r, c };
							curr.setFill(Color.ORANGE);
							break inner;
						}
					}
					
				}
			}
			
		}
		else {
			infoInit.setText("There is no path to reach the goal!");
		}
	}
	
	public static void main(String... args) {
		launch(args);
	}
}

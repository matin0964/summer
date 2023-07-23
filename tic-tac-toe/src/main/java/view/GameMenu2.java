package view;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMenu2 extends Application {
    private boolean turn = true;
    private int[][] board = new int[3][3];
    private int cnt = 0;
    private double thickness = 5;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(600, 600);
        GridPane grid = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Pane pane = new Pane();
                pane.setPrefSize(100, 100);
                grid.add(pane, i, j);
                int m = i;
                int n = j;
                pane.setOnMouseClicked(event -> {
                    if (!pane.getChildren().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("This cell is already occupied");
                        alert.showAndWait();
                    } else {
                        int[][] ansLine = getLine(pane, m, n, board, turn, cnt);
                        cnt++;
                        turn = !turn;
                        if (ansLine != null) {
                            if (board[ansLine[0][0]][ansLine[0][1]] == 1) {
                                getLine(stage, root, grid, ansLine, 1);
                            } else if (board[ansLine[0][0]][ansLine[0][1]] == 2) {
                                getLine(stage, root, grid, ansLine, 2);
                            }
                        } else {
                            GameMenu.drawMessage(stage, cnt);
                        }
                    }
                });
            }
        }
        GameMenu.setGridPane(stage, root, grid);
    }

    static int[][] getLine(Pane pane, int m, int n,int[][] board,boolean turn,int cnt) {
        Text text = new Text();
        text.setLayoutX(40);
        text.setLayoutY(60);
        text.setFont(javafx.scene.text.Font.font(50));
        if (turn) {
            text.setText("X");
            text.setFill(Color.RED);
            board[m][n] = 1;
        } else {
            text.setText("O");
            text.setFill(Color.GREEN);
            board[m][n] = 2;
        }
        pane.getChildren().add(text);
        int[][] ansLine = line(board);
        return ansLine;
    }


    private void getLine(Stage stage, Pane root, GridPane grid, int[][] ansLine, int number) {
        Line line = new Line();
        if (number == 1)
            line.setStroke(Color.RED);
        else
            line.setStroke(Color.GREEN);
        line.setStrokeWidth(thickness);
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null) {
                if (GridPane.getColumnIndex(node) == ansLine[0][0] && GridPane.getRowIndex(node) == ansLine[0][1]) {
                    line.setEndX(node.getLayoutX() + 190);
                    line.setEndY(node.getLayoutY() + 190);
                }
                if (GridPane.getColumnIndex(node) == ansLine[2][0] && GridPane.getRowIndex(node) == ansLine[2][1]) {
                    line.setStartX(node.getLayoutX() + 190);
                    line.setStartY(node.getLayoutY() + 190);
                }
            }
        }
        root.getChildren().add(line);
        GameMenu.showWinner(number, stage);
        stage.close();
    }

    private static int[][] line(int[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                int[] ansS = {i, 0};
                int[] ansM = {i, 1};
                int[] ansE = {i, 2};
                int[][] ans = {ansS, ansM, ansE};
                return ans;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                int[] ansS = {0, i};
                int[] ansM = {1, i};
                int[] ansE = {2, i};
                int[][] ans = {ansS, ansM, ansE};
                return ans;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            int[] ansS = {0, 0};
            int[] ansM = {1, 1};
            int[] ansE = {2, 2};
            int[][] ans = {ansS, ansM, ansE};
            return ans;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            int[] ansS = {0, 2};
            int[] ansM = {1, 1};
            int[] ansE = {2, 0};
            int[][] ans = {ansS, ansM, ansE};
            return ans;
        }
        return null;
    }
}

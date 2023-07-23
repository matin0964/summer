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

public class GameMenu extends Application {
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
                        Text text = new Text();
                        text.setLayoutX(40);
                        text.setLayoutY(60);
                        text.setFont(javafx.scene.text.Font.font(50));
                        if (turn) {
                            text.setText("X");
                            text.setFill(Color.RED);
                            turn = false;
                            board[m][n] = 1;
                            cnt++;
                        } else {
                            text.setText("O");
                            text.setFill(Color.GREEN);
                            turn = true;
                            board[m][n] = 2;
                            cnt++;
                        }
                        pane.getChildren().add(text);
                        int[][] ansLine = line();
                        if (ansLine != null) {
                            if (board[ansLine[0][0]][ansLine[0][1]] == 1) {
//                                getLine(stage, root, grid, ansLine, 1);
                                highLight(grid, ansLine, 1, stage);
                            } else if (board[ansLine[0][0]][ansLine[0][1]] == 2) {
//                                getLine(stage, root, grid, ansLine, 2);
                                highLight(grid, ansLine, 2, stage);
                            }
                        } else {
                            if (cnt == 9) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Game Over");
                                alert.setHeaderText("Game Over");
                                alert.setContentText("Draw");
                                alert.showAndWait();
                                stage.close();
                            }
                        }
                    }
                });
            }
        }
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(true);
        root.getChildren().add(grid);
        grid.setLayoutX(140);
        grid.setLayoutY(140);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static void highLight(GridPane grid, int[][] ansLine, int number, Stage stage) {
        int cnt = 0;
        for (Node node : grid.getChildren()) {
            if ((GridPane.getColumnIndex(node) == ansLine[0][0] && GridPane.getRowIndex(node) == ansLine[0][1]) || (GridPane.getColumnIndex(node) == ansLine[1][0] && GridPane.getRowIndex(node) == ansLine[1][1]) || (GridPane.getColumnIndex(node) == ansLine[2][0] && GridPane.getRowIndex(node) == ansLine[2][1])) {
                BackgroundFill fill = null;
                if (number == 1) {
                    fill = new BackgroundFill(Color.PINK, null, null);
                } else {
                    fill = new BackgroundFill(Color.PALEGREEN, null, null);
                }
                Background background = new Background(fill);
                ((Pane) node).setBackground(background);
                cnt++;
                if (cnt == 3)
                    break;
            }
        }
        showWinner(number, stage);
    }

    private static void showWinner(int number, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Over");
        if (number == 2)
            alert.setContentText("O wins");
        else if (number == 1)
            alert.setContentText("X wins");
        alert.showAndWait();
        try {
            new MainMenu().start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private int[][] line() {
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

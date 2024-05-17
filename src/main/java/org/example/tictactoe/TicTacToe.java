package org.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application { //The Application class is the entry point for JavaFX applications

    private Button buttons[][] = new Button[3][3];// 2D Array of size 3*3 bcz we need 9 blocks
    private Label playerXScoreLabel, playerOScoreLabel; // Label - non-editable text : show label on screen
    private int playerXScore = 0, playerOScore = 0; // Variables to show score of both player
    private boolean playerXTurn = true; // start the game with playerX, that's why it is boolean 'true'
    private BorderPane createContent(){ // Borderpane used to place nodes at left,right,center,top and bottom position
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));// padding
        //Title
        Label titleLabel = new Label("Tic Tac Toe"); // name of the game : use 'Label' component
        titleLabel.setStyle("-fx-font-size : 35pt; -fx-font-weight : bold;"); //styling
        root.setTop(titleLabel); // set on Top of the screen
        BorderPane.setAlignment(titleLabel, Pos.CENTER);//Title shift to center

        //Game Board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // horizontal gap among columns
        gridPane.setVgap(10); // vertical gap among rows
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++) { // 3 rows
            for (int j = 0; j < 3; j++) { // 3 cols
                Button button = new Button(); // Button is created in Grid
                button.setPrefSize(100,100); // size of Button
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;"); // Assigned Char on Button's size and Bold.
                button.setOnAction(event-> buttonClicked(button));
                buttons[i][j] = button;//created button will store as type of Button class
                gridPane.add(button,j,i);//button,colNo, rowNo
            }
        }
        root.setCenter(gridPane); // gridPane setup in center of BorderPane
        BorderPane.setAlignment(gridPane,Pos.CENTER); // gridpane position is fixed at center in BorderPane

        //Score : Hbox use to place children in single horizontal row
        HBox scoreBoard = new HBox(20); // 20 is spacing
        scoreBoard.setAlignment(Pos.CENTER);//Score of X and O will show at Center of screen
        playerXScoreLabel = new Label("Player X : 0");
        playerXScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        playerOScoreLabel = new Label("Player 0 : 0");
        playerOScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        scoreBoard.getChildren().addAll(playerXScoreLabel,playerOScoreLabel); // adding child in HBox row
        root.setBottom(scoreBoard); // set at bottom screen

        return root;

    }

    private void buttonClicked(Button button){ // Action perform when Player Tap the Button
        if(button.getText().equals("")){
            if(playerXTurn){
                button.setText("X");
                //button.setTextFill(Color.BLUEVIOLET);
            }
            else{
                button.setText("O");
                //button.setTextFill(Color.PURPLE);
            }
            playerXTurn = !playerXTurn; //true ~ false and false ~ true -> one by one player will play the game
            checkWinner(); // check winner
        }
        return;
    }

    private void checkWinner(){ // Player will Win, if entire row or col or diagonal has same character
        //row
        for(int row = 0; row < 3; row++){
            if(buttons[row][0].getText().equals(buttons[row][1].getText()) &&
               buttons[row][1].getText().equals(buttons[row][2].getText()) &&
               !buttons[row][0].getText().isEmpty()){
                //you are a winner
                String winner = buttons[row][0].getText(); // getText is a function : bring 'player's Mark either 'X' Or 'O'
                updateScore(winner); // if player win : count++
                showWinnerDialog(winner); // popUp will come for WINNER
                resetBoard(); // All buttons will reset as blank block
                return; // return - because if 1 of 3 winning conditions is satisfied, no need to check other 2 conditions
            }
        }
        //col
        for(int col = 0; col < 3; col++){
            if(buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[1][col].getText().equals(buttons[2][col].getText()) &&
                    !buttons[0][col].getText().isEmpty()){
                //you are a winner
                String winner = buttons[0][col].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }
        //diagonal - 1st diagonal
        if(buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()){
            //you are a winner
            String winner = buttons[0][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        //2nd diagonal
        if(buttons[2][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[0][2].getText()) &&
                !buttons[2][0].getText().isEmpty()){
            //you are a winner
            String winner = buttons[2][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        //tie condition
        boolean tie = true;
        for(Button row[] : buttons){
            for(Button button : row){
                if(button.getText().equals("")){ // All buttons are totally filled by X or O
                    tie = false;
                    break;
                }
            }
        }

        if(tie){
            showTieDialog();
            resetBoard();
        }
    }
    private void showWinnerDialog(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // To popUp winner, Alert class has been used
        alert.setTitle("Winner"); // .setTitle : to show Title
        alert.setContentText("Congratulation "+ winner + "! You Won the Game");
        alert.setHeaderText("");
        alert.showAndWait(); // .showAndWait - popUp came and wait on Screen
    }

    private void showTieDialog(){ // Alert use if game is Tie
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setContentText("Game is Over ! It's a Tie !");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void updateScore(String winner){ // Winner score will ++ after victory
        if(winner.equals("X")){
            playerXScore++;
            playerXScoreLabel.setText("Player X : "+playerXScore);
        }else{
            playerOScore++;
            playerOScoreLabel.setText("Player O : "+playerOScore);
        }
    }

    private void resetBoard(){ //2D array's each cell will be empty for NestedLoop
        for(Button row[] : buttons){
            for(Button button : row){
                button.setText("");
            }
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent()); // Every Component will be child in Scene
        stage.setTitle("Tic Tac Toe"); // Game title display on 'stage' -> top left corner
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
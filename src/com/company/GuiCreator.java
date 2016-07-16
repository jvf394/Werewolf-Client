package com.company;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EventListener;

public class GuiCreator {

    private static String playerName, name = "";
    private static int numberOfPlayers;

    public static String usernameGui(String isFirst) {
        Stage usernameStage = new Stage();
        usernameStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text usernameTxt = new Text("Username:");
        TextField usernameField = new TextField("Username");
        Text numberOfPlayersTxt = new Text("Number of Players:");
        TextField numberOfPlayersField = new TextField("1");
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {
            playerName = usernameField.getText();
            numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());
            if (isFirst.equals("1")) {
                if ((playerName != null) && (!playerName.equals(""))) {
                    if ((numberOfPlayers > 1) && (numberOfPlayers < 11)) {
                        name = playerName + "," + numberOfPlayers;
                    }
                }
            } else if ((playerName != null) && (!playerName.equals(""))) {
                name = playerName;
            }
            usernameStage.close();
        });
        grid.add(usernameTxt, 0, 0);
        grid.add(usernameField, 1, 0);
        usernameStage.setTitle("Client");
        if (isFirst.equals("1")) {
            usernameStage.setTitle("Host");
            grid.add(numberOfPlayersTxt, 0, 1);
            grid.add(numberOfPlayersField, 1, 1);
        }
        grid.add(submitBtn, 0, 2);
        Scene usernameScene = new Scene(grid);
        usernameStage.setScene(usernameScene);
        usernameStage.showAndWait();
        return name;
    }

    public static void waitingGui() {
        Stage waitingStage = new Stage();
        waitingStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text waitingTxt = new Text("waiting...");
        Button submitBtn = new Button("Ok");
        submitBtn.setOnAction(event -> {

            waitingStage.close();
        });
        grid.add(waitingTxt, 0, 0);
        waitingStage.setTitle("Client");
        grid.add(submitBtn, 0, 2);
        Scene usernameScene = new Scene(grid);
        waitingStage.setScene(usernameScene);
        waitingStage.showAndWait();
    }

    private static String deckStr;

    public static String characterSelectionGui() {
        Stage characterSelectionStage = new Stage();
        characterSelectionStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        //grid.setStyle("-fx-background-image: url('0.png');-fx-background-position: center center;-fx-background-repeat: stretch");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.minWidth(200);
        String[] characterNames = {"Doppelganger", "Werewolf", "Werewolf", "Minion", "Mason", "Mason", "Seer", "Robber", "Troublemaker", "Drunk", "Insomniac", "Hunter", "Tanner", "Villager", "Villager", "Villager"};
        ArrayList<Button> acceptBtns = new ArrayList();
        ArrayList<Button> rejectBtns = new ArrayList();
        ArrayList<Text> status = new ArrayList();

        final int[] numPlayers = {0};
        Text question = new Text("Which of these will be playing?");
        Text players = new Text("Number of Players: " + numPlayers[0]);
        Button continueBtn = new Button("Continue");
        grid.add(question, 0, 0, 2, 1);
        grid.add(players, 2, 0, 2, 1);
        grid.add(continueBtn, 3, 0);
        continueBtn.setOnAction(event1 -> {
            if (numberOfPlayers == numPlayers[0]) {
                if (status.get(4).getText().equals(status.get(5).getText())) {
                    deckStr = "";
                    for (int i = 0; i < 16; i++) {
                        if (status.get(i).getText().equals("yes")) {
                            deckStr += i + ",";
                        }
                    }
                    deckStr = deckStr.substring(0, deckStr.length() - 1);
                    String[] deck = deckStr.split(",");
                    Collections.shuffle(Arrays.asList(deck));
                    deckStr = Arrays.toString(deck);
                    deckStr = deckStr.substring(1, deckStr.length() - 1);
                    deckStr = deckStr.replace(" ", "");
                    characterSelectionStage.close();
                }
            }
        });
        int count = 0;
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 8; c++) {
                Text statusTxt = new Text("no");
                VBox vContainer = new VBox();
                HBox hContainer = new HBox();
                status.add(statusTxt);
                Button yesBtn = new Button("Yes");
                yesBtn.minWidth(2000);
                yesBtn.setOnAction(event -> {
                    System.out.println(characterNames[acceptBtns.indexOf(yesBtn)]);
                    if (status.get(acceptBtns.indexOf(yesBtn)).getText().equalsIgnoreCase("no")) numPlayers[0]++;
                    players.setText("Number of Players: " + numPlayers[0]);
                    vContainer.setStyle("-fx-background-color: #00CC00; -fx-border-color: #000000;");
                    status.get(acceptBtns.indexOf(yesBtn)).setText("yes");
                    //characterSelectionStage.showAndWait();
                });
                Button noBtn = new Button("No");
                noBtn.minWidth(2000);
                noBtn.setOnAction(event -> {
                    System.out.println(characterNames[rejectBtns.indexOf(noBtn)]);
                    if (status.get(rejectBtns.indexOf(noBtn)).getText().equalsIgnoreCase("yes")) numPlayers[0]--;
                    players.setText("Number of Players: " + numPlayers[0]);
                    vContainer.setStyle("-fx-background-color: #DD0000; -fx-border-color: #000000;");
                    status.get(rejectBtns.indexOf(noBtn)).setText("no");
                });
                acceptBtns.add(yesBtn);
                rejectBtns.add(noBtn);
                Image cardImg = new Image(count + ".png", 75, 548, true, true);
                ImageView imageView = new ImageView(cardImg);
                vContainer.setPadding(new Insets(5, 5, 5, 5));
                vContainer.minWidth(200);
                vContainer.setStyle("-fx-background-color: #DD0000; -fx-border-color: #000000;");
                if (count == 1 || count == 2 || count == 3)
                    vContainer.setStyle("-fx-background-color: #00CC00; -fx-border-color: #000000;");
                hContainer.setSpacing(10);
                hContainer.setPadding(new Insets(5, 10, 5, 10));
                hContainer.getChildren().add(yesBtn);
                hContainer.getChildren().add(noBtn);
                hContainer.setAlignment(Pos.CENTER);
                vContainer.setAlignment(Pos.CENTER);
                vContainer.getChildren().add(imageView);
                vContainer.getChildren().add(hContainer);
                grid.add(vContainer, c, r + 1);
                count++;
            }
        }
        status.get(1).setText("yes");
        status.get(2).setText("yes");
        status.get(3).setText("yes");
        Scene characterSelectionScene = new Scene(grid);
        characterSelectionStage.setScene(characterSelectionScene);
        characterSelectionStage.showAndWait();
        return deckStr;
    }

    public void showCardGui(String cardAndName) throws InterruptedException {
        Stage showCardStage = new Stage();
        showCardStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene showCardScene = new Scene(grid);
        showCardStage.setScene(showCardScene);
        String card = cardAndName.split(",")[0];
        String name = cardAndName.split(",")[1];
        Text titleTxt = new Text(name + " you start as:");
        grid.add(titleTxt, 0, 0);
        Image cardImg = new Image(card + ".png", 100, 548, true, true);
        ImageView cardView = new ImageView(cardImg);
        grid.add(cardView, 0, 1);
        PauseTransition delay = new PauseTransition(Duration.seconds(15));
        delay.setOnFinished(event -> showCardStage.close());
        delay.play();
        showCardStage.showAndWait();
    }

    public void takeTurnGui(int card) throws InterruptedException{
        Stage takeTurnStage = new Stage();
        takeTurnStage.setAlwaysOnTop(true);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button();
        switch (card){
            //seer

            case 6: break;
            //robber
            case 7: break;
            //troublemaker
            case 8: break;
            //drunk
            case 9: break;
            //insomniac
            case 10: break;
        }
        Scene showCardScene = new Scene(grid);
        takeTurnStage.setScene(showCardScene);
        Image cardImg = new Image(card + ".png", 100, 548, true, true);
        ImageView cardView = new ImageView(cardImg);
        grid.add(cardView, 0, 1);
        takeTurnStage.showAndWait();
    }

    public void updateCardGui() {

    }
}
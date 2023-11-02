package com.example.objektorientering_inlamning3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.*;

public class HelloController {

    //CONSTRUCTORS

    //RAW DATA
    int buttonSize = 150;
    int columnSize = 4;
    int rowSize = 4;
    boolean randomizeInitialEmptySlot = false;
    int maxCells = rowSize*columnSize-1;
    int currentEmptySlot = 15;
    List<Integer> slotsNotTaken = new ArrayList<>();
    @FXML
    public GridPane gameGridPane;


    //INTERFACE

    @FXML
    protected void onNewGameButtonClick() {
        newGame();
        //shuffleToWinGame();
        checkGameState();
    }

    protected boolean verifyIfSlotsSwappable(int slotA, int slotB)
    {
        //One slot has to be the empty slot
        if(slotA != currentEmptySlot && slotB != currentEmptySlot)
            return false;

        //Logic for verifying moving rows
        if(Math.abs(slotA - slotB) == columnSize)
        {
            return true;
        }
        //Logic for verifying moving columns
        else if(Math.abs(slotA - slotB) == 1 && ((slotA / columnSize) == (slotB / columnSize)))
        {
            return true;
        }

        return false;
    }

    protected void newGame()
    {
        gameGridPane.getChildren().clear();
        currentEmptySlot = 15;

        // Init slotsNotTaken
        for(int i = 0; i < (rowSize); ++i)
        {
            for (int j = 0; j < (columnSize); ++j)
            {
                //Last slot has to be empty
                if((rowSize*i+j) >= (maxCells))
                    break;

                slotsNotTaken.add((rowSize*i+j));
            }
        }


       // List<Button> gameButtons = new ArrayList<>(Arrays.asList(new Button("1"), new Button("2"), new Button("3"), new Button("4")));
        List<Button> gameButtons = new ArrayList<>();
        gameButtons.add(new Button("1"));
        gameButtons.add(new Button("2"));
        gameButtons.add(new Button("3"));
        gameButtons.add(new Button("4"));
        gameButtons.add(new Button("5"));
        gameButtons.add(new Button("6"));
        gameButtons.add(new Button("7"));
        gameButtons.add(new Button("8"));
        gameButtons.add(new Button("9"));
        gameButtons.add(new Button("10"));
        gameButtons.add(new Button("11"));
        gameButtons.add(new Button("12"));
        gameButtons.add(new Button("13"));
        gameButtons.add(new Button("14"));
        gameButtons.add(new Button("15"));

        Random random = new Random();
        int rolledSlot = 0;
        int rolledRow = 0;
        int rolledColumn = 0;
        int indexRoll = 0;
        for(int i = 0; i < (rowSize); ++i)
        {
            for(int j = 0; j < (columnSize); ++j)
            {
                //Last slot has to be empty
                if((rowSize*i+j) >= (maxCells))
                    break;

                Button button = gameButtons.get((rowSize*i+j));
                button.setMaxSize(buttonSize, buttonSize);
                button.setOnAction(this::onGameButtonClick);

                //Roll slot to place button
                if(slotsNotTaken.size() <= 1)
                    indexRoll = 0;
                else
                    indexRoll = random.nextInt(slotsNotTaken.size());

                rolledSlot = slotsNotTaken.get(indexRoll);
                slotsNotTaken.remove(indexRoll);
                rolledRow = rolledSlot / columnSize;
                rolledColumn = rolledSlot % columnSize;


                //Place button
                if(randomizeInitialEmptySlot)
                    gameGridPane.add(button, j, i);
                else
                    gameGridPane.add(button, rolledRow, rolledColumn);

            }
        }

    }

    @FXML
    protected void shuffleToWinGame() {
        for(Node node : gameGridPane.getChildren())
        {
            Button button = (Button)node;
            int buttonNumber = Integer.parseInt(button.getText()) - 1;
            int buttonNewColumnIndex = buttonNumber % gameGridPane.getColumnCount();
            int buttonNewRowIndex = buttonNumber / gameGridPane.getColumnCount();

            gameGridPane.setColumnIndex(button, buttonNewColumnIndex);
            gameGridPane.setRowIndex(button, buttonNewRowIndex);
        }
    }
    @FXML
    protected void onGameButtonClick(ActionEvent actionEvent) {
        Object node = actionEvent.getSource();
        Button button = (Button)node;
        if(button == null)
            return;

        int buttonColumnIndex = gameGridPane.getColumnIndex(button);
        int buttonRowIndex = gameGridPane.getRowIndex(button);
        int clickedButtonConvertedSlot = buttonRowIndex * gameGridPane.getColumnCount() + buttonColumnIndex;

        //Swap indices of buttons
        if(verifyIfSlotsSwappable(clickedButtonConvertedSlot, currentEmptySlot))
        {
            gameGridPane.setColumnIndex(button, currentEmptySlot % gameGridPane.getColumnCount());
            gameGridPane.setRowIndex(button, currentEmptySlot / gameGridPane.getColumnCount());

            currentEmptySlot = clickedButtonConvertedSlot;
        }

        checkGameState();
    }

    protected void checkGameState()
    {
        gameGridPane.getChildren();
        for(Node node : gameGridPane.getChildren())
        {
            Button button = (Button)node;
            int buttonColumnIndex = gameGridPane.getColumnIndex(button);
            int buttonRowIndex = gameGridPane.getRowIndex(button);
            int clickedButtonConvertedSlot = buttonRowIndex * gameGridPane.getColumnCount() + buttonColumnIndex + 1;
            if(!button.getText().equals(Integer.toString(clickedButtonConvertedSlot)))
                return;
        }

        //You win, open window message
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Prevent clicking main window while this popup exist
        VBox dialogVbox = new VBox(); // parameter = spacing
        dialogVbox.getChildren().add(new Text("Congratulations, you win!"));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 100);
        stage.setScene(dialogScene);
        stage.show();
    }
}
package com.example.objektorientering_inlamning3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController {

    //CONSTRUCTORS

    //RAW DATA
    int buttonSize = 150;
    int columnSize = 4;
    int rowSize = 4;
    boolean randomizeInitialEmptySlot = false;
    int maxCells = rowSize*columnSize-1;

    List<Integer> slotsNotTaken = new ArrayList<>();

    //INTERFACE
    @FXML
    public GridPane gameGridPane;
    @FXML
    private Label welcomeText;


    //LAYOUT NEEDED
    //Button for new game (Center, then have gameboard under) (Shuffles all)
    //Gameboard = gridbox
    // test



    protected void newGame()
    {
        // Init slotsNotTaken
        for(int i = 0; i < (rowSize); ++i)
        {
            for (int j = 0; j < (columnSize); ++j)
            {
                //Last slot has to be empty
                if((rowSize*i+j) >= (maxCells))
                    break;

                slotsNotTaken.add((rowSize*i+j));
                System.out.println("ADD INDEX: " + (rowSize*i+j));
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
                //System.out.println("Text: " + button.getText() + " | Index: " + gameButtons.indexOf(button));
                //System.out.println("get index: " + (rowSize*i+j)+1);
                //Button button = gameButtons.get((rowSize*i+j)+1);

                //Last slot has to be empty
                if((rowSize*i+j) >= (maxCells))
                    break;

                Button button = gameButtons.get((rowSize*i+j));
                button.setMaxSize(buttonSize, buttonSize);

                //Roll slot to place button
                if(slotsNotTaken.size() <= 1)
                    indexRoll = 0;
                else
                    indexRoll = random.nextInt(slotsNotTaken.size()-1);

                rolledSlot = slotsNotTaken.get(indexRoll);
                System.out.println("ROLLEDSLOT: " + rolledSlot + " | slotsNotTaken SIZE: " + slotsNotTaken.size());
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

        //myButton.setMaxSize(buttonSize, buttonSize);
        //gameGridPane.add(myButton, 0, 0);

    }

    @FXML
    protected void onNewGameButtonClick() {
        newGame();
    }
}
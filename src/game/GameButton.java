package game;

import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton {

    private int buttonIndex;
    private GameBoard board;

    GameButton(int gameButtonIndex, GameBoard currentGameBoard){
        buttonIndex=gameButtonIndex;
        board=currentGameBoard;

        int rowNum = buttonIndex / GameBoard.dimension;
        int cellNum = buttonIndex % GameBoard.dimension;

        Font fontButton = new Font("NewTimesRoman",Font.BOLD,50);
        setFont(fontButton);

        setSize(GameBoard.sellSize-5,GameBoard.sellSize - 5);
        addActionListener(new GameActionListener(rowNum,cellNum,this));
    }

    GameBoard getBoard() {
        return board;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }
}

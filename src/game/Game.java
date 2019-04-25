package game;

import javax.swing.*;

public class Game {
    private GameBoard board;
    private GamePlayer[] gamePlayers=new GamePlayer[2];
    private int playersTurn=0;

    public Game(){
        board = new GameBoard(this);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayer('X',true);
        gamePlayers[1] = new GamePlayer('O',false);

    }

    void passTurn(){
        playersTurn=(playersTurn==0) ? 1 : 0;

    }

    GamePlayer getGamePlayer(){
        return gamePlayers[playersTurn];
    }

    GamePlayer getRelaxPlayer(){
        int relaxIndex= (playersTurn==0)?1:0;
        return gamePlayers[relaxIndex] ;
    }

    void showMessage(String messageText){
        JOptionPane.showMessageDialog(board,messageText);
    }
}

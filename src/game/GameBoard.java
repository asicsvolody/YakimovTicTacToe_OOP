package game;

import javax.swing.*;
import java.awt.*;


 class GameBoard extends JFrame {

    static int dimension = 3;
    static int sellSize = 150;
    private char [][] gameFiled;
    private GameButton [] gameButtons;
    static char nullSymbol='\u0000';

    static int levelNumber  = 0;

    private Game game;

    GameBoard(Game currentGame){
        this.game=currentGame;
        initField();
    }
    private void initField(){
        setBounds(sellSize*dimension,sellSize*dimension,400,300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");

        newGameButton.addActionListener( (e)-> emptyField());

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(sellSize*dimension,150);

        String[] levels=new String[]{"Тупой компьютер","Средний компьютер","Умный компьютер (DEMO)"};
        JComboBox <String> levelInput = new JComboBox <> (levels);
        levelInput.setEditable(true);
        levelInput.addActionListener((e)-> levelNumber = levelInput.getSelectedIndex());

        controlPanel.add(levelInput);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension,dimension));
        gameFieldPanel.setSize(sellSize*dimension,sellSize*dimension);
        gameFiled=new char[dimension][dimension];
        gameButtons=new GameButton[dimension*dimension];


        for (int i = 0; i < gameButtons.length; i++) {
            gameButtons[i]=new GameButton(i,this);
            gameFieldPanel.add(gameButtons[i]);
        }
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField(){
        for (int i = 0; i <gameButtons.length ; i++) {
            gameButtons[i].setText("");

            int x = i / dimension;
            int y = i % dimension;

            gameFiled[y][x] = nullSymbol;
        }
    }

    Game getGame() {
        return game;
    }

    boolean isTurnable(int x, int y){
        return gameFiled[y][x]==nullSymbol;
    }

    boolean isCurrentPlayerSymbol(int indexButton,char playerSymbol){
        int x = indexButton / dimension;
        int y = indexButton % dimension;
        return gameFiled[y][x]==playerSymbol;

    }

    void updateGameField(int x, int y){
        gameFiled[y][x] = game.getGamePlayer().getPlayerSign();
    }

    boolean isCheckWin(){
        char playerSymbol = game.getGamePlayer().getPlayerSign();
        return isCheckWinDiagonals(playerSymbol) || isCheckWinLines(playerSymbol);
    }

    private boolean isCheckWinDiagonals(char playerSymbol){
        boolean upDiagonalWin = true, downDiagonalWin = true;
        for (int i = 0; i < dimension ; i++) {
            upDiagonalWin&= gameFiled[i][i]== playerSymbol;
            downDiagonalWin &= gameFiled[gameFiled.length-1-i][i] ==playerSymbol;
        }
        return upDiagonalWin || downDiagonalWin;
    }

    private boolean isCheckWinLines(char playerSymbol){
        boolean  colsWin=true, rollsWin=true;

        for (int col = 0; col < dimension ; col++) {
            colsWin=true;
            rollsWin=true;
            for (int rol = 0; rol < dimension; rol++) {
                colsWin &= gameFiled[col][rol] == playerSymbol;
                rollsWin &= gameFiled[rol][col] == playerSymbol;
            }
            if(colsWin || rollsWin) break;
        }
        return colsWin || rollsWin;
    }

    boolean isFull(){
        boolean result =true;
        outputCycle:
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(gameFiled[i][j]==nullSymbol){
                    result=false;
                    break outputCycle;
                }
            }

        }
        return result;
    }

    GameButton getButton(int buttonIndex){
        return gameButtons[buttonIndex];
    }
}

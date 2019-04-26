package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int rowNum;
    private int cellNum;
    private GameButton button;


    GameActionListener(int rowNum,int cellNum, GameButton button){
        this.rowNum=rowNum;
        this.cellNum=cellNum;
        this.button=button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();
        if(board.isTurnable(rowNum, cellNum)){
            updateByPlayersDate(board);
            if(board.isFull()){
                notWinner(board);
            }
            else{
                updateByAiDate(board);
                if(board.isFull())notWinner(board);
            }
        }
        else {
            board.getGame().showMessage("Вы ввели некорректное поле!");
        }
    }
    private void notWinner(GameBoard board){
        board.getGame().showMessage("Ничья!");
        board.emptyField();
    }

    private void updateByPlayersDate(GameBoard board){
        board.updateGameField(rowNum,cellNum);
        button.setText(Character.toString(board.getGame().getGamePlayer().getPlayerSign()));

        checkWin(board,"Вы выйграли!");
    }

    private void updateByAiDate(GameBoard board){
        int cellIndex=-1;

        switch(GameBoard.levelNumber){
            case 0:
              cellIndex=stupidComputerStep(board);
              break;
            case 1:
                cellIndex=smartComputerStep(board);
                break;
            case 2:
            case 3:
                ExpertComputerDemo expertComputerDemo = new ExpertComputerDemo(button.getBoard());
                cellIndex=expertComputerDemo.expertComputerStep();
                break;
        }
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getGamePlayer().getPlayerSign()));

        checkWin(board,"Компьютер выйграл!");
    }

    private void checkWin (GameBoard board,String messageText){
        if(board.isCheckWin()){
            board.getGame().showMessage(messageText);
            board.emptyField();
        }else{
            board.getGame().passTurn();
        }
    }

    private int stupidComputerStep(GameBoard board){
        int x,y;
        Random random = new Random();
        do{
            x = random.nextInt(GameBoard.dimension);
            y = random.nextInt(GameBoard.dimension);
        }while(!board.isTurnable(x,y));

        board.updateGameField(x,y);
        return GameBoard.dimension*x+y;
    }

    private int smartComputerStep(GameBoard board){
        char computerSymbol= board.getGame().getGamePlayer().getPlayerSign();
        int winnerButton= -1;
        int winnerButtonPoints = 0;
        for (int i = 0; i < GameBoard.dimension*GameBoard.dimension; i++) {
           int pointsButton = 0;

           if(board.isTurnable(i / GameBoard.dimension,i % GameBoard.dimension)) {

               if ((i - 1) >= 0
                       && (i % GameBoard.dimension) != 0
                       && board.isCurrentPlayerSymbol(i - 1, computerSymbol))
                   pointsButton++;
               if ((i + 1) < GameBoard.dimension * GameBoard.dimension
                       && (i % GameBoard.dimension) != (GameBoard.dimension-1)
                       && board.isCurrentPlayerSymbol(i + 1, computerSymbol))
                   pointsButton++;
               if ((i - GameBoard.dimension) >= 0
                       && board.isCurrentPlayerSymbol(i - GameBoard.dimension, computerSymbol))
                   pointsButton++;
               if ((i + GameBoard.dimension) < GameBoard.dimension * GameBoard.dimension
                       && board.isCurrentPlayerSymbol(i + GameBoard.dimension, computerSymbol))
                   pointsButton++;
               if ((i - (GameBoard.dimension+1)) >= 0
                       && i!=GameBoard.dimension*GameBoard.dimension-GameBoard.dimension
                       && board.isCurrentPlayerSymbol(i - (GameBoard.dimension+1), computerSymbol))
                   pointsButton++;
               if ((i + (GameBoard.dimension+1)) < GameBoard.dimension * GameBoard.dimension
                       && i!=(GameBoard.dimension-1)
                       && board.isCurrentPlayerSymbol(i + (GameBoard.dimension+1), computerSymbol))
                   pointsButton++;
               if ((i - (GameBoard.dimension-1)) >= 0
                       && i%GameBoard.dimension!=(GameBoard.dimension-1)
                       && board.isCurrentPlayerSymbol(i - (GameBoard.dimension-1), computerSymbol))
                   pointsButton++;
               if ((i + (GameBoard.dimension-1)) < GameBoard.dimension * GameBoard.dimension
                       && i%GameBoard.dimension!=0
                       && board.isCurrentPlayerSymbol(i + (GameBoard.dimension-1), computerSymbol))
                   pointsButton++;

               if (pointsButton > winnerButtonPoints) {
                   winnerButton = i;
                   winnerButtonPoints=pointsButton;
               }
           }
        }
        if (winnerButton == -1) {
            do{
            Random random = new Random();
            winnerButton = random.nextInt(GameBoard.dimension*GameBoard.dimension-1);
            }while(!board.isTurnable(winnerButton/GameBoard.dimension,winnerButton % GameBoard.dimension));

        }
        board.updateGameField(winnerButton/GameBoard.dimension,winnerButton % GameBoard.dimension);
        return winnerButton;
    }

}

package game;
/*
      Интелект "Без шансов" для пользователя разработан с применением банального перечисления
      вариантов. Он работает только для поля 3*3 и неподходит не для какого другого поля
      Для того чтобы не захламлять класс GameActionListener  я вынес его в отдельно.
       Понимаю что это откровенный говно-код. Но хотелось сделать что то
      абсолютно непобедимое.
*/

import java.util.Random;




class ExpertComputerDemo  {
    private char computerSymbol;
    private char realPlayerSymbol;
    private GameBoard board;

    ExpertComputerDemo(GameBoard board){
        this.board = board;
        this.computerSymbol = board.getGame().getGamePlayer().getPlayerSign();
        this.realPlayerSymbol = board.getGame().getRelaxPlayer().getPlayerSign();

    }

      int expertComputerStep() {

        int computerStep = -1;
        int computerStepPoints=0;



       for (int i = 0; i < GameBoard.dimension*GameBoard.dimension; i++) {
           int buttonPoint;
           if(board.isTurnable(i / GameBoard.dimension,i % GameBoard.dimension)){
               buttonPoint=verticalCheck(i,computerSymbol);
               if(buttonPoint > computerStepPoints){
                   computerStep = i;
                   computerStepPoints=buttonPoint;
               }
               buttonPoint=horizontalCheck(i,computerSymbol);
               if(buttonPoint > computerStepPoints){
                   computerStep = i;
                   computerStepPoints=buttonPoint;
               }
               buttonPoint=upDownDiagonal(i,computerSymbol);
               if(buttonPoint > computerStepPoints){
                   computerStep = i;
                   computerStepPoints=buttonPoint;
               }
               buttonPoint=downUpDiagonal(i,computerSymbol);
               if(buttonPoint > computerStepPoints){
                   computerStep = i;
                   computerStepPoints=buttonPoint;
               }
           }
       }
       if(computerStepPoints < GameBoard.dimension){
           int playerWinStep = -1;
           int playerWinStepPoints = 0;
           for (int i = 0; i < GameBoard.dimension*GameBoard.dimension; i++) {
               int buttonPoint;
               if(board.isTurnable(i / GameBoard.dimension,i % GameBoard.dimension)){
                   buttonPoint=verticalCheck(i,realPlayerSymbol);
                   if(buttonPoint > playerWinStepPoints){
                       playerWinStep = i;
                       playerWinStepPoints=buttonPoint;
                   }
                   buttonPoint=horizontalCheck(i,realPlayerSymbol);
                   if(buttonPoint > playerWinStepPoints){
                       playerWinStep = i;
                       playerWinStepPoints=buttonPoint;
                   }
                   buttonPoint=upDownDiagonal(i,realPlayerSymbol);
                   if(buttonPoint > playerWinStepPoints){
                       playerWinStep = i;
                       playerWinStepPoints=buttonPoint;
                   }
                   buttonPoint=downUpDiagonal(i,realPlayerSymbol);
                   if(buttonPoint > playerWinStepPoints){
                       playerWinStep = i;
                       playerWinStepPoints=buttonPoint;
                   }
               }
           }
           if(playerWinStepPoints==GameBoard.dimension){
               computerStep = playerWinStep;
               computerStepPoints = playerWinStepPoints;
           }
       }

       if(computerStepPoints == 1){
           if(board.isTurnable( (GameBoard.dimension*GameBoard.dimension/2)/ GameBoard.dimension,
                   (GameBoard.dimension*GameBoard.dimension/2) % GameBoard.dimension)){
               computerStep = GameBoard.dimension*GameBoard.dimension/2;
           }else {
               Random random = new Random();
               int randomInt;
               do{
                   randomInt = random.nextInt(GameBoard.dimension*GameBoard.dimension-1);
               }while(!board.isTurnable(randomInt / GameBoard.dimension,randomInt % GameBoard.dimension));

           }
       }

        board.updateGameField(computerStep/GameBoard.dimension,computerStep % GameBoard.dimension);
        return computerStep;

   }


    private int verticalCheck(int indexButton, char playerSymbol){
        //boolean result=true;
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = (indexButton - GameBoard.dimension); i >= 0; i -= GameBoard.dimension) {
                //result &= (board.isCurrentPlayerSymbol(i, playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        for (int i = (indexButton + GameBoard.dimension); i < GameBoard.dimension*GameBoard.dimension;
             i+=GameBoard.dimension) {
           // result &=(board.isCurrentPlayerSymbol(i,playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;
    }
    private int horizontalCheck(int indexButton,char playerSymbol){
        //boolean result=true;
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = indexButton-1; i>=0 && i % GameBoard.dimension != GameBoard.dimension-1 ; i --) {
            //result &= (board.isCurrentPlayerSymbol(i, playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }



        for (int i = indexButton + 1; i < GameBoard.dimension*GameBoard.dimension && i % GameBoard.dimension!=0;
             i++) {
              //  result &=(board.isCurrentPlayerSymbol(i,playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;
    }
    private int upDownDiagonal(int indexButton, char playerSymbol){
        //boolean result=true;
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = indexButton-(GameBoard.dimension +1); i >=0 ; i -=(GameBoard.dimension +1)) {
            //result &= (board.isCurrentPlayerSymbol(i, playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;

        }
        for (int i = indexButton + (GameBoard.dimension+1); i < GameBoard.dimension*GameBoard.dimension;
             i+=(GameBoard.dimension+1)) {
            //result &=(board.isCurrentPlayerSymbol(i,playerSymbol));howManyButtons++;
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;

        }
        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;
    }

    private int downUpDiagonal(int indexButton, char playerSymbol){
        //boolean result=true;
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = indexButton-(GameBoard.dimension - 1); i >0
                && i % GameBoard.dimension == (i+ GameBoard.dimension - 1) % GameBoard.dimension - 1;
                i -= (GameBoard.dimension - 1)) {
            //result &= (board.isCurrentPlayerSymbol(i, playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        for (int i = indexButton+(GameBoard.dimension - 1); i < GameBoard.dimension*GameBoard.dimension
                && i % GameBoard.dimension == ((i- GameBoard.dimension - 1) % GameBoard.dimension - 1);
             i += (GameBoard.dimension - 1)) {
            //result &=(board.isCurrentPlayerSymbol(i,playerSymbol));
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }
        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;

    }

}

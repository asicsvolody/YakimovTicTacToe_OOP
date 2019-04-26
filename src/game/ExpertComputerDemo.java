package game;
/*
      Интелект "Умный компьютер" создан с применением анализа игрового поля.
      Сначала он анализирует может ли он сделать победный ход и если может делает его.
      Потом он анализирует сможет ли потивник сделать победный ход следующим шагом и если да то ставит свой символ туда.
      Затем он проверяет руководствуясь предыдущим анализом первый ли это хо. И если первый то если центральная клетка
      не занята ставит символ туда, если занята то генерирует случайное число и ставит символ в клетку с этим индексом
      Если ход не первый то руководствуясь предыдущим анализом атакует.
      Я специально сделал одну прореху в логике. Что бы у пользователя оставался шанс выйграть. Это всего лишь одна
      последовательность ходов.
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

        int computerStep ;
        int computerStepPoints;
        int [] computerStepArr;

       computerStepArr = cycleAnalysisField(computerSymbol);
       computerStep = computerStepArr[0];
       computerStepPoints = computerStepArr[1];

       if(computerStepPoints < GameBoard.dimension){
           computerStepArr = cycleAnalysisField(realPlayerSymbol);
           if(computerStepArr[1]==GameBoard.dimension){
               computerStep = computerStepArr[0];
               computerStepPoints = computerStepArr[1];
           }
       }

       if(computerStepPoints == 1){
           computerStep=firstComputerStep();
       }

        board.updateGameField(computerStep/GameBoard.dimension,computerStep % GameBoard.dimension);
        return computerStep;

   }

   private int [] cycleAnalysisField(char playerSymbol){
       int [] playerWinStep = new int[] {-1,0};
       for (int i = 0; i < GameBoard.dimension*GameBoard.dimension; i++) {
           int buttonPoint;
           if(board.isTurnable(i / GameBoard.dimension,i % GameBoard.dimension)){
               buttonPoint=verticalCheck(i,playerSymbol);
               if(buttonPoint > playerWinStep[1]){
                   playerWinStep[0] = i;
                   playerWinStep[1]=buttonPoint;
               }
               buttonPoint=horizontalCheck(i,playerSymbol);
               if(buttonPoint > playerWinStep[1]){
                   playerWinStep[0] = i;
                   playerWinStep[1]=buttonPoint;
               }
               buttonPoint=upDownDiagonal(i,playerSymbol);
               if(buttonPoint > playerWinStep[1]){
                   playerWinStep[0] = i;
                   playerWinStep[1]=buttonPoint;
               }
               buttonPoint=downUpDiagonal(i,playerSymbol);
               if(buttonPoint > playerWinStep[1]){
                   playerWinStep[0] = i;
                   playerWinStep[1]=buttonPoint;
               }
           }
       }
       return playerWinStep;

   }

   private int firstComputerStep(){
        int firstComputerStep;
       if(board.isTurnable( (GameBoard.dimension*GameBoard.dimension/2)/ GameBoard.dimension,
               (GameBoard.dimension*GameBoard.dimension/2) % GameBoard.dimension)){
           firstComputerStep = GameBoard.dimension*GameBoard.dimension/2;
       }else {
           Random random = new Random();
           int randomInt;
           do{
               randomInt = random.nextInt(GameBoard.dimension*GameBoard.dimension-1);
           }while(!board.isTurnable(randomInt / GameBoard.dimension,randomInt % GameBoard.dimension));
           firstComputerStep = randomInt;

       }

        return firstComputerStep;
   }



    private int verticalCheck(int indexButton, char playerSymbol){
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = (indexButton - GameBoard.dimension); i >= 0; i -= GameBoard.dimension) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        for (int i = (indexButton + GameBoard.dimension); i < GameBoard.dimension*GameBoard.dimension;
             i+=GameBoard.dimension) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;
    }
    private int horizontalCheck(int indexButton,char playerSymbol){

        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = indexButton-1; i>=0 && i % GameBoard.dimension != GameBoard.dimension-1 ; i --) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }



        for (int i = indexButton + 1; i < GameBoard.dimension*GameBoard.dimension && i % GameBoard.dimension!=0;
             i++) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }
        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;
    }
    private int upDownDiagonal(int indexButton, char playerSymbol){
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = indexButton-(GameBoard.dimension +1); i >=0 ; i -=(GameBoard.dimension +1)) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;

        }
        for (int i = indexButton + (GameBoard.dimension+1); i < GameBoard.dimension*GameBoard.dimension;
             i+=(GameBoard.dimension+1)) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;

        }
        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;
    }

    private int downUpDiagonal(int indexButton, char playerSymbol){
        int howManyTrue=1;
        int howManyButtons = 1;

        for (int i = indexButton-(GameBoard.dimension - 1); i >0
                && i % GameBoard.dimension == (i+ (GameBoard.dimension - 1) % GameBoard.dimension )+ 1;
                i -= (GameBoard.dimension - 1)) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }

        for (int i = indexButton+(GameBoard.dimension - 1); i < GameBoard.dimension*GameBoard.dimension
                && i % GameBoard.dimension == (i- GameBoard.dimension + 1) % GameBoard.dimension - 1;
             i += (GameBoard.dimension - 1)) {
            howManyButtons++;
            if(board.isCurrentPlayerSymbol(i, playerSymbol)) howManyTrue++;
        }
        howManyTrue=(howManyButtons==GameBoard.dimension)? howManyTrue:0;

        return howManyTrue;

    }

}

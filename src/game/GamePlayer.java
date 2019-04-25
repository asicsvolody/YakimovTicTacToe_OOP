package game;

public class GamePlayer {
    private char playerSign;
    private boolean realPlayer;

    GamePlayer(char playerSign, boolean isRealPlayer){
        this.playerSign=playerSign;
        this.realPlayer=isRealPlayer;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }

    char getPlayerSign() {
        return playerSign;
    }
}

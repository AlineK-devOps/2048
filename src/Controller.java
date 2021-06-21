import java.awt.event.KeyAdapter;

public class Controller extends KeyAdapter { //следит за нажатием клавиш во время игры
    private Model model; //игровое поле
    private View view; //представление

    public Tile[][] getGameTiles(){ //получить игровое поле
        return model.getGameTiles();
    }

    public int getScore(){ //получить счёт
        return model.score;
    }
}

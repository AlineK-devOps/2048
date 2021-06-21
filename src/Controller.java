import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter { //следит за нажатием клавиш во время игры
    private static final int WINNING_TILE = 2048; //победная плитка

    private Model model; //игровое поле
    private View view; //представление

    public Controller(Model model){
        this.model = model;
        view = new View(this);
    }

    public Tile[][] getGameTiles(){ //получить игровое поле
        return model.getGameTiles();
    }

    public View getView(){
        return view;
    }

    public int getScore(){ //получить счёт
        return model.score;
    }

    public void resetGame(){ //обнуление игры
        model.score = 0;
        view.isGameLost = false;
        view.isGameWon = false;
        model.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent e) { //обработчик событий кнопок
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) //если нажата esc
            resetGame();

        if (!model.canMove()) //если больше нет возможных ходов - игра проиграна
            view.isGameLost = true;

        if (!(view.isGameLost && view.isGameWon)){ //обработка нажатий
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    model.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    model.right();
                    break;
                case KeyEvent.VK_DOWN:
                    model.down();
                    break;
                case KeyEvent.VK_UP:
                    model.up();
                    break;
                case KeyEvent.VK_Z:
                    model.rollback();
                    break;
            }
        }

        if (model.maxTile == WINNING_TILE) //если достигнута цель 2048 - игра выиграна
            view.isGameWon = true;

        view.repaint(); //обновление окна
    }
}

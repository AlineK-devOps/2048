import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Model { //содержит игровую логику и хранит игровое поле
    private static final int FIELD_WIDTH = 4; //ширина игрового поля

    private Tile[][] gameTiles; //игровое поле
    protected int score; //игровой счёт
    protected int maxTile; //значение самое большой плитки
    private Stack<Tile[][]> previousStates = new Stack<>(); //предыдущие состояния игрового поля
    private Stack<Integer> previousScores = new Stack<>(); //предыдущие счета
    private boolean isSaveNeeded = true; //нужно ли сохранить текущее состояние игры

    public Model() {
        resetGameTiles();
    }

    protected void resetGameTiles(){ //сбрасывает состояние игрового поля
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH]; //заполнение поля пустыми ячейками

        for (int i = 0; i < FIELD_WIDTH; i++){
            for (int j = 0; j < FIELD_WIDTH; j++){
                gameTiles[i][j] = new Tile();
            }
        }

        addTile(); //2 начальные клетки
        addTile();
    }

    public Tile[][] getGameTiles(){
        return gameTiles;
    }

    public boolean canMove(){ //возможен ли ход
        if (getEmptyTiles().size() != 0)
            return true;

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if ((i < FIELD_WIDTH - 1 && gameTiles[i][j].value == gameTiles[i + 1][j].value)
                        || ((j < FIELD_WIDTH - 1) && gameTiles[i][j].value == gameTiles[i][j + 1].value)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addTile(){//добавление новой активной плитки
        List<Tile> emptyTiles = getEmptyTiles();

        if (!emptyTiles.isEmpty()){
            int randomValue = Math.random() < 0.9? 2 : 4;
            int randomTile = (int)(Math.random() * emptyTiles.size());

            emptyTiles.get(randomTile).value = randomValue;
        }
    }

    private List<Tile> getEmptyTiles(){ //получение списка свободных клеток
        List<Tile> emptyTiles = new ArrayList<>();

        for (int i = 0; i < FIELD_WIDTH; i++){
            for (int j = 0; j < FIELD_WIDTH; j++){
                if (gameTiles[i][j].isEmpty())
                    emptyTiles.add(gameTiles[i][j]);
            }
        }

        return emptyTiles;
    }

    public void left(){ //движение влево
        if (isSaveNeeded)
            saveState(gameTiles); // сохраняем текущее состояние игрового поля

        boolean isChanged = false;

        for (Tile[] tiles : gameTiles){ //сжимаем и сливаем клетки игрового поля
            if (compressTiles(tiles) | mergeTiles(tiles))
                isChanged = true;
        }

        if (isChanged) addTile(); //добавляем новую клетку, если ход был выполнен

        isSaveNeeded = true;
    }

    public void up(){
        saveState(gameTiles); // сохраняем текущее состояние игрового поля

        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        left();
        rotateClockwise();
    }

    public void right(){
        saveState(gameTiles); // сохраняем текущее состояние игрового поля

        rotateClockwise();
        rotateClockwise();
        left();
        rotateClockwise();
        rotateClockwise();
    }

    public void down(){
        saveState(gameTiles); // сохраняем текущее состояние игрового поля

        rotateClockwise();
        left();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    public void rotateClockwise(){ //поворот поля на 90 градусов
        Tile[][] result = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                result[j][FIELD_WIDTH - 1 - i] = gameTiles[i][j];
            }
        }
        gameTiles = result;
    }

    private boolean compressTiles(Tile[] tiles){ //сжатие плиток
        boolean isChanged = false;

        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles.length - 1; j++){
                if (tiles[j].isEmpty() && !tiles[j+1].isEmpty()){
                    Tile temp = tiles[j];
                    tiles[j] = tiles[j+1];
                    tiles[j+1] = temp;

                    isChanged = true;
                }
            }
        }
        return isChanged;
    }

    private boolean mergeTiles(Tile[] tiles){ //слияние плиток
        boolean isChanged = false;

        for (int i = 0; i < tiles.length - 1; i++){
            if (tiles[i].value == tiles[i+1].value && !tiles[i].isEmpty()){
                tiles[i].value *= 2;
                tiles[i+1].value = 0;

                if (tiles[i].value > maxTile) //проверяем является ли новая плитка максимальной на поле
                    maxTile = tiles[i].value;

                score += tiles[i].value; //увеличиваем счёт

                isChanged = true;
            }
        }
        compressTiles(tiles);
        return isChanged;
    }

    private void saveState(Tile[][] state){ //сохраняет текущее состояние игрового поля и счет в стек
        Tile[][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                temp[i][j] = new Tile(state[i][j].value);
            }
        }

        previousStates.push(temp);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback(){ //устанавливает текущее игровое состояние равным последнему в стеке
        if (!previousStates.isEmpty())
            gameTiles = previousStates.pop();
        if (!previousScores.isEmpty())
            score = previousScores.pop();
    }
}

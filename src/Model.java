import java.util.ArrayList;
import java.util.List;

public class Model { //содержит игровую логику и хранит игровое поле
    private static final int FIELD_WIDTH = 4; //ширина игрового поля

    private Tile[][] gameTiles; //игровое поле
    protected int score; //игровой счёт
    protected int maxTile; //значение самое большой плитки

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

    private void compressTiles(Tile[] tiles){ //сжатие плиток
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles.length - 1; j++){
                if (tiles[j].isEmpty()){
                    Tile temp = tiles[j];
                    tiles[j] = tiles[j+1];
                    tiles[j+1] = temp;
                }
            }
        }
    }

    private void mergeTiles(Tile[] tiles){ //слияние плиток
        for (int i = 0; i < tiles.length - 1; i++){
            if (tiles[i].value == tiles[i+1].value){
                tiles[i].value *= 2;
                tiles[i+1].value = 0;

                if (tiles[i].value > maxTile) //проверяем является ли новая плитка максимальной на поле
                    maxTile = tiles[i].value;

                score += tiles[i].value; //увеличиваем счёт
            }
        }
        compressTiles(tiles);
    }
}

import java.util.ArrayList;
import java.util.List;

public class Model { //содержит игровую логику и хранит игровое поле
    private static final int FIELD_WIDTH = 4; //ширина игрового поля

    private Tile[][] gameTiles; //игровое поле

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
}

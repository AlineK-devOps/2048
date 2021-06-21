public class Model { //содержит игровую логику и хранит игровое поле
    private static final int FIELD_WIDTH = 4; //ширина игрового поля

    private Tile[][] gameTiles; //игровое поле

    public Model() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH]; //заполнение поля пустыми ячейками

        for (int i = 0; i < FIELD_WIDTH; i++){
            for (int j = 0; j < FIELD_WIDTH; j++){
                gameTiles[i][j] = new Tile();
            }
        }
    }
}

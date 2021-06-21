public class MoveEfficiency { //эффективность хода
    private int numberOfEmptyTiles; //количество пустых клеток
    private int score; //счёт после хода
    private Move move; //ход

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove(){
        return move;
    }
}

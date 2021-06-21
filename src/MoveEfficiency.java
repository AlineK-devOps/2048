public class MoveEfficiency implements Comparable<MoveEfficiency>{ //эффективность хода
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

    @Override
    public int compareTo(MoveEfficiency o) {
        if (this.numberOfEmptyTiles - o.numberOfEmptyTiles > 0)
            return 1;
        else if (this.numberOfEmptyTiles - o.numberOfEmptyTiles < 0)
            return -1;
        else return Integer.compare(this.score - o.score, 0);
    }
}

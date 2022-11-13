// Any live cell with two or three live neighbours survives.
// All other live cells die in the next generation. Similarly, all other dead cells stay dead.
fun handleLiveCell(liveNeighbours: Int): CellState = when (liveNeighbours) {
    2, 3 -> Alive
    else -> Dead
}

// Any dead cell with three live neighbours becomes a live cell.
// All other live cells die in the next generation. Similarly, all other dead cells stay dead.
fun handleDeadCell(liveNeighbours: Int): CellState = when (liveNeighbours) {
    3 -> Alive
    else -> Dead
}

fun tick(board: Board): Board = Board(
    generateSequence(Pair(0, 0)) {
        if (it.second + 1 >= board.columns) Pair(it.first + 1, 0)
        else Pair(it.first, it.second + 1)
    }.take(board.size)
        .map { (r, c) ->
            val n = board.aliveCellsBy(r, c)
            when (board.getState(r, c)) {
                Alive -> handleLiveCell(n)
                Dead -> handleDeadCell(n)
            }
        }.toList().toTypedArray(), board.columns
)

fun main(args: Array<String>) {
}
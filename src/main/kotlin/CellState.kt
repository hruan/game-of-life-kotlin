sealed interface CellState

object Alive : CellState {
    override fun toString(): String = "O"
}

object Dead : CellState {
    override fun toString(): String = "."
}
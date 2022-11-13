data class Board(private val cells: Array<CellState>, val columns: Int) {
    init {
        require(cells.size % columns == 0) { "number of cells must be evenly divisible by columns" }
    }

    val size = cells.size
    val rows = cells.size / columns

    private fun withinBounds(r: Int, c: Int): Boolean =
        r >= 0 && c >= 0 && r < rows && c < columns

    fun getState(r: Int, c: Int): CellState {
        require(withinBounds(r, c)) { "r and c must be within upper bounds (${rows}, ${columns})" }
        return cells[r * columns + c]
    }

    private fun neighbouringCellsTo(r: Int, c: Int): Iterator<CellState> = object : Iterator<CellState> {
        init {
            require(withinBounds(r, c)) { "starting cell out of bounds" }
        }

        var next = 0
        var peeked = false

        override fun hasNext(): Boolean {
            if (next >= 0) {
                next = try {
                    nextWithinBoundsFrom(next)
                } catch (_: NoSuchElementException) {
                    -1
                }
            }

            peeked = next >= 0
            return peeked
        }

        override fun next(): CellState {
            if (next < 0) throw NoSuchElementException()

            if (!peeked) next = nextWithinBoundsFrom(next)

            peeked = false
            val i = next++
            return cells[(r + dr[i]) * columns + c + dc[i]]
        }

        fun nextWithinBoundsFrom(start: Int) =
            (start until dr.size).first { withinBounds(r + dr[it], c + dc[it]) }
    }

    private inline fun <reified T : CellState> neighboursTo(r: Int, c: Int): List<T> =
        neighbouringCellsTo(r, c).asSequence().filter { it is T }.map { it as T }.toList()

    fun aliveCellsBy(r: Int, c: Int): Int = neighboursTo<Alive>(r, c).size

    companion object {
        private val dr = arrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
        private val dc = arrayOf(-1, 0, 1, -1, 1, -1, 0, 1)

        operator fun invoke(seed: Array<Array<Int>>): Board {
            require(seed.isNotEmpty()) { "seed must contain at least one row" }

            return Board(seed.reduce { a, b -> a + b }.map {
                when (it) {
                    0 -> Dead
                    else -> Alive
                }
            }.toTypedArray(), seed[0].size)
        }

        operator fun invoke(seed: String, columns: Int): Board {
            require(seed.isNotEmpty()) { "seed must not be empty" }
            require(seed.any { !(it == '.' || it == 'O') }) { "only '.' and 'O' are allowed characters in seed" }

            return Board(
                seed.split('\n')
                    .joinToString(separator = "") { it.trim() }
                    .map {
                        when (it) {
                            '.' -> Dead
                            'O' -> Alive
                            else -> throw UnsupportedOperationException("unexpected character")
                        }
                    }.toTypedArray(), columns
            )
        }

        operator fun invoke(seed: Array<Array<CellState>>): Board {
            require(seed.isNotEmpty()) { "seed must contain at least one row" }

            return Board(seed.reduce { a, b -> a + b }, seed[0].size)
        }
    }

    override fun toString(): String =
        cells.asIterable()
            .chunked(columns)
            .joinToString(separator = "\n") { row ->
                row.joinToString("") { it.toString() }
            }

    // region Generated code
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (!cells.contentEquals(other.cells)) return false
        if (rows != other.rows) return false
        if (columns != other.columns) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cells.contentHashCode()
        result = 31 * result + rows
        result = 31 * result + columns
        return result
    }
    // endregion
}
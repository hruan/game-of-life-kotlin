import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class MainKtTest : ShouldSpec({
    context("board") {
        should("should be able to toString board") {
            val stillLifeBlockBoard = Board(
                arrayOf(
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 1, 1, 0),
                    arrayOf(0, 1, 1, 0),
                    arrayOf(0, 0, 0, 0)
                )
            )

            stillLifeBlockBoard.toString() shouldBe """
                ....
                .OO.
                .OO.
                ....
            """.trimIndent()
        }
    }

    context("tick") {
        should("still life block should remain unchanged") {
            val stillLifeBlockBoard = Board(
                arrayOf(
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 1, 1, 0),
                    arrayOf(0, 1, 1, 0),
                    arrayOf(0, 0, 0, 0)
                )
            )

            tick(stillLifeBlockBoard) shouldBe stillLifeBlockBoard
        }

        should("still life tub should remain") {
            val stillLifeTubBoard = Board(
                arrayOf(
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(0, 0, 1, 0, 0),
                    arrayOf(0, 1, 0, 1, 0),
                    arrayOf(0, 0, 1, 0, 0),
                    arrayOf(0, 0, 0, 0, 0)
                )
            )

            tick(stillLifeTubBoard) shouldBe stillLifeTubBoard
        }

        should("handle minimum bounding box") {
            val blinkerBoard = Board(
                """
                .O.
                .O.
                .O.
            """.trimIndent(), 3
            )

            val blinkerBoard2 = Board(
                arrayOf(
                    arrayOf(0, 0, 0),
                    arrayOf(1, 1, 1),
                    arrayOf(0, 0, 0),
                )
            )

            tick(blinkerBoard2) shouldBe blinkerBoard
        }

        should("blinker states should be swap") {
            val blinkerBoard = Board(
                arrayOf(
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(0, 0, 1, 0, 0),
                    arrayOf(0, 0, 1, 0, 0),
                    arrayOf(0, 0, 1, 0, 0),
                    arrayOf(0, 0, 0, 0, 0),
                )
            )

            val blinkerBoard2 = Board(
                arrayOf(
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(0, 1, 1, 1, 0),
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0, 0),
                )
            )

            tick(blinkerBoard) shouldBe blinkerBoard2
            tick(tick(blinkerBoard)) shouldBe blinkerBoard
        }

        should("pulsar should transition between its states") {
            val cells =
                """...............
                   ...OOO...OOO...
                   ...............
                   .O....O.O....O.
                   .O....O.O....O.
                   .O....O.O....O.
                   ...OOO...OOO...
                   ...............
                   ...OOO...OOO...
                   .O....O.O....O.
                   .O....O.O....O.
                   .O....O.O....O.
                   ...............
                   ...OOO...OOO...
                   ...............""".trimIndent()
            val pulsar1 = Board(cells, 15)

            tick(tick(tick(pulsar1))) shouldBe pulsar1
        }
    }
})
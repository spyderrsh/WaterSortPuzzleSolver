package solver

import model.Board
import model.ColorPour
import model.Vial
import model.WaterColor
import model.WaterColor.*
import org.junit.Test

class BruteForceBoardSolverTest {
    val board1 = Board(
        Vial.from(Vial.EMPTY, ColorPour(WaterColor.DARK_BLUE, 3))!!,
        Vial.from(Vial.EMPTY, ColorPour(WaterColor.RED, 3))!!,
        Vial.from(Vial.EMPTY, ColorPour(WaterColor.DARK_BLUE, 1))!!,
        Vial.from(Vial.EMPTY, ColorPour(WaterColor.RED, 1))!!,

        )


    @Test
    fun `Solves Board 1`() {
        assert(BruteForceBoardSolver.solveBoard(board1).onEach {
            println(it)
        }.isNotEmpty())
    }

    val board2 = Board(
        Vial(
            ORANGE,
            ORANGE,
            LIGHT_BLUE,
            LIGHT_BLUE,
        ),
        Vial(
            DARK_GREEN,
            DARK_BLUE,
            PINK,
            ORANGE
        ),
        Vial(
            NEON_GREEN,
            DARK_GREEN,
            PINK,
            DARK_BLUE
        ),
        Vial(
            OLIVE_GREEN,
            GREY,
            DARK_BLUE,
            RED
        ),
        Vial(
            OLIVE_GREEN,
            GREY,
            GREY,
            NEON_GREEN
        ),
        Vial(
            OLIVE_GREEN,
            RED,
            PINK,
            BROWN
        ),
        Vial(
            PURPLE,
            GREY,
            OLIVE_GREEN,
            BROWN
        ),
        Vial(
            PURPLE,
            NEON_GREEN,
            LIGHT_BLUE,
            DARK_GREEN
        ),
        Vial(
            DARK_GREEN,
            BROWN,
            NEON_GREEN,
            PINK
        ),
        Vial(
            RED,
            LIGHT_BLUE,
            YELLOW,
            YELLOW
        ),
        Vial(
            ORANGE,
            BROWN,
            DARK_BLUE,
            PURPLE
        ),
        Vial(
            YELLOW,
            YELLOW,
            RED,
            PURPLE
        ),
        Vial.EMPTY,
        Vial.EMPTY
    )

    @Test
    fun `Solves Board 2`() {
        assert(BruteForceBoardSolver.solveBoard(board2).onEach {
            println(it)
        }.isNotEmpty())
    }
}
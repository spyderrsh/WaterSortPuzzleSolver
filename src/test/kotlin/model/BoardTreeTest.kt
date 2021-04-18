package model

import org.junit.Assert.assertEquals
import org.junit.Test

class BoardTreeTest {

    @Test
    fun `Can add unique boards that are slightly different`() {
        val boardTree = BoardTree()
        val board1 = Board(
            Vial(
                WaterColor.RED,
                WaterColor.BROWN,
                WaterColor.DARK_GREEN
            ),
            Vial(
                WaterColor.LIGHT_BLUE,
                WaterColor.DARK_BLUE,
                WaterColor.BROWN,
            ),
            Vial.EMPTY
        )
        val board2 = Board(
            Vial(
                WaterColor.RED,
                WaterColor.BROWN,
                WaterColor.DARK_GREEN
            ),
            Vial(
                WaterColor.LIGHT_BLUE,
                WaterColor.DARK_BLUE,
                WaterColor.DARK_BLUE,
            ),
            Vial.EMPTY
        )
        assertEquals(true, boardTree.addBoard(board1))
        assertEquals(true, boardTree.addBoard(board2))
    }

    @Test
    fun `Cannot add boards that are only different in vial order`() {
        val boardTree = BoardTree()
        val board1 = Board(
            Vial(
                WaterColor.RED,
                WaterColor.BROWN,
                WaterColor.DARK_GREEN
            ),
            Vial(
                WaterColor.LIGHT_BLUE,
                WaterColor.DARK_BLUE,
                WaterColor.BROWN,
            ),
            Vial.EMPTY
        )
        val board2 = Board(
            Vial(
                WaterColor.RED,
                WaterColor.BROWN,
                WaterColor.DARK_GREEN
            ),
            Vial(
                WaterColor.LIGHT_BLUE,
                WaterColor.DARK_BLUE,
                WaterColor.DARK_BLUE,
            ),
            Vial.EMPTY
        )
        assertEquals(true, boardTree.addBoard(board1))
        assertEquals(true, boardTree.addBoard(board2))


        val board3 = Board(
            Vial.EMPTY,
            Vial(
                WaterColor.RED,
                WaterColor.BROWN,
                WaterColor.DARK_GREEN
            ),
            Vial(
                WaterColor.LIGHT_BLUE,
                WaterColor.DARK_BLUE,
                WaterColor.BROWN,
            ),
        )
        val board4 = Board(
            Vial.EMPTY,
            Vial(
                WaterColor.RED,
                WaterColor.BROWN,
                WaterColor.DARK_GREEN
            ),
            Vial(
                WaterColor.LIGHT_BLUE,
                WaterColor.DARK_BLUE,
                WaterColor.DARK_BLUE,
            ),
        )
        assertEquals(false, boardTree.addBoard(board3))
        assertEquals(false, boardTree.addBoard(board4))
    }
}
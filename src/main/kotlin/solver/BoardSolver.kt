package solver

import model.Board
import model.Move

interface BoardSolver {
    fun solveBoard(board: Board): List<Move>
}


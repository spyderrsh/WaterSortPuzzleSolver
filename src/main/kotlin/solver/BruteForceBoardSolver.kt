package solver

import model.Board
import model.Move

object BruteForceBoardSolver : BoardSolver {
    override fun solveBoard(board: Board): List<Move> {
        return BreadthFirstBoardSolver(board).solve()
    }

}
package solver

import model.Board
import model.BoardTree
import model.Move
import java.util.*

class BreadthFirstBoardSolver(board: Board) {
    private val boardQueue =
        PriorityQueue<BoardState>(Comparator.comparingInt<BoardState> { it.turn }).apply {
            add(BoardState(board, 0, null, null))
        }

    private val boardTree = BoardTree()

    fun solve(): List<Move> {
        while (boardQueue.isNotEmpty()) {
            val boardState = boardQueue.remove()
            if (boardState.board.isSolved()) {
                return boardState.extractMoveList()
            } else {
                boardQueue.addAll(boardState.getAllNextBoards().filter { boardTree.addBoard(it.board) })
                println("Evaluation set = ${boardQueue.size}")
            }
        }
        throw IllegalStateException("No solutions found!!!")
    }

    inner class BoardState(
        val board: Board,
        val turn: Int,
        val previousMove: Move?,
        val previousBoardState: BoardState?
    ) {
        fun extractMoveList(): List<Move> {
            if (previousMove == null) {
                return emptyList()
            }
            if (previousBoardState == null)
                return listOf(previousMove)
            return previousBoardState.extractMoveList().plus(previousMove)
        }

        fun getAllNextBoards(): Collection<BoardState> {
            return board.possibleMoves.mapNotNull {
                BoardState(
                    board.applyMove(it) ?: return@mapNotNull null,
                    turn + 1,
                    it,
                    this
                )
            }
        }
    }

}

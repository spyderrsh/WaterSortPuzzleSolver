package model

data class Board(val vials: List<Vial>) {
    fun isSolved(): Boolean = vials.all { (it.isPure && it.isFull) || it.isEmpty }
    fun applyMove(move: Move): Board? {
        val sendVial = vials[move.pourFromIndex]
        val receiveVial = vials[move.pourToIndex]
        val (newVial1, newVial2) = sendVial.pourIntoOrNull(receiveVial) ?: return null
        return Board(vials.mapIndexed { index, vial ->
            when (index) {
                move.pourFromIndex -> newVial1
                move.pourToIndex -> newVial2
                else -> vial
            }
        })
    }

    constructor(vararg vials: Vial) : this(vials.toList())

    /**
     * Basic implementation to get all possible moves from the current Board
     */
    val possibleMoves: List<Move> by lazy {
        return@lazy vials.flatMapIndexed { sendIndex, sendVial ->
            return@flatMapIndexed if (sendVial.isEmpty) {
                emptyList()
            } else {
                return@flatMapIndexed vials.mapIndexedNotNull { receiverIndex, receiveVial ->
                    when {
                        sendIndex == receiverIndex -> null
                        receiveVial.canReceivePartialPour(sendVial.currentColorPourOrNull) -> Move(
                            sendIndex,
                            receiverIndex
                        )
                        else -> null
                    }
                }
            }
        }
    }
}

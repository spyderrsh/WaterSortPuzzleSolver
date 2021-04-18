package model

class BoardTree {
    private val rootNode = BoardTreeNode()
    fun addBoard(board: Board): Boolean {
        return rootNode.putIfUnique(board.vials.map { it.longHashCode }.sorted())
    }
}

data class BoardTreeNode(val children: MutableMap<ULong, BoardTreeNode> = HashMap()) {
    fun putIfUnique(vials: List<ULong>, index: Int = 0): Boolean {
        if (index >= vials.size)
            return false
        val vial = vials[index]
        val child = children[vial]
        return if (child == null) {
            children[vial] = BoardTreeNode().also {
                it.putIfUnique(vials, index + 1)
            }
            true
        } else {
            child.putIfUnique(vials, index + 1)
        }
    }
}
package model

import Constants
import util.stackOf
import java.util.*

data class Vial(val colors: Stack<WaterColor>, val maxCapacity: Short = Constants.VIAL_MAX_CAPACITY) {
    val isPure: Boolean = colors.all { it == colors.first() }
    val isFull: Boolean = colors.size.toShort() == maxCapacity

    fun canReceivePour(colorPour: ColorPour?): Boolean {
        return colorPour != null &&
                (colors.isEmpty() || colors.peek() == colorPour.color) &&
                colors.size + colorPour.size <= maxCapacity
    }

    val currentColorPourOrNull: ColorPour? by lazy {
        if (colors.isEmpty() || (isPure && isFull))
            return@lazy null
        val topColor = colors.peek()
        var pourSize = 1
        for (i: Int in (0 until (colors.size - 1)).reversed()) {
            if (colors[i] != topColor) {
                break
            }
            pourSize++
        }
        ColorPour(topColor, pourSize)
    }

    fun pourIntoOrNull(other: Vial): Vial? {
        if (other.canReceivePour(currentColorPourOrNull)){
            Vial.from(other, currentColorPourOrNull)
        }
    }

    companion object {
        val EMPTY: Vial = Vial(stackOf())

    }
}

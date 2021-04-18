package model

import Constants
import util.stackOf
import util.toStack
import java.util.*

data class Vial(val colors: Stack<WaterColor>, val maxCapacity: Int = Constants.VIAL_MAX_CAPACITY) {
    constructor(vararg colors: WaterColor, maxCapacity: Int = Constants.VIAL_MAX_CAPACITY) : this(
        stackOf(*colors),
        maxCapacity
    )

    val isEmpty: Boolean = colors.size == 0
    val isPure: Boolean = colors.all { it == colors.first() }
    val isFull: Boolean = colors.size == maxCapacity

    fun canReceiveFullPour(colorPour: ColorPour?): Boolean {
        return colorPour != null &&
                (colors.isEmpty() || colors.peek() == colorPour.color) &&
                colors.size + colorPour.size <= maxCapacity
    }

    fun canReceivePartialPour(colorPour: ColorPour?): Boolean {
        return colorPour != null && (isEmpty || (!this.isFull && colors.peek() == colorPour.color))
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

    private val vialFromRemovingPour: Vial by lazy {
        VIAL_TO_CHILD_MAP.getOrPut(this) {
            createVialFromRemovingPour()
        }
    }

    private fun createVialFromRemovingPour(): Vial {
        return currentColorPourOrNull?.let { colorPour ->
            if (isPure) {
                EMPTY
            } else {
                Vial(colors.dropLast(colorPour.size).toStack(), maxCapacity)
            }
        } ?: EMPTY
    }

    /**
     * @return <code>null</code> if the pour is invalid, otherwise a Pair of [Vial]s to replace the mixed [Vial]s. The first
     * value is result of the [Vial] sending the [ColorPour] and the second [Vial] is the result of receiving the vial.
     */
    fun pourIntoOrNull(other: Vial): Pair<Vial, Vial>? {
        val currentColorPour = currentColorPourOrNull ?: return null
        return if (currentColorPour != null && other.canReceiveFullPour(currentColorPour)) {
            val receivingVial = from(other, currentColorPour) ?: return null
            val sendingVial = this.vialFromRemovingPour
            return sendingVial to receivingVial
        } else if (other.canReceivePartialPour(currentColorPour)) {
            val spaceAvailable = other.maxCapacity - other.colors.size
            val partialColorPour = ColorPour(currentColorPour.color, spaceAvailable)
            val receivingVial = from(other, partialColorPour) ?: return null
            val sendingVial = unsafeRemove(this, spaceAvailable)
            return sendingVial to receivingVial
        } else {
            null
        }
    }

    override fun hashCode(): Int {
        var hash: Int = 0
        var placeHolder: Int = 0
        colors.onEach {
            hash += (it.ordinal + 1).shl(placeHolder)
            placeHolder += 5
        }
        return hash
    }

    val longHashCode: ULong by lazy {
        //TODO optimized for a 4 value vial or less
        var hash: ULong = 0u
        var placeHolder: ULong = 1u
        colors.onEach {
            hash += (it.ordinal + 1).toULong() * placeHolder
            placeHolder = placeHolder.shl(5)
        }
        return@lazy hash
    }


    companion object {
        val EMPTY: Vial = Vial()
        fun from(vial: Vial, colorPour: ColorPour): Vial? {
            return if (vial.canReceiveFullPour(colorPour)) {
                Vial(stackOf(*vial.colors.toTypedArray(), *(colorPour.toTypedArray())), vial.maxCapacity)
            } else {
                null
            }
        }

        private fun unsafeRemove(vial: Vial, size: Int): Vial {
            return Vial(vial.colors.dropLast(size).toStack(), vial.maxCapacity)
        }

        private val VIAL_TO_CHILD_MAP: MutableMap<Vial, Vial> = mutableMapOf()

    }
}


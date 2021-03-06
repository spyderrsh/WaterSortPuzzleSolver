package model

data class ColorPour(val color: WaterColor, val size: Int){
    fun toTypedArray(): Array<WaterColor> = Array(size) { color }

    init {
        if (size <= 0) {
            throw IllegalStateException("ColorPour must be greater than 0 (got $size)")
        }
    }
}

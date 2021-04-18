package model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import util.stackOf

internal class VialTest {

    @Test
    fun `Empty Vial should be able to receive valid pour`() {
        assert(Vial.EMPTY.canReceiveFullPour(ColorPour(WaterColor.BROWN, 4)))
        assert(Vial.EMPTY.canReceiveFullPour(ColorPour(WaterColor.BROWN, 3)))
        assert(Vial.EMPTY.canReceiveFullPour(ColorPour(WaterColor.BROWN, 2)))
        assert(Vial.EMPTY.canReceiveFullPour(ColorPour(WaterColor.BROWN, 1)))
    }

    @Test
    fun `An Invalid Pour should return false`() {
        assertFalse(Vial(stackOf(WaterColor.RED)).canReceiveFullPour(ColorPour(WaterColor.BROWN, 1)))
        assertFalse(Vial(stackOf(WaterColor.RED)).canReceiveFullPour(ColorPour(WaterColor.RED, 4)))
        assertFalse(Vial.EMPTY.canReceiveFullPour(ColorPour(WaterColor.RED, 5)))
        assertFalse(
            Vial(stackOf(WaterColor.RED, WaterColor.BROWN, WaterColor.RED, WaterColor.BROWN)).canReceiveFullPour(
                ColorPour(WaterColor.BROWN, 1)
            )
        )
    }

    @Test
    fun `A valid Pour should return true`() {
        assert(Vial(stackOf(WaterColor.BROWN)).canReceiveFullPour(ColorPour(WaterColor.BROWN, 3)))
        assert(
            Vial(stackOf(WaterColor.GREY, WaterColor.NEON_GREEN)).canReceiveFullPour(
                ColorPour(
                    WaterColor.NEON_GREEN,
                    2
                )
            )
        )
    }

    @Test
    fun `getColorPour from empty should be null`() {
        assertEquals(null, Vial.EMPTY.currentColorPourOrNull)
    }

    @Test
    fun `getColorPour from a full and pure vial should be null`() {
        assertEquals(
            null,
            Vial(
                stackOf(
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                )
            ).currentColorPourOrNull
        )
    }

    @Test
    fun `getColorPour returns only the count of the top color size`() {
        assertEquals(
            ColorPour(WaterColor.NEON_GREEN, 2),
            Vial(
                stackOf(
                    WaterColor.DARK_BLUE,
                    WaterColor.DARK_BLUE,
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                )
            ).currentColorPourOrNull
        )
        assertEquals(
            ColorPour(WaterColor.NEON_GREEN, 2),
            Vial(
                stackOf(
                    WaterColor.DARK_BLUE,
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                )
            ).currentColorPourOrNull
        )
        assertEquals(
            ColorPour(WaterColor.NEON_GREEN, 2),
            Vial(
                stackOf(
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                )
            ).currentColorPourOrNull
        )
        assertEquals(
            ColorPour(WaterColor.NEON_GREEN, 2),
            Vial(
                stackOf(
                    WaterColor.NEON_GREEN,
                    WaterColor.DARK_BLUE,
                    WaterColor.NEON_GREEN,
                    WaterColor.NEON_GREEN,
                )
            ).currentColorPourOrNull
        )
    }

    @Test
    fun `Valid pours return the correct vials`() {
        val senderVial1 = Vial(
            WaterColor.BROWN,
            WaterColor.RED,
            WaterColor.RED,
            WaterColor.RED
        )
        val receiverVial1 = Vial.EMPTY
        val receiverVial2 = Vial(
            WaterColor.RED
        )

        val answer1 = Vial(WaterColor.BROWN) to
                Vial(
                    WaterColor.RED,
                    WaterColor.RED,
                    WaterColor.RED
                )
        val answer2 = Vial(WaterColor.BROWN) to
                Vial(
                    WaterColor.RED,
                    WaterColor.RED,
                    WaterColor.RED,
                    WaterColor.RED,
                )

        assertEquals(
            answer1,
            senderVial1.pourIntoOrNull(receiverVial1)
        )
        assertEquals(
            answer2,
            senderVial1.pourIntoOrNull(receiverVial2)
        )

        val senderVial2 = Vial(
            WaterColor.RED,
            WaterColor.DARK_BLUE,
            WaterColor.RED,
            WaterColor.DARK_GREEN
        )
        val receiverVial3 = Vial(
            WaterColor.GREY,
            WaterColor.GREY,
            WaterColor.DARK_GREEN
        )

        val answer3 = Vial(
            WaterColor.RED,
            WaterColor.DARK_BLUE,
            WaterColor.RED,
        ) to
                Vial(
                    WaterColor.GREY,
                    WaterColor.GREY,
                    WaterColor.DARK_GREEN,
                    WaterColor.DARK_GREEN
                )
        assertEquals(
            answer3,
            senderVial2.pourIntoOrNull(receiverVial3)
        )
    }

    @Test
    fun `Partial pours work`() {
        val senderVial1 = Vial(
            WaterColor.BROWN,
            WaterColor.LIGHT_BLUE,
            WaterColor.RED,
            WaterColor.RED
        )

        val receiverVial1 = Vial(
            WaterColor.LIGHT_BLUE,
            WaterColor.LIGHT_BLUE,
            WaterColor.RED,
        )
        val answer1 = Vial(
            WaterColor.BROWN,
            WaterColor.LIGHT_BLUE,
            WaterColor.RED
        ) to
                Vial(
                    WaterColor.LIGHT_BLUE,
                    WaterColor.LIGHT_BLUE,
                    WaterColor.RED,
                    WaterColor.RED
                )
        assertEquals(
            answer1,
            senderVial1.pourIntoOrNull(receiverVial1)
        )
    }

    @Test
    fun `longHashCode`() {
        val allUniqueVials: List<Vial> = WaterColor.values().flatMap { color1 ->
            WaterColor.values().flatMap { color2 ->
                WaterColor.values().flatMap { color3 ->
                    WaterColor.values().map { color4 ->
                        Vial(color1, color2, color3, color4)
                    }.plus(Vial(color1, color2, color3))
                }.plus(Vial(color1, color2))
            }.plus(Vial(color1))
        }.plus(Vial.EMPTY)

        println("Evaluating ${allUniqueVials.size} unique vials")
        assertEquals(allUniqueVials.size, allUniqueVials
            .map {
                it.hashCode()
            }
            .toSortedSet().size)

    }


}
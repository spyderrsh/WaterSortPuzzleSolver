package model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import util.stackOf

internal class VialTest {

    @Test
    fun `Empty Vial should be able to receive valid pour`() {
        assert(Vial.EMPTY.canReceivePour(ColorPour(WaterColor.BROWN, 4)))
        assert(Vial.EMPTY.canReceivePour(ColorPour(WaterColor.BROWN, 3)))
        assert(Vial.EMPTY.canReceivePour(ColorPour(WaterColor.BROWN, 2)))
        assert(Vial.EMPTY.canReceivePour(ColorPour(WaterColor.BROWN, 1)))
    }

    @Test
    fun `An Invalid Pour should return false`() {
        assertFalse(Vial(stackOf(WaterColor.RED)).canReceivePour(ColorPour(WaterColor.BROWN, 1)))
        assertFalse(Vial(stackOf(WaterColor.RED)).canReceivePour(ColorPour(WaterColor.RED, 4)))
        assertFalse(Vial.EMPTY.canReceivePour(ColorPour(WaterColor.RED, 5)))
        assertFalse(
            Vial(stackOf(WaterColor.RED, WaterColor.BROWN, WaterColor.RED, WaterColor.BROWN)).canReceivePour(
                ColorPour(WaterColor.BROWN, 1)
            )
        )
    }

    @Test
    fun `A valid Pour should return true`() {
        assert(Vial(stackOf(WaterColor.BROWN)).canReceivePour(ColorPour(WaterColor.BROWN, 3)))
        assert(
            Vial(stackOf(WaterColor.GREY, WaterColor.NEON_GREEN)).canReceivePour(
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


}
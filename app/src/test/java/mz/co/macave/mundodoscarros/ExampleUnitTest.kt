package mz.co.macave.mundodoscarros

import mz.co.macave.mundodoscarros.utils.Converter
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun isConvertCorrect() {
        val otherCurrency = Converter.toOtherCurrency("R$ 5.000,00", 5.0)
        assertEquals("25_000.0", otherCurrency, 0.0)
    }
}
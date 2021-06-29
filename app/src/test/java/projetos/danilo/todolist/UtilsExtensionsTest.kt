package projetos.danilo.todolist

import org.junit.Assert
import org.junit.Test
import projetos.danilo.todolist.extensions.dayOfWeekBR
import java.util.*

class UtilsExtensionsTest {

    @Test
    fun `validaDateDayOfWeek - quando for terça feira`() {
        val data = "29/06/2021"
        val esperado = "Ter"
        Assert.assertEquals(esperado, Date().dayOfWeekBR(data))
    }

    @Test
    fun `validaDateDayOfWeek - quando for doming`() {
        val data = "27/06/2021"
        val esperado = "Dom"
        Assert.assertEquals(esperado, Date().dayOfWeekBR(data))
    }

}
package com.genaku.router

import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.*

class BaseRouterScreensTest {

    @Test
    fun `empty screens returns empty screen map`() {
        val s = BaseRouterScreens<TestScreen>()
        val allScreens = s.getAllScreens()
        assertEquals(emptyMap<Long, TestScreen>(), allScreens)
    }

    @Test
    fun `after screen added to empty screens it returns map of this screen`() {
        val s = BaseRouterScreens<TestScreen>()
        val expectedScreen = TestScreen(TestScreenParams(0))
        val uid = s.addScreen(expectedScreen)
        val allScreens = s.getAllScreens()
        assertEquals(mapOf(uid to expectedScreen), allScreens)
    }

    @Test
    fun `after 2 screens added to empty screens it returns map of these screens`() {
        val s = BaseRouterScreens<TestScreen>()
        val expectedScreen1 = TestScreen(TestScreenParams(0))
        val uid1 = s.addScreen(expectedScreen1)
        val expectedScreen2 = TestScreen(TestScreenParams(1))
        val uid2 = s.addScreen(expectedScreen2)
        val allScreens = s.getAllScreens()
        assertEquals(mapOf(uid1 to expectedScreen1, uid2 to expectedScreen2), allScreens)
    }

    @Test
    fun `delete not stored screen from screens with 2 elements dont change screens`() {
        val s = BaseRouterScreens<TestScreen>()
        val expectedScreen1 = TestScreen(TestScreenParams(0))
        val uid1 = s.addScreen(expectedScreen1)
        val expectedScreen2 = TestScreen(TestScreenParams(1))
        val uid2 = s.addScreen(expectedScreen2)
        val allScreens = s.getAllScreens()
        assertEquals(mapOf(uid1 to expectedScreen1, uid2 to expectedScreen2), allScreens)
        s.deleteScreen(UUID.randomUUID())
        val allScreens2 = s.getAllScreens()
        assertEquals(mapOf(uid1 to expectedScreen1, uid2 to expectedScreen2), allScreens2)
    }

    @Test
    fun `after delete 1 screen from screens with 2 element it returns map of the rest screen`() {
        val s = BaseRouterScreens<TestScreen>()
        val expectedScreen1 = TestScreen(TestScreenParams(0))
        val uid1 = s.addScreen(expectedScreen1)
        val expectedScreen2 = TestScreen(TestScreenParams(1))
        val uid2 = s.addScreen(expectedScreen2)
        val allScreens = s.getAllScreens()
        assertEquals(mapOf(uid1 to expectedScreen1, uid2 to expectedScreen2), allScreens)
        s.deleteScreen(uid1)
        val allScreens2 = s.getAllScreens()
        assertEquals(mapOf(uid2 to expectedScreen2), allScreens2)
    }

    @Test
    fun `check getScreen by uuid`() {
        val s = BaseRouterScreens<TestScreen>()
        val expectedScreen1 = TestScreen(TestScreenParams(0))
        val uid1 = s.addScreen(expectedScreen1)
        val expectedScreen2 = TestScreen(TestScreenParams(1))
        val uid2 = s.addScreen(expectedScreen2)

        assertEquals(expectedScreen1, s.getScreenOrNull(uid1))
        assertEquals(expectedScreen2, s.getScreenOrNull(uid2))
        assertNotEquals(expectedScreen1, s.getScreenOrNull(UUID.randomUUID()))
    }

    @Test
    fun `check add screens`() {
        val s = BaseRouterScreens<TestScreen>()
        val expectedScreen1 = TestScreen(TestScreenParams(0))
        val uid1 = s.addScreen(expectedScreen1)
        val expectedScreen2 = TestScreen(TestScreenParams(1))
        val uid2 = s.addScreen(expectedScreen2)
        val allScreens = s.getAllScreens()
        assertEquals(mapOf(uid1 to expectedScreen1, uid2 to expectedScreen2), allScreens)

        val expectedScreen3 = TestScreen(TestScreenParams(2))
        val uid3 = s.addScreen(expectedScreen3)
        val expectedScreen4 = TestScreen(TestScreenParams(3))
        val uid4 = s.addScreen(expectedScreen3)
        val expectedScreen5 = TestScreen(TestScreenParams(4))
        val uid5 = s.addScreen(expectedScreen3)
        val newScreens = mapOf(uid3 to expectedScreen3, uid4 to expectedScreen4, uid5 to expectedScreen5)
        s.addScreens(newScreens)

        assertEquals(mapOf(uid1 to expectedScreen1, uid2 to expectedScreen2, uid3 to expectedScreen3, uid4 to expectedScreen4, uid5 to expectedScreen5), s.getAllScreens())
    }

    inner class TestScreen(override val params: TestScreenParams) : RouterScreen {

        @Transient
        override val resultStateFlow: MutableStateFlow<ScreenResult> = MutableStateFlow(
            EmptyScreenResult
        )
    }

    data class TestScreenParams(val id: Int) : ScreenParams

}
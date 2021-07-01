package com.genaku.router

import app.cash.turbine.test
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class StorableCommandFlowTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    inner class MorozovStorableCommandFlow<C : RouterCommand>(dispatcher: CoroutineDispatcher) :
        StorableCommandFlow<C>(dispatcher) {
        public override fun pullAllCommandsAndPauseFlow(): List<C> =
            super.pullAllCommandsAndPauseFlow()

        public override fun addCommandsAndResumeFlow(storedCommands: List<C>) =
            super.addCommandsAndResumeFlow(storedCommands)
    }

    @Test
    fun `проверим, отправляются ли команды через flow`() = runBlockingTest {
        val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
        q.send(Comm.First)
        q.send(Comm.Second)
        q.commandFlow.test {
            assertEquals(Comm.First, expectItem())
            assertEquals(Comm.Second, expectItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `проверим, получаем ли мы список неотработанных команд`() = testScope.runBlockingTest {
        val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
        q.send(Comm.First)
        q.send(Comm.Second)
        val toStore = q.pullAllCommandsAndPauseFlow()
        assertEquals(listOf(Comm.First, Comm.Second), toStore)
        q.commandFlow.test { cancelAndIgnoreRemainingEvents() }
    }

    @Test
    fun `getCommandsToStore должен вернуть список неотработанных команд и удалить их из очереди`() =
        testScope.runBlockingTest {
            val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
            q.send(Comm.First)
            q.send(Comm.Second)
            val toStore = q.pullAllCommandsAndPauseFlow()
            assertEquals(listOf(Comm.First, Comm.Second), toStore)
            q.commandFlow.test { cancelAndIgnoreRemainingEvents() }

            q.addCommandsAndResumeFlow(emptyList())
            q.send(Comm.Second)
            q.send(Comm.First)
            q.commandFlow.test {
                assertEquals(Comm.Second, expectItem())
                assertEquals(Comm.First, expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `setCommandsFromStore в пустую очередь команд должен добавить новые команды`() =
        testScope.runBlockingTest {
            val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
            q.addCommandsAndResumeFlow(listOf(Comm.First, Comm.Second))
            q.commandFlow.test {
                assertEquals(Comm.First, expectItem())
                assertEquals(Comm.Second, expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `после восстановления очереди через setCommandsFromStore добавление новой команды должно поместить ее в конец очереди`() =
        testScope.runBlockingTest {
            val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
            q.addCommandsAndResumeFlow(listOf(Comm.First, Comm.Second))
            q.send(Comm.Third)
            q.commandFlow.test {
                assertEquals(Comm.First, expectItem())
                assertEquals(Comm.Second, expectItem())
                assertEquals(Comm.Third, expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `после восстановления очереди через setCommandsFromStore проверим, что они добавились и отдаются через flow, и затем добавление команды работает корректно`() =
        testScope.runBlockingTest {
            val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
            q.addCommandsAndResumeFlow(listOf(Comm.First, Comm.Second))
            q.commandFlow.test {
                assertEquals(Comm.First, expectItem())
                assertEquals(Comm.Second, expectItem())
                cancelAndIgnoreRemainingEvents()
            }
            q.send(Comm.Third)
            q.commandFlow.test {
                assertEquals(Comm.Third, expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `если до восстановления состояния успели отправить команду, она должна оказаться в конце, после сохраненного списка команд`() =
        testScope.runBlockingTest {
            val q = MorozovStorableCommandFlow<Comm>(dispatcher = testDispatcher)
            q.send(Comm.Third)
            q.addCommandsAndResumeFlow(listOf(Comm.First, Comm.Second))
            q.commandFlow.test {
                assertEquals(Comm.First, expectItem())
                assertEquals(Comm.Second, expectItem())
                assertEquals(Comm.Third, expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    sealed class Comm : RouterCommand {
        object First : Comm()
        object Second : Comm()
        object Third : Comm()
    }
}
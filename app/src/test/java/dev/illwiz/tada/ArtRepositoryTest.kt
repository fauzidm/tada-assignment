package dev.illwiz.tada

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dev.illwiz.tada.data.repository.art.ArtRepository
import dev.illwiz.tada.data.repository.remote.ArtAPI
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@SmallTest
@RunWith(JUnit4::class)
class ArtRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun artRepositoryGetAllArtShouldDoRestNetworkCall() = runBlocking {
        // Arrange
        val artAPI = mockk<ArtAPI>(relaxed = true)
        val artRepository = ArtRepository(artAPI)
        // Act
        artRepository.getAllArt(0,10)
        // Assert
        coVerify(exactly = 1) { artAPI.getArtCollection(0,10) }
    }

    @Test
    fun artRepositoryGetArtByObjectNumberShouldDoRestNetworkCall() = runBlocking {
        // Arrange
        val artAPI = mockk<ArtAPI>(relaxed = true)
        val artRepository = ArtRepository(artAPI)
        // Act
        artRepository.getArtByObjectNumber("abcd")
        // Assert
        coVerify(exactly = 1) { artAPI.getArt("abcd") }
    }
}
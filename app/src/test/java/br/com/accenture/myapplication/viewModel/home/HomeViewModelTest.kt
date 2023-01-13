@file:Suppress("DEPRECATION")

package br.com.accenture.myapplication.viewModel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import s8u.studies.myapplication.api.PokedexEndpoint
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.repository.PokedexRepository
import s8u.studies.myapplication.viewModel.HomeViewModel
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import s8u.studies.myapplication.model.Pokedex.Pokedex
import s8u.studies.myapplication.model.Pokedex.PokedexSpecies

class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockPokedexEndpoint: PokedexEndpoint
    private lateinit var mockPokedexTypeEndpoint: PokedexTypeEndpoint
    private lateinit var mockPokemonTypeEndpoint: PokemonTypeEndpoint
    private lateinit var repository: PokedexRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var observerEntries: Observer<ArrayList<PokedexEntries>>
    private val dispatcher = TestCoroutineDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockPokedexEndpoint = mockk()
        mockPokedexTypeEndpoint = mockk()
        mockPokemonTypeEndpoint = mockk()
        repository = PokedexRepository(
            mockPokedexEndpoint,
            mockPokedexTypeEndpoint,
            mockPokemonTypeEndpoint
        )
        viewModel = HomeViewModel(repository)
        observerEntries = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When inserting the id of a region, should return a list of pokemons`() = runBlocking {
        viewModel.listPokedexEntriesLiveData.observeForever(observerEntries)
        coEvery { repository.getPokedex("1") } returns Pokedex(
            arrayListOf(
                PokedexEntries(
                    1,
                    PokedexSpecies(
                        "bulbassaur",
                        "https://pokeapi.co/api/v2/pokemon-species/1/"
                    )
                )
            )
        )

        viewModel.getPokedexEntriesList(1)

        coVerify {
            observerEntries.onChanged(
                arrayListOf(
                    PokedexEntries(
                        1,
                        PokedexSpecies(
                            "bulbassaur",
                            "https://pokeapi.co/api/v2/pokemon-species/1/"
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `When showing the list, should validate if it is a filtered list`() {
    }

    @Test
    fun `When the api makes a request, should trigger the loading screen`() {
        viewModel.setLoadingState(true)

        assertEquals(viewModel.loadingPokeballTrue.value, Unit)
    }

    @Test
    fun `When the api finishes the request, should remove the loading screen`() {
        viewModel.setLoadingState(false)

        assertEquals(viewModel.loadingPokeballFalse.value, Unit)
    }
}
@file:Suppress("DEPRECATION")

package br.com.accenture.myapplication.viewModel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import s8u.studies.myapplication.api.PokedexEndpoint
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.repository.PokedexRepository
import s8u.studies.myapplication.viewModel.HomeViewModel
import s8u.studies.myapplication.model.Pokemon.PokedexTypes as PokedexTypesFromPokemon
import s8u.studies.myapplication.model.Pokemon.PokemonTypes as PokemonTypesFromPokemon
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import s8u.studies.myapplication.model.Pokedex.*
import s8u.studies.myapplication.model.Pokemon.PokemonTypeList

class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockPokedexEndpoint: PokedexEndpoint
    private lateinit var mockPokedexTypeEndpoint: PokedexTypeEndpoint
    private lateinit var mockPokemonTypeEndpoint: PokemonTypeEndpoint
    private lateinit var repository: PokedexRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var observerEntries: Observer<ArrayList<PokedexEntries>>
    private lateinit var observerTypes: Observer<PokemonTypes>
    private lateinit var observerTypesList: Observer<ArrayList<PokedexTypesFromPokemon>>
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
        observerTypes = mockk(relaxed = true)
        observerTypesList = mockk(relaxed = true)
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
    fun `When click on a type of Pokemon, should return a list of filtered pokemons`() {
        viewModel.listPokedexFilteredLiveData.observeForever(observerTypes)
        coEvery { repository.getPokemonType("1") } returns PokemonTypes(
            "normal",
            arrayListOf(
                PokedexTypes(
                    PokedexSpecies(
                        "pidgey",
                        "https://pokeapi.co/api/v2/pokemon/16/"
                    )
                )
            )
        )

        viewModel.getPokedexFilteredList("1")

        coVerify {
            observerTypes.onChanged(
                PokemonTypes(
                    "normal",
                    arrayListOf(
                        PokedexTypes(
                            PokedexSpecies(
                                "pidgey",
                                "https://pokeapi.co/api/v2/pokemon/16/"
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `When receiving a Pokemon and its type, should return a pokedexEntries`() {
        val pokemonIn = PokedexSpecies(
            "pidgey",
            "https://pokeapi.co/api/v2/pokemon/16/"
        )
        val pokemonOut = PokedexSpecies(
            "pidgey",
            "https://pokeapi.co/api/v2/pokemon/16/"
        )

        assertEquals(
            viewModel.updateLiveData(pokemonIn, 16),
            PokedexEntries(16, pokemonOut)
        )
    }

    @Test
    fun `When you receive a list of Pokemons, should insert the list in livedata`() {
        val listPokemonsIn = arrayListOf(
            PokedexEntries(
                16,
                PokedexSpecies(
                    "pidgey",
                    "https://pokeapi.co/api/v2/pokemon/16/"
                )
            )
        )

        val listPokemonsOut = arrayListOf(
            PokedexEntries(
                16,
                PokedexSpecies(
                    "pidgey",
                    "https://pokeapi.co/api/v2/pokemon/16/"
                )
            )
        )

        viewModel.setLiveEntries(listPokemonsIn)

        assertEquals(viewModel.listPokedexEntriesLiveData.value, listPokemonsOut)
    }

    @Test
    fun `When I Cry`() = runBlockingTest {
        viewModel.listPokedexTypesLiveData.observeForever(observerTypesList)

        coEvery { arrayListOf(repository.getPokedexType("1")) } returns
                arrayListOf(
                    PokedexTypesFromPokemon(
                        arrayListOf(
                            PokemonTypesFromPokemon(
                                PokemonTypeList(
                                    "bulbassaur"
                                )
                            )
                        ),
                        "1"
                    )
                )

        viewModel.getPokedexTypesList()

        coVerify {
            observerTypesList.onChanged(
                arrayListOf(
                    PokedexTypesFromPokemon(
                        arrayListOf(
                            PokemonTypesFromPokemon(
                                PokemonTypeList(
                                    "bulbassaur"
                                )
                            )
                        ),
                        "1"
                    )
                )
            )
        }
    }

    @Test
    fun `When a certain API name is pulled, if you enter the conditional, should change the name`() {
        val pokemonName = "tornadus-incarnate"
        val pokemon = PokedexEntries(
            id = 641,
            PokedexSpecies(
                pokemonName = "tornadus",
                url = "https://pokeapi.co/api/v2/pokemon/641"
            )
        )

        viewModel.solveApiProblems(pokemon)

        assertEquals(pokemon.pokedexSpecies.pokemonName, pokemonName)
    }

    @Test
    fun `When showing the list, should validate if it is a filtered list`() {
        var enteredLambda = 0
        viewModel.isFiltered(1) {
            enteredLambda = 1
        }

        assertEquals(enteredLambda, 1)
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
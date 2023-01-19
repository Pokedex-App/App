@file:Suppress("DEPRECATION")

package br.com.accenture.myapplication.viewModel.description

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import s8u.studies.myapplication.api.PokemonAbilityEndpoint
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.model.Pokemon.*
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescription
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescriptionOfficial
import s8u.studies.myapplication.model.Pokemon.abilities.*
import s8u.studies.myapplication.model.PokemonData
import s8u.studies.myapplication.repository.DescriptionRepository
import s8u.studies.myapplication.viewModel.DescriptionViewModel

class DescriptionViewModelTest {
    @Rule @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var mockPokemonEndpoint: PokemonEndpoint
    private lateinit var mockPokemonDescriptionEndpoint: PokemonDescriptionEndpoint
    private lateinit var mockPokemonAbilityEndpoint: PokemonAbilityEndpoint
    private lateinit var repository: DescriptionRepository
    private lateinit var viewModel: DescriptionViewModel
    private lateinit var observerPokemon: Observer<PokemonData>
    private lateinit var observerAbility: Observer<PokemonAbilityInformation>
    private lateinit var observerButtonFirst: Observer<Unit>
    private lateinit var observerButtonLast: Observer<Unit>
    private val dispatcher = TestCoroutineDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockPokemonEndpoint = mockk()
        mockPokemonDescriptionEndpoint = mockk()
        mockPokemonAbilityEndpoint = mockk()
        repository = DescriptionRepository(
            mockPokemonEndpoint, mockPokemonDescriptionEndpoint, mockPokemonAbilityEndpoint
        )
        viewModel = DescriptionViewModel(repository)
        observerPokemon = mockk(relaxed = true)
        observerAbility = mockk(relaxed = true)
        observerButtonFirst = mockk(relaxed = true)
        observerButtonLast = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When I Cry`() = runBlocking {
        viewModel.apiData.observeForever(observerPokemon)

        val poke = Pokemon(
            1, "bulbasaur", "7", "69",
            arrayListOf(PokemonTypes(PokemonTypeList("grass"))),
            PokemonImgList(
                PokemonImgOfficial(
                    PokemonImg(
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
                    )
                )
            ),
            arrayListOf(PokemonMoves(PokemonMove("razor-wind")))
        )
        val pokeDesc = PokemonDescription(
            arrayListOf(
                PokemonDescriptionOfficial(
                    "A strange seed was\nplanted on its\nback at birth.\nThe plant sprouts\nand grows with\nthis POKéMON."
                )
            ),
            PokemonEvolutions(null.toString())
        )

        coEvery { repository.getPokemon("1") } returns poke
        coEvery { repository.getPokemonDesc("1") } returns pokeDesc

        viewModel.getPokemonDescription("1", "1", "151")

        coVerify {
            observerPokemon.onChanged(
                PokemonData(
                    poke.id,
                    poke.name,
                    poke.height,
                    poke.weight,
                    poke.typeList,
                    poke.imgList,
                    poke.movesList,
                    pokeDesc.pastEvolution,
                    pokeDesc.descriptionList
                )
            )
        }
    }

    @Test
    fun `When you click to view a skill, should enter the skill information in liveData`() = runBlocking {
        viewModel.abilityInformationPokemon.observeForever(observerAbility)

        val abilityPokemonIn = PokemonAbilityInformation(
            arrayListOf(
                AbilityDescription(
                    "1st turn: Prepare\\n2nd turn: Attack"
                )
            ),
            null,
            AbilityType(
                "razor-wind"
            ),
            AbilityDamage(
                "special"
            )
        )

        val abilityPokemonOut = PokemonAbilityInformation(
            arrayListOf(
                AbilityDescription(
                    "1st turn: Prepare\\n2nd turn: Attack"
                )
            ),
            null,
            AbilityType(
                "razor-wind"
            ),
            AbilityDamage(
                "special"
            )
        )

        coEvery { repository.getPokemonAbility("razor-wind") } returns abilityPokemonIn

        viewModel.getAbilityInformation("razor-wind")

        coVerify {
            observerAbility.onChanged(
                abilityPokemonOut
            )
        }
    }

    @Test
    fun `When you reach the first pokemon in the list, should enter a Unit value in the livedata`() {
        viewModel.hideButtons("1", "1", "151")

        assertEquals(viewModel.pokemonLiveData1.value, Unit)
    }

    @Test
    fun `When you reach the last pokemon in the list, should enter a Unit value in the livedata`() {
        viewModel.hideButtons("151", "1", "151")

        assertEquals(viewModel.pokemonLiveData2.value, Unit)
    }

    @Test
    fun `When receiving a list and its size is equal to 1, should return an xml value so that it sums up an item on the screen`() {
        assertEquals(viewModel.visibilitySecondaryType(1), View.GONE)
    }

    @Test
    fun `When receiving a list and its size is greater than 1, should return an xml value to show an item on the screen`() {
        assertEquals(viewModel.visibilitySecondaryType(2), View.VISIBLE)
    }

    @Test
    fun `When the Pokemon has two types, should perform a behavior method`() {
        var enteredLambda = 0
        viewModel.existsSecondaryType(2) {
            enteredLambda = 1
        }
        assertEquals(enteredLambda, 1)
    }

    @Test
    fun `When the api makes a request, should trigger the loading screen`() {
        viewModel.setLoadingState(true)

        Assert.assertEquals(viewModel.loadingPokeballTrue.value, Unit)
    }

    @Test
    fun `When the api finishes the request, should remove the loading screen`() {
        viewModel.setLoadingState(false)

        Assert.assertEquals(viewModel.loadingPokeballFalse.value, Unit)
    }
}
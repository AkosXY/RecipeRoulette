package com.example.reciperoulette

import com.example.reciperoulette.data.model.RecipeEntity
import com.example.reciperoulette.utils.createIngredientsString
import com.example.reciperoulette.utils.createInstructionsString
import com.example.reciperoulette.utils.createShortSummary
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
class RecipeViewModelTest {

    @Test
    fun testCreateIngredientsString() {
        val recipeEntity = RecipeEntity(
            title = "Test Recipe",
            dishType = "Main Course",
            summary = "A simple test recipe.",
            ingredients = "1 cup of flour, 2 eggs",
            instructions = "Mix ingredients. Bake for 20 minutes.",
            image = "test_image.jpg"
        )
        val result = createIngredientsString(recipeEntity)
        assertEquals("1 cup of flour, 2 eggs", result)
    }

    @Test
    fun testCreateInstructionsString() {
        val recipeEntity = RecipeEntity(
            title = "Test Recipe",
            dishType = "Main Course",
            summary = "A simple test recipe.",
            ingredients = "1 cup of flour, 2 eggs",
            instructions = "Mix ingredients. Bake for 20 minutes.",
            image = "test_image.jpg"
        )
        val result = createInstructionsString(recipeEntity)
        assertEquals("Mix ingredients. Bake for 20 minutes.", result)
    }

    @Test
    fun testCreateShortSummaryWithValidSummary() {
        val fullSummary = "This is a test recipe."
        val result = createShortSummary(fullSummary)
        assertEquals("This is a test recipe.", result)
    }

    @Test
    fun testCreateShortSummaryWithNullSummary() {
        val fullSummary: String? = null
        val result = createShortSummary(fullSummary)
        assertEquals("", result)
    }

    @Test
    fun testCreateShortSummaryWithEmptySummary() {
        val fullSummary = ""
        val result = createShortSummary(fullSummary)
        assertEquals("", result)
    }

    @Test
    fun testCreateShortSummaryWithHTMLTags() {
        val fullSummary = "<p>This is a test recipe.</p> It is very simple! <b>Enjoy cooking.</b>"
        val result = createShortSummary(fullSummary)
        assertEquals("This is a test recipe.", result)
    }

    @Test
    fun testCreateShortSummaryFromLongWithHTMLTags() {
        val fullSummary = "<p>This is a test recipe. It is very simple! Enjoy cooking.</p> It is very simple! <b>Enjoy cooking.</b>"
        val result = createShortSummary(fullSummary)
        assertEquals("This is a test recipe.", result)
    }

    @Test
    fun testCreateShortSummaryWithMultipleSentences() {
        val fullSummary = "This is a test recipe. It is very simple. Enjoy cooking."
        val result = createShortSummary(fullSummary)
        assertEquals("This is a test recipe.", result)
    }

    @Test
    fun testCreateShortSummaryWithComplexHTML() {
        val fullSummary = "<div><p>This is a test recipe. It is <em>very</em> simple!</p></div><b>Enjoy cooking.</b>"
        val result = createShortSummary(fullSummary)
        assertEquals("This is a test recipe.", result)
    }

    @Test
    fun testCreateShortSummaryWithVeryLongSummary() {
        val fullSummary = "This is a test recipe. ".repeat(100)
        val result = createShortSummary(fullSummary)
        assertEquals("This is a test recipe.", result)
    }

}
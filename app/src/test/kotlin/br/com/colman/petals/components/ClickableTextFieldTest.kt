package br.com.colman.petals.components

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import br.com.colman.petals.R
import br.com.colman.petals.utils.truncatedToMinute
import compose.icons.TablerIcons
import compose.icons.tablericons.Clock
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime

class ClickableTextFieldTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun clickableTextField_ShouldDisplayLabel() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val testLabelString = context.getString(R.string.start_time)
    composeTestRule.setContent {
      ClickableTextField(
        label = R.string.start_time, // Actual resource ID
        leadingIcon = TablerIcons.Clock,
        value = LocalTime.now().truncatedToMinute().toString(),
        onClick = {}
      )
    }
    composeTestRule.onNodeWithText(testLabelString).assertIsDisplayed()
  }

  @Test
  fun clickableTextField_ShouldRespondToClicks() {
    val mockOnClick = mockk<() -> Unit>(relaxed = true)
    val context = ApplicationProvider.getApplicationContext<Context>()
    val testLabelString = context.getString(R.string.start_time)

    composeTestRule.setContent {
      ClickableTextField(
        label = R.string.start_time,
        leadingIcon = TablerIcons.Clock,
        value = LocalTime.now().truncatedToMinute().toString(),
        onClick = mockOnClick
      )
    }
    composeTestRule.onNodeWithText(testLabelString).performClick()
    // Verify that the onClick function was called
    verify(exactly = 1) { mockOnClick.invoke() }
  }
  @Test
  fun clickableTextField_ShouldUseModifier() {
    val testModifier = Modifier.testTag("testTag")

    composeTestRule.setContent {
      ClickableTextField(
        label = R.string.start_time, // Actual resource ID
        leadingIcon = TablerIcons.Clock,
        value = LocalTime.now().truncatedToMinute().toString(),
        modifier = testModifier,
        onClick = {}
      )
    }
    // Assert modifier is used
    composeTestRule.onNodeWithTag("testTag").assertIsDisplayed()
  }

}

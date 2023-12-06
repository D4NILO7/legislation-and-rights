package br.com.colman.petals.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class SettingsViewKtTest{

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun setAppCurrencyTest() {
    composeTestRule.setContent {
      CurrencyListItem(currency = "$", setCurrency = {})
    }
    composeTestRule.onNodeWithText("Currency Icon").assertIsDisplayed()
    composeTestRule.onNodeWithText("Currency Icon").performClick()
    composeTestRule.onNodeWithText("$").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()
    composeTestRule.onNodeWithText("Currency Icon").assertIsDisplayed()
  }
  @Test
  fun setAppDateFormatDialogTest() {
    var selectedDate: String = "dd/MM/yyyy"
    val setDate: (String) -> Unit = { date ->
      selectedDate = date
    }
    composeTestRule.setContent {
      DateListItem(
        dateFormat = selectedDate,
        dateFormatList = listOf(
          "dd/MM/yyyy",
          "yyyy-MM-dd",
          "MM/dd/yyyy",
          "yyyy/MM/dd",
          "dd-MM-yyyy",
          "dd.MM.yyyy",
          "MM-dd-yyyy"
        ),
        setDateFormat = setDate
      )
    }
    composeTestRule.onNodeWithText("Date format").assertIsDisplayed()
    composeTestRule.onNodeWithText("Date format").performClick()
    composeTestRule.onNodeWithText("dd/MM/yyyy").assertIsDisplayed()
    composeTestRule.onNodeWithText("dd/MM/yyyy").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("MM/dd/yyyy").assertIsDisplayed()
  }
  @Test
  fun setAppDateFormatTest() {
    var selectedDate: String = "dd/MM/yyyy"
    val setDate: (String) -> Unit = { date ->
      selectedDate = date
    }
    composeTestRule.setContent {
      DateListItem(
        dateFormat = selectedDate,
        dateFormatList = listOf(
          "dd/MM/yyyy",
          "yyyy-MM-dd",
          "MM/dd/yyyy",
          "yyyy/MM/dd",
          "dd-MM-yyyy",
          "dd.MM.yyyy",
          "MM-dd-yyyy"
        ),
        setDateFormat = setDate
      )
    }
    composeTestRule.onNodeWithText("Date format").performClick()
    composeTestRule.onNodeWithText("dd/MM/yyyy").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("MM/dd/yyyy").performClick()
    composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()
    assert(selectedDate == "MM/dd/yyyy")
  }

  @Test
  fun setTimeFormatDialogTest(){
    var selectedTime: String = "HH:mm"
    var timeFormatList = listOf("HH:mm", "KK:mm a", "HH:mm:ss", "KK:mm:ss a")
    val setTimeFormat: (String) -> Unit = { time ->
      selectedTime = time
    }
    composeTestRule.setContent {
      TimeListItem(
        timeFormat = selectedTime,
        timeFormatList = timeFormatList,
        setTimeFormat = setTimeFormat
      )
    }
    composeTestRule.onNodeWithText("Time format").assertIsDisplayed()
    composeTestRule.onNodeWithText("Time format").performClick()
    composeTestRule.onNodeWithText("HH:mm").assertIsDisplayed()
    composeTestRule.onNodeWithText("HH:mm").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KK:mm a").assertIsDisplayed()
  }

  @Test
  fun setTimeFormatTest(){
    var selectedTime: String = "HH:mm"
    var timeFormatList = listOf("HH:mm", "KK:mm a", "HH:mm:ss", "KK:mm:ss a")
    val setTimeFormat: (String) -> Unit = { time ->
      selectedTime = time
    }
    composeTestRule.setContent {
      TimeListItem(
        timeFormat = selectedTime,
        timeFormatList = timeFormatList,
        setTimeFormat = setTimeFormat
      )
    }
    composeTestRule.onNodeWithText("Time format").performClick()
    composeTestRule.onNodeWithText("HH:mm").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KK:mm a").assertIsDisplayed()
    composeTestRule.onNodeWithText("KK:mm a").performClick()
    composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()
    assert(selectedTime == "KK:mm a")
  }

  @Test
  fun setDecimalPrecisionDialogTest(){
    var decimalPrecision = 2
    var decimalPrecisionList = listOf(0, 1, 2, 3)
    val setDecimalPrecision: (Int) -> Unit = { precision ->
      decimalPrecision = precision
    }

    composeTestRule.setContent {
      PrecisionListItem(
        decimalPrecision = decimalPrecision,
        decimalPrecisionList = decimalPrecisionList,
        setDecimalPrecision = setDecimalPrecision
      )
    }

    composeTestRule.onNodeWithText("Decimal Precision").assertIsDisplayed()
    composeTestRule.onNodeWithText("Decimal Precision").performClick()
    composeTestRule.onNodeWithText("2").assertIsDisplayed()
  }

  @Test
  fun setToggleMillisecondsDialogTest(){
    var millisEnabled = "enabled"
    var decimalPrecisionList = listOf("enabled", "disabled")
    val setDecimalPrecision: (String) -> Unit = { state ->
      millisEnabled = state
    }

    composeTestRule.setContent {
      MillisecondsEnabledListItem(
        millisEnabled = millisEnabled,
        millisOptions = decimalPrecisionList,
        setMillisEnabled = setDecimalPrecision
      )
    }

    composeTestRule.onNodeWithText("Toggle Milliseconds Bar").assertIsDisplayed()
    composeTestRule.onNodeWithText("Toggle Milliseconds Bar").performClick()
    composeTestRule.onNodeWithText("enabled").assertIsDisplayed()
  }

  @Test
  fun setToggleMillisecondsTest(){
    var millisEnabled = "enabled"
    var decimalPrecisionList = listOf("enabled", "disabled")
    val setDecimalPrecision: (String) -> Unit = { state ->
      millisEnabled = state
    }

    composeTestRule.setContent {
      MillisecondsEnabledListItem(
        millisEnabled = millisEnabled,
        millisOptions = decimalPrecisionList,
        setMillisEnabled = setDecimalPrecision
      )
    }

    composeTestRule.onNodeWithText("Toggle Milliseconds Bar").assertIsDisplayed()
    composeTestRule.onNodeWithText("Toggle Milliseconds Bar").performClick()
    composeTestRule.onNodeWithText("enabled").assertIsDisplayed()
    composeTestRule.onNodeWithText("enabled").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("disabled").assertIsDisplayed()
    composeTestRule.onNodeWithText("disabled").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()
    assert(millisEnabled == "disabled")
  }

  @Test
  fun setDecimalPrecisionTest(){
    var decimalPrecision = 2
    var decimalPrecisionList = listOf(0, 1, 2, 3)
    val setDecimalPrecision: (Int) -> Unit = { precision ->
      decimalPrecision = precision
    }

    composeTestRule.setContent {
      PrecisionListItem(
        decimalPrecision = decimalPrecision,
        decimalPrecisionList = decimalPrecisionList,
        setDecimalPrecision = setDecimalPrecision
      )
    }
    val item = 1
    composeTestRule.onNodeWithText("Decimal Precision").performClick()
    composeTestRule.onNodeWithText("2").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText(item.toString()).assertIsDisplayed()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText(item.toString()).performClick()
    composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()
    assert(decimalPrecision == 1)
  }

}

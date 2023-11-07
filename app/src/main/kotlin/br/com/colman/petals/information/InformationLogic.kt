package br.com.colman.petals.information

import android.content.Context
import android.content.res.XmlResourceParser
import br.com.colman.petals.R
import org.xmlpull.v1.XmlPullParser
import java.util.Locale

// Retrieving the content of the general_knowledge xml
data class InfoItem(val title: String, val content: String)

fun getXmlGKResId(context: Context): Int {
  val locale = context.resources.configuration.locale ?: Locale.getDefault()
  val localizeXmlResId = when (locale.language) {
    Locale.ENGLISH.language -> R.xml.general_knowledge_en
    else -> R.xml.general_knowledge_en
  }
  return localizeXmlResId
}

fun parseXmlGenKnowledge(context: Context): List<InfoItem> {
  val infoItems = mutableListOf<InfoItem>()
  val locale = context.resources.configuration.locale ?: Locale.getDefault()
  val localizeXmlResId = getXmlGKResId(context)
  val parser: XmlResourceParser = context.resources.getXml(localizeXmlResId)
  var eventType = parser.eventType
  var currentTag: String? = null
  var currentTitle: String? = null
  var currentContent: String? = null

  while (eventType != XmlPullParser.END_DOCUMENT) {
    when (eventType) {
      XmlPullParser.START_TAG -> {
        currentTag = parser.name
      }

      XmlPullParser.TEXT -> {
        when (currentTag) {
          "title" -> currentTitle = parser.text
          "content" -> currentContent = parser.text
        }
      }

      XmlPullParser.END_TAG -> {
        if (parser.name == "item" && currentTitle != null && currentContent != null) {
          infoItems.add(InfoItem(currentTitle, currentContent))
        }
        if (parser.name == "item") {
          currentTitle = null
          currentContent = null
        }
        currentTag = null
      }
    }
    eventType = parser.next()
  }
  return infoItems
}

// Retrieving all the countries that are in the legislation_and_rights xml
data class CountryItem(val name: String)

fun getCountriesList(context: Context): List<CountryItem> {
  val countries = mutableListOf<CountryItem>()
  val locale = context.resources.configuration.locale ?: Locale.getDefault()
  val localizeXmlResId = when (locale.language) {
    Locale.ENGLISH.language -> R.xml.legislation_and_rights_en
    else -> R.xml.legislation_and_rights_en
  }

  val parser: XmlResourceParser = context.resources.getXml(localizeXmlResId)

  var eventType = parser.eventType
  var currentTag: String? = null
  var countryName: String? = null

  while (eventType != XmlPullParser.END_DOCUMENT) {
    when (eventType) {
      XmlPullParser.START_TAG -> {
        currentTag = parser.name
      }

      XmlPullParser.TEXT -> {
        if (currentTag == "country") {
          countryName = parser.text.trim()
        }
      }

      XmlPullParser.END_TAG -> {
        if (parser.name == "country" && countryName != null) {
          countries.add(CountryItem(name = countryName))
          countryName = null
        }
        currentTag = null
      }
    }
    eventType = parser.next()
  }
  return countries
}

// Searching in the legislation_and_rights xml
// Returning the information for the specific country

data class CountryInformation(
  val countryName: String,
  val legalStatus: String,
  val possession: String,
  val consumption: String,
  val medicalUse: String,
  val cultivation: String,
  val purchaseAndSale: String,
  val enforcement: String,
  val lastUpdate: String
)

fun getXmlCIResId(context: Context): Int {
  val locale = context.resources.configuration.locale ?: Locale.getDefault()
  val localizeXmlResId = when (locale.language) {
    Locale.ENGLISH.language -> R.xml.legislation_and_rights_en
    else -> R.xml.legislation_and_rights_en
  }
  return localizeXmlResId
}

fun getCountryInformation(context: Context, countryNameToFind: String): CountryInformation? {
  val localizeXmlResId = getXmlCIResId(context)
  val parser: XmlResourceParser = context.resources.getXml(localizeXmlResId)
  var eventType = parser.eventType
  var foundCountry = false
  var countryName = ""
  var legalStatus = ""
  var possession = ""
  var consumption = ""
  var medicalUse = ""
  var cultivation = ""
  var purchaseAndSale = ""
  var enforcement = ""
  var lastUpdate = ""

  while (eventType != XmlPullParser.END_DOCUMENT && !foundCountry) {
    when (eventType) {
      XmlPullParser.START_TAG -> {
        when (parser.name) {
          "country" -> {
            countryName = parser.nextText()
            if (countryName.equals(countryNameToFind, ignoreCase = true)) {
              foundCountry = true
            }
          }
        }
      }
      XmlPullParser.END_TAG -> {}
      else -> {}
    }
    if (!foundCountry) {
      eventType = parser.next()
    }
  }
  // If the country has been found, continue parsing the rest of the information
  if (foundCountry) {
    eventType = parser.next()
    while (eventType != XmlPullParser.END_DOCUMENT && parser.name != "item") {
      when (eventType) {
        XmlPullParser.START_TAG -> {
          val tagName = parser.name
          when (tagName) {
            "legalstatus" -> legalStatus = parser.nextText()
            "possession" -> possession = parser.nextText()
            "consumption" -> consumption = parser.nextText()
            "medicaluse" -> medicalUse = parser.nextText()
            "cultivation" -> cultivation = parser.nextText()
            "purchaseandsale" -> purchaseAndSale = parser.nextText()
            "enforcement" -> enforcement = parser.nextText()
            "lastupdate" -> lastUpdate = parser.nextText()
          }
        }
      }
      eventType = parser.next()
    }
  }
  return CountryInformation(
    countryName,
    legalStatus,
    possession,
    consumption,
    medicalUse,
    cultivation,
    purchaseAndSale,
    enforcement,
    lastUpdate
  )
}

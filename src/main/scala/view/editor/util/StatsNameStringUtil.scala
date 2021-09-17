package view.editor.util

import model.characters.properties.stats.StatName._

object StatsNameStringUtil {

  val CharismaString: String = Charisma.toString
  val ConstitutionString: String = Constitution.toString
  val DexterityString: String = Dexterity.toString
  val IntelligenceString: String = Intelligence.toString
  val StrengthString: String = Strength.toString
  val WisdomString: String = Wisdom.toString

  val StatStrings: List[String] = List(
    CharismaString,
    ConstitutionString,
    DexterityString,
    IntelligenceString,
    StrengthString,
    WisdomString
  )
}

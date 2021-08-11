package model.characters.properties.stats

object StatName {

  /**
   * Trait used by [[Stat]] and [[StatModifier]] to
   * specify the name of the property they referred to.
   */
  trait StatName

  case object Strength extends StatName

  case object Speed extends StatName

  case object Defence extends StatName

  case object Dexterity extends StatName

  case object Constitution extends StatName

  case object Intelligence extends StatName

  case object Wisdom extends StatName

  case object Charisma extends StatName

}

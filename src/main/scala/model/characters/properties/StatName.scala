package model.characters.properties

object StatName {

  /**
   * Trait used by [[model.characters.properties.Stat]] and [[model.characters.properties.StatModifier]] to
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

package model.characters.properties.stats

object StatName {

  /**
   * Trait used by [[Stats.Stat]] and [[Stats.StatModifier]] to
   * specify the name of the property they referred to.
   */
  sealed trait StatName extends Serializable

  case object Strength extends StatName

  case object Dexterity extends StatName

  case object Constitution extends StatName

  case object Intelligence extends StatName

  case object Wisdom extends StatName

  case object Charisma extends StatName

  /**
   * @return a set containing all the [[model.characters.properties.stats.StatName.StatName]] that a
   *         [[model.characters.Character]] should have.
   */
  def setOfValidStats(): Set[StatName] = Set(Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma)
}

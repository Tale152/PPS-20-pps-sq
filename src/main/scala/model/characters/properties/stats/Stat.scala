package model.characters.properties

import model.characters.properties.StatName.StatName

/**
 * Trait that represents a stat, which is a value assigned to a specific StatName.
 * It is used to define a certain statistic of a character.
 */
trait Stat extends StatDescriptor {
  def value(): Int
}

object Stat {

  /**
   * Implementation of Stat.
   * @param value is the value of the Stat created.
   * @param statName is the actual Stat Name the value is referred to.
   * @return the Stat.
   */
  def apply(value: Int, statName: StatName): Stat = new StatImpl(value, statName)

  private class StatImpl(override val value: Int, override val statName: StatName) extends Stat {
    require(statName != null)
  }
}

package model.characters.properties.stats

import StatName.StatName

/**
 * Trait that represents a stat, which is a value assigned to a specific StatName.
 * It is used to define a certain statistic of a character.
 */
sealed trait Stat extends StatDescriptor {
  val value: Int
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

    def canEqual(other: Any): Boolean = other.isInstanceOf[StatImpl]

    override def equals(other: Any): Boolean = other match {
      case that: StatImpl =>
        (that canEqual this) &&
          value == that.value &&
          statName == that.statName
      case _ => false
    }

    override def hashCode(): Int = {
      val state = Seq(value, statName)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
  }
}

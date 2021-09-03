package model.characters.properties.stats

import StatName.StatName

/**
 * Trait that represents a Stat Modifier, which is used to apply a change to a specific stat.
 */
sealed trait StatModifier extends StatDescriptor {
  def modifyStrategy: Int => Int
}

object StatModifier {

  /**
   * Implementation of StatModifier.
   * @param statName is the name of the stat the StatModifier is going to modify.
   * @param modifyStrategy is the function apply to the value of the stat. Returns the modified value of the stat.
   * @return the StatModifier.
   */
  def apply(statName: StatName, modifyStrategy : Int => Int): StatModifier =
    new StatModifierImpl(statName, modifyStrategy)

  private class StatModifierImpl(override val statName: StatName,
                                 override val modifyStrategy: Int => Int) extends StatModifier {
    require(statName != null && modifyStrategy != null)

    def canEqual(other: Any): Boolean = other.isInstanceOf[StatModifierImpl]

    override def equals(other: Any): Boolean = other match {
      case that: StatModifierImpl =>
        (that canEqual this) &&
          statName == that.statName &&
          modifyStrategy == that.modifyStrategy
      case _ => false
    }

    override def hashCode(): Int = {
      val state = Seq(statName, modifyStrategy)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
  }
}

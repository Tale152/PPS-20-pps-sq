package model.characters.properties

import model.characters.properties.StatName.StatName

/**
 * Trait that represents a Stat Modifier, which is used to apply a change to a specific stat.
 */
trait StatModifier {
  def statName(): StatName
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
  }
}

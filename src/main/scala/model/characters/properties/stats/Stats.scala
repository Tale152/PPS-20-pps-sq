package model.characters.properties.stats

import model.characters.properties.stats.StatName.StatName

object Stats {

  /**
   * Trait that represents a generic StatDescriptor, where you can specify with which stat you want to work with.
   */
  sealed trait StatDescriptor extends Serializable {
    val statName: StatName
  }

  /**
   * Implementation of StatModifier.
   * @param statName is the name of the stat the StatModifier is going to modify.
   * @param modifyStrategy is the function apply to the value of the stat. Returns the modified value of the stat.
   * @return the StatModifier.
   */
  case class StatModifier(override val statName: StatName, modifyStrategy: Int => Int)
    extends StatDescriptor {
    require(statName != null && modifyStrategy != null)
  }

  /**
   * Implementation of Stat.
   *
   * @param value    is the value of the Stat created.
   * @param statName is the actual Stat Name the value is referred to.
   * @return the Stat.
   */
  case class Stat(value: Int, override val statName: StatName) extends StatDescriptor {
    require(statName != null)
  }
}

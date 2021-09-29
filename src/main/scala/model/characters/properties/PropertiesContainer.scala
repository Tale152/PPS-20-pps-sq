package model.characters.properties

import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{Stat, StatModifier}

/**
 * A propertiesContainer contains all the properties of a specific character.
 */
sealed trait PropertiesContainer extends Serializable {

  def health: Health

  def stats: Set[Stat]

  /**
   * Returns a stat object by it's statName.
   *
   * @param statName the name of the stat.
   * @return the relative stat.
   */
  def stat(statName: StatName): Stat

  /**
   * Returns every statModifier that alters a specific stat.
   *
   * @param statName the statName of the stat.
   * @return a set of every modifier of statName.
   */
  def statModifiers(statName: StatName): List[StatModifier]

  def statModifiers: List[StatModifier]

  def statModifiers_=(statModifierSet: List[StatModifier]): Unit

  /**
   * Returns a stat with actually the modified stat.
   * @param statName the stat that is going to be returned with the actual current value.
   * @return a stat with the current stat value.
   */
  def modifiedStat(statName: StatName): Stat
}

object PropertiesContainer {

  /**
   * Implementation of PropertiesContainer.
   *
   * @param maxPS the number of PS for character's full life.
   * @param stats the statistics of a specific Character.
   */
  private class PropertiesContainerImpl(private val maxPS: Int,
                                        val stats: Set[Stat],
                                        var statModifiers: List[StatModifier])
    extends PropertiesContainer {

    val health: Health = Health(maxPS)

    override def stat(statName: StatName): Stat = stats.find(s => s.statName == statName).get

    override def statModifiers(st: StatName): List[StatModifier] = statModifiers.filter(s => s.statName == st)

    override def modifiedStat(statName: StatName): Stat =
      statModifiers(statName)
        .foldLeft(stat(statName))((stat, modifier) => Stat(modifier.modifyStrategy(stat.value),stat.statName))

  }

  def apply(maxPS: Int, stats: Set[Stat], statModifiers: List[StatModifier] = List()): PropertiesContainer =
    new PropertiesContainerImpl(maxPS, stats, statModifiers)

}

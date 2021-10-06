package model.characters.properties

import model.characters.properties.stats.StatName.StatName
import model.characters.properties.stats.{Stat, StatModifier}

/**
 * A propertiesContainer contains all the properties of a specific character.
 */
sealed trait PropertiesContainer extends Serializable {

  /**
   * @return the instance of [[Health]] associated with this PropertiesContainer
   */
  def health: Health

  /**
   * @return all the [[Stat]] associated with this PropertiesContainer
   *         (having the same value as the value when the player created his character)
   */
  def stats: Set[Stat]

  /**
   * Returns a stat object by it's statName.
   * The returned Stat has the same value as the value when the player created his character
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

  /**
   * @return all the StatModifier contained in this PropertiesContainer
   */
  def statModifiers: List[StatModifier]

  /**
   * Changes the StatModifier that this PropertiesContainer contains
   * @param statModifierSet the new set of StatModifiers
   */
  def statModifiers_=(statModifierSet: List[StatModifier]): Unit

  /**
   * Returns a stat with actually the modified stat (if the resulting value is less than one, it will be raised to one).
   * @param statName the stat that is going to be returned with the actual current value.
   * @return a stat with the current stat value.
   */
  def modifiedStat(statName: StatName): Stat
}

object PropertiesContainer {

  private class PropertiesContainerImpl(private val maxPS: Int,
                                        val stats: Set[Stat],
                                        var statModifiers: List[StatModifier])
    extends PropertiesContainer {

    val health: Health = Health(maxPS)

    override def stat(statName: StatName): Stat = stats.find(s => s.statName == statName).get

    override def statModifiers(st: StatName): List[StatModifier] = statModifiers.filter(s => s.statName == st)

    override def modifiedStat(statName: StatName): Stat = {
      val res = statModifiers(statName)
        .foldLeft(stat(statName))((stat, modifier) => Stat(modifier.onApply(stat.value),stat.statName))
      if(res.value > 0){
        res
      } else {
        Stat(1, res.statName)
      }
    }

  }

  def apply(maxPS: Int, stats: Set[Stat], statModifiers: List[StatModifier] = List()): PropertiesContainer =
    new PropertiesContainerImpl(maxPS, stats, statModifiers)

}

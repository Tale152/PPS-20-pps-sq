package model.nodes

import model.StoryModel
import model.characters.properties.stats.StatName.StatName
import model.items.Item

/**
 * A Prerequisite is a condition that needs to be satisfied in order to choose a [[Pathway]]
 */
sealed trait Prerequisite extends (StoryModel => Boolean) with Serializable

/**
 * A [[Prerequisite]] that checks the presence of an [[Item]] in the [[Character]]'s inventory.
 * @param item the Item to check
 */
case class ItemPrerequisite(item: Item) extends Prerequisite {
  override def apply(storyModel: StoryModel): Boolean = storyModel.player.inventory.contains(item)
}

/**
 * A [[Prerequisite]] that checks if a [[Character]]'s Stat currently has a minimum value.
 * @param statName the Stat to check
 * @param value the minimum value to reach
 */
case class StatPrerequisite(statName: StatName, value: Int) extends Prerequisite {
  override def apply(storyModel: StoryModel): Boolean =
    storyModel.player.properties.modifiedStat(statName).value >= value
}
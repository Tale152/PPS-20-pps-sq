package model.nodes.util

import model.StoryModel
import model.characters.properties.stats.StatName.StatName
import model.items.Item

sealed trait Prerequisite extends (StoryModel => Boolean) with Serializable

case class ItemPrerequisite(item: Item) extends Prerequisite {
  override def apply(storyModel: StoryModel): Boolean = storyModel.player.inventory.contains(item)
}

case class StatPrerequisite(statName: StatName, value: Int) extends Prerequisite {
  override def apply(storyModel: StoryModel): Boolean =
    storyModel.player.properties.modifiedStat(statName).value >= value
}
package model.nodes.util

import model.StoryModel
import model.characters.properties.stats.StatName.StatName
import model.items.Item

trait Prerequisite extends Serializable {
  def isSatisfied(storyModel: StoryModel): Boolean
}

case class ItemPrerequisite(item: Item) extends Prerequisite {
  override def isSatisfied(storyModel: StoryModel): Boolean = storyModel.player.inventory.contains(item)
}

case class StatPrerequisite(statName: StatName, value: Int) extends Prerequisite {
  override def isSatisfied(storyModel: StoryModel): Boolean =
    storyModel.player.properties.stat(statName).value >= value
}
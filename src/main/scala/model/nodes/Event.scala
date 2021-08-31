package model.nodes

import model.StoryModel
import model.characters.properties.stats.StatModifier
import model.items.Item

/**
 * Contains the strategy to pass to StoryNode's events.
 * @see [[model.nodes.StoryNode]]
 */
sealed trait Event extends Serializable {
  /**
   * Strategy of what happens in a StoryNode's event.
   * @param storyModel the StoryModel to manipulate on execution.
   * @see [[model.StoryModel]]
   * @see [[model.nodes.StoryNode]]
   */
  def handle(storyModel: StoryModel): Unit
}

/**
 * [[model.nodes.Event]] that contains a [[model.characters.properties.stats.StatModifier]].
 * @param statModifier the [[model.characters.properties.stats.StatModifier]] contained in the event.
 */
case class StatEvent (statModifier: StatModifier) extends Event {
  override def handle(storyModel: StoryModel): Unit =
    storyModel.player.properties.statModifiers += statModifier
}

/**
 * [[model.nodes.Event]] that contains a [[model.items.Item]].
 * @param item the [[model.items.Item]] contained in the event.
 */
case class ItemEvent(item: Item) extends Event {
  override def handle(storyModel: StoryModel): Unit =
    storyModel.player.inventory = storyModel.player.inventory :+ item
}

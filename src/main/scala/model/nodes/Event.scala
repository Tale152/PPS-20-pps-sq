package model.nodes

import model.StoryModel
import model.characters.properties.stats.StatModifier
import model.items.Item

/**
 * Trait that represents an Event inside a StoryNode.
 * @see [[model.nodes.StoryNode]]
 */
sealed trait Event extends (StoryModel => Unit) with Serializable {

  /**
   * @return a Description of what happened in the event.
   */
  val description: String
}

/**
 * [[model.nodes.Event]] that contains a [[model.characters.properties.stats.Stats.StatModifier]].
 *
 * @param statModifier the [[model.characters.properties.stats.Stats.StatModifier]] contained in the event.
 */
case class StatEvent(override val description: String, statModifier: StatModifier) extends Event {
  override def apply(storyModel: StoryModel): Unit =
    storyModel.player.properties.statModifiers = storyModel.player.properties.statModifiers :+ statModifier
}

/**
 * [[model.nodes.Event]] that contains a [[model.items.Item]].
 *
 * @param item the [[model.items.Item]] contained in the event.
 */
case class ItemEvent(override val description: String, item: Item) extends Event {
  override def apply(storyModel: StoryModel): Unit =
    storyModel.player.inventory = storyModel.player.inventory :+ item
}

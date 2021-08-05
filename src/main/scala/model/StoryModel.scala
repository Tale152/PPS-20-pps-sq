package model

import model.characters.Player
import model.nodes.StoryNode

/**
 * Trait that represents the StoryModel, the class that gives the controller all data about the model state.
 */
trait StoryModel {
  def player: Player
  def currentStoryNode: StoryNode
  def currentStoryNode_=(newNode: StoryNode): Unit
}

/**
 * The case class that implements the actual Story Model.
 * @param player is the main character of the game, it must be immutable.
 * @param currentStoryNode is the current node of the story. As the story goes on, the current node is always updated.
 */
case class StoryModelImpl (override val player: Player, override var currentStoryNode: StoryNode) extends StoryModel

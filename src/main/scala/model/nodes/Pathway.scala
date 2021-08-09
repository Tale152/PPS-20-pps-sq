package model.nodes

import model.StoryModel

/**
 * Trait that represents a pathway, which is the data structure of a possible choice that the player can make to
 * navigate the story nodes.
 * The possibility to choose a pathway is dictated by the result of the prerequisite
 */
trait Pathway {
  def description: String
  def destinationNode: StoryNode
  def prerequisite: StoryModel => Boolean
}

object Pathway {

  /**
   * Implementation of the pathway.
   * @param description is the string showed to the player, which describe the action that the player can make.
   * @param destinationNode is the story node that will be the current one if the player chooses this pathway.
   * @param prerequisite is the condition to satisfy to make this pathway available
   * @return the pathway.
   */
  def apply(description: String, destinationNode: StoryNode, prerequisite: StoryModel => Boolean): Pathway =
    new PathwayImpl(description, destinationNode, prerequisite)

  private class PathwayImpl(override val description: String,
                            override val destinationNode: StoryNode,
                            override val prerequisite: StoryModel => Boolean) extends Pathway{
    require(description != null && description.trim.nonEmpty && destinationNode != null && prerequisite != null)
  }
}

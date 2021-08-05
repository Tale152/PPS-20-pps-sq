package model.nodes

/**
 * Trait that represents a pathway, which is the data structure of a possible choice that the player can make to
 * navigate the story nodes.
 */
trait Pathway {
  def description: String
  def destinationNode: StoryNode
}

object Pathway {

  /**
   * Implementation of the pathway.
   * @param description is the string showed to the player, which describe the action that the player can make.
   * @param destinationNode is the story node that will be the current one if the player chooses this pathway.
   * @return the pathway.
   */
  def apply(description: String, destinationNode: StoryNode): Pathway = new PathwayImpl(description, destinationNode)

  private class PathwayImpl(override val description: String, override val destinationNode: StoryNode) extends Pathway{
    require(description != null && description.trim.nonEmpty && destinationNode != null)
  }
}

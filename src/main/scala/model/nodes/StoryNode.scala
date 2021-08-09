package model.nodes

import scala.collection.immutable.Set

/**
 * Trait that represents a story node, which is used to have a reference of all the current possible pathways and
 * of what is happening in the story.
 */
trait StoryNode {
  def id: Int
  def narrative: String
  def pathways: Set[Pathway]
}

object StoryNode {

  /**
   * Implementation of StoryNode.
   * @param id is the unique id of the node.
   * @param narrative is the text that the player will read, which is the actual story.
   * @param pathways are the possible pathways that the player can see and choose, to progress in the story.
   * @return the story node.
   */
  def apply(id: Int, narrative: String, pathways: Set[Pathway]): StoryNode = new StoryNodeImpl(id, narrative, pathways)

  private class StoryNodeImpl(override val id: Int,
                              override val narrative: String,
                              override val pathways: Set[Pathway]) extends StoryNode {
    require(
      !id.isNaN && narrative != null &&
        narrative.trim.nonEmpty &&
        pathways.forall(p => !pathways.exists(o => !o.eq(p) && o.destinationNode.eq(p.destinationNode)))
    )
  }
}
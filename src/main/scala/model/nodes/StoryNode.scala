package model.nodes

import scala.collection.immutable.Set

trait StoryNode {
  def id: Int
  def narrative: String
  def pathways: Set[Pathway]
}

object StoryNode {
  def apply(id: Int, narrative: String, pathways: Set[Pathway]): StoryNode = new StoryNodeImpl(id, narrative, pathways)

  private class StoryNodeImpl(override val id: Int,
                              override val narrative: String,
                              override val pathways: Set[Pathway]) extends StoryNode {
    assert(!id.isNaN && narrative.trim.nonEmpty)
  }
}
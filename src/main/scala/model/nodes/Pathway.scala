package model.nodes

trait Pathway {
  def description: String
  def destinationNode: StoryNode
}

object Pathway {
  def apply(description: String, destinationNode: StoryNode): Pathway = new PathwayImpl(description, destinationNode)

  private class PathwayImpl(override val description: String, override val destinationNode: StoryNode) extends Pathway{
    assert(description.trim.nonEmpty && destinationNode != null)
  }
}

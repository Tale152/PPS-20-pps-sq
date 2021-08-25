package model.progress

/**
 * Used to serialize the history.
 * @see [[model.StoryModel]]
 * @see [[model.nodes.StoryNode]]
 */
sealed trait SerializableHistory extends Serializable {
  /**
   * @return a list containing all visited nodes id, in order of traversal
   * @see [[model.StoryModel]]
   * @see [[model.nodes.StoryNode]]
   */
  def visitedNodesId: List[Int]
}

object SerializableHistory {
  private class SerializableHistoryImpl(override val visitedNodesId: List[Int]) extends SerializableHistory{
    require(
      visitedNodesId != null &&
        visitedNodesId.nonEmpty &&
        visitedNodesId.size == visitedNodesId.toSet.size
    )
  }

  def apply(visitedNodesId: List[Int]): SerializableHistory = new SerializableHistoryImpl(visitedNodesId)
}

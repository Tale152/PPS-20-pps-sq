package model.progress

sealed trait SerializableHistory extends Serializable {
  def visitedNodesId: List[Int]
}

object SerializableHistory {
  private class SerializableHistoryImpl(override val visitedNodesId: List[Int]) extends SerializableHistory

  def apply(visitedNodesId: List[Int]): SerializableHistory = new SerializableHistoryImpl(visitedNodesId)
}

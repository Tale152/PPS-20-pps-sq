package controller.util.serialization

import controller.util.serialization.FileSerializer.{deserializeObject, serializeObject}
import model.StoryModel
import model.nodes.StoryNode
import model.progress.{Progress, SerializableHistory}

/**
 * Used to serialize / deserialize a Progress.
 *
 * @see [[model.progress.Progress]]
 */
object ProgressSerializer {

  /**
   * Serializes a Progress.
   *
   * @see [[model.progress.Progress]]
   * @param progress the Progress to serialize.
   * @param fileName serialized Progress file destination.
   */
  def serializeProgress(progress: Progress, fileName: String): Unit = {
    serializeObject(progress, fileName)
  }

  /**
   * Deserialize a Progress, giving back a StoryModel to resume the game.
   *
   * @param storyNode starting StoryNode of the deserialized story.
   * @param fileUri   serialized Progress file source.
   * @return a StoryModel identical to the one when the user has saved his progress.
   * @see [[model.StoryModel]]
   * @see [[model.nodes.StoryNode]]
   */
  def deserializeProgress(storyNode: StoryNode, fileUri: String): StoryModel = {
    val progress = deserializeObject(fileUri).asInstanceOf[Progress]
    StoryModel(extractStoryName(fileUri), progress.player, rebuildHistory(storyNode, progress.serializableHistory))
  }

  /**
   * Create a ordered list of [[model.nodes.StoryNode]] from History to build the path already travelled.
   *
   * @param startingNode      the starting [[model.nodes.StoryNode]] of the story.
   * @param serializedHistory the [[model.progress.SerializableHistory]].
   * @return the ordered list of [[model.nodes.StoryNode]] travelled in the History.
   */
  private def rebuildHistory(startingNode: StoryNode, serializedHistory: SerializableHistory): List[StoryNode] = {
    if (startingNode.id == serializedHistory.visitedNodesId.head) {
      var alreadyVisitedIDs: List[Int] = serializedHistory.visitedNodesId
      var result: List[StoryNode] = List()
      var currentNode: StoryNode = startingNode
      result = result :+ currentNode
      alreadyVisitedIDs = alreadyVisitedIDs.drop(1)
      while (alreadyVisitedIDs.nonEmpty) {
        if (currentNode.pathways.exists(p => p.destinationNode.id == alreadyVisitedIDs.head)) {
          currentNode = currentNode.pathways.filter(
            p => p.destinationNode.id == alreadyVisitedIDs.head
          ).head.destinationNode
          result = result :+ currentNode
          alreadyVisitedIDs = alreadyVisitedIDs.drop(1)
        } else {
          throw new IllegalArgumentException()
        }
      }
      // the events on the final node (the node where to start in the progress) have already been processed
      result.last.removeAllEvents()
      result
    } else {
      throw new IllegalArgumentException("The starting node and the deserialized node are not the same.")
    }
  }

  def extractStoryName(storyUri: String): String =
    storyUri.split("/")(storyUri.split("/").size - 1).replace(".sqstr", "")
}

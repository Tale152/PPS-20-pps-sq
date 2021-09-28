package controller.util.serialization

import controller.editor.StoryNodeConverter.{ImmutableStoryNodeConverter, MutableStoryNodeConverter}
import controller.util.ResourceNames.FileExtensions.{StoryFileExtension, StoryProgressFileExtension}
import controller.util.serialization.FileSerializer.{deserializeObject, serializeObject}
import model.{Progress, StoryModel}
import model.nodes.StoryNode
import model.nodes.StoryNode.MutableStoryNode

/**
 * Used to serialize / deserialize a Progress.
 *
 * @see [[Progress]]
 */
object ProgressSerializer {

  /**
   * Serializes a Progress.
   *
   * @see [[Progress]]
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
   * @param serializedHistory the list of visited [[model.nodes.StoryNode]] Ids.
   * @return the ordered list of [[model.nodes.StoryNode]] travelled in the History.
   */
  private def rebuildHistory(startingNode: StoryNode, serializedHistory: List[Int]): List[StoryNode] = {
    val mutableStructure = startingNode.toMutable
    val findPredicate: MutableStoryNode => Boolean = n => n.id == serializedHistory.last
    if (!mutableStructure._2.exists(findPredicate)) {
      throw new IllegalArgumentException("An id in the serialized history does not match with any valid StoryNode")
    }
    // the events on the final node (the node where to start in the progress) have already been processed
    mutableStructure._2.filter(findPredicate).head.events = List()
    val newStartingNode = mutableStructure._1.toImmutable._1
    if (newStartingNode.id != serializedHistory.head) {
      throw new IllegalArgumentException("The starting node and the deserialized node are not the same.")
    }
    var previousNode: StoryNode = newStartingNode
    val result: List[StoryNode] = List(newStartingNode) ++ serializedHistory.drop(1).map(id =>
      if (previousNode.pathways.exists(p => p.destinationNode.id == id)) {
        previousNode = previousNode.pathways.filter(p => p.destinationNode.id == id).head.destinationNode
        previousNode
      } else {
        throw new IllegalArgumentException("An id in the serialized history does not match with any valid StoryNode")
      }
    )
    result
  }

  /**
   * @param storyUri uri of the story
   * @return the name of the story, extracted from the uri
   */
  def extractStoryName(storyUri: String): String =
    storyUri.split("/")(storyUri.split("/").length - 1)
      .replace("." + StoryProgressFileExtension, "")
      .replace("." + StoryFileExtension, "")
}

package controller.util.serialization

import controller.util.serialization.FileSerializer.{deserializeObject, serializeObject}
import model.nodes.StoryNode

/**
 * Component that can serialize (to a file) and deserialize (from a file) a [[model.nodes.StoryNode]].
 */
object StoryNodeSerializer {

  /**
   * @param storyNode the [[model.nodes.StoryNode]] to serialize.
   * @param fileName  the file path where storyNode will be serialized.
   */
  def serializeStory(storyNode: StoryNode, fileName: String): Unit = {
    serializeObject(storyNode, fileName)
  }

  /**
   * @param fileUri the path of the file to deserialize.
   * @return a deserialized [[model.nodes.StoryNode]].
   */
  def deserializeStory(fileUri: String): StoryNode = {
    deserializeObject(fileUri).asInstanceOf[StoryNode]
  }
}

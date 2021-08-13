package model.nodes.util

import model.nodes.StoryNode

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

/**
 * Component that can serialize (to a file) and deserialize (from a file) a [[model.nodes.StoryNode]]
 */
object StoryNodeSerializer {

  /**
   * @param storyNode the [[model.nodes.StoryNode]] to serialize
   * @param fileName  the file path where storyNode will be serialized.
   */
  def serializeStory(storyNode: StoryNode, fileName: String): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(fileName))
    oos.writeObject(storyNode)
    oos.close()
  }

  /**
   * @param fileUri the path of the file to deserialize.
   * @return a deserialized [[model.nodes.StoryNode]]
   */
  def deserializeStory(fileUri: String): StoryNode = {
    val ois = new ObjectInputStream(new FileInputStream(fileUri))
    val storyNode = ois.readObject.asInstanceOf[StoryNode]
    ois.close()
    storyNode
  }

}

package model.progress

import model.nodes.StoryNode
import model.StoryModel

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

/**
 * Used to serialize / deserialize a Progress.
 * @see [[model.progress.Progress]]
 */
object ProgressSerializer {

  /**
   * Serializes a Progress.
   * @see [[model.progress.Progress]]
   * @param progress the Progress to serialize
   * @param fileName serialized Progress file destination
   */
  def serializeProgress(progress: Progress, fileName:String): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(fileName))
    oos.writeObject(progress)
    oos.close()
  }

  /**
   * Deserialize a Progress, giving back a StoryModel to resume the game
   * @param storyNode starting StoryNode of the deserialized story
   * @param fileUri serialized Progress file source
   * @return a StoryModel identical to the one when the user has saved his progress
   * @see [[model.StoryModel]]
   * @see [[model.nodes.StoryNode]]
   */
  def deserializeProgress(storyNode: StoryNode, fileUri: String): StoryModel = {
    val ois = new ObjectInputStream(new FileInputStream(fileUri))
    val progress = ois.readObject.asInstanceOf[Progress]
    ois.close()
    StoryModel(progress.player, rebuildHistory(storyNode, progress.serializableHistory))
  }

  private def rebuildHistory(startingNode: StoryNode, serializedHistory: SerializableHistory):List[StoryNode] = {
    if(startingNode.id == serializedHistory.visitedNodesId.head){
      var _ids = serializedHistory.visitedNodesId
      var _res: List[StoryNode] = List()
      var _currentNode = startingNode
      _res = _res :+ _currentNode
      _ids = _ids.drop(1)
      while(_ids.nonEmpty){
        if(_currentNode.pathways.exists(p => p.destinationNode.id == _ids.head)){
          _currentNode = _currentNode.pathways.filter(p => p.destinationNode.id == _ids.head).head.destinationNode
          _res = _res :+ _currentNode
          _ids = _ids.drop(1)
        } else {
          throw new IllegalArgumentException()
        }
      }
      _res
    } else {
      throw new IllegalArgumentException()
    }
  }
}

package model.progress

import model.characters.Player
import model.nodes.StoryNode
import model.StoryModel
import model.characters.properties.stats.{Stat, StatName}

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

object ProgressSerializer {

  def serializeProgress(progress: Progress, fileName:String): Unit = {
    val oos = new ObjectOutputStream(new FileOutputStream(fileName))
    oos.writeObject(progress)
    oos.close()
  }

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

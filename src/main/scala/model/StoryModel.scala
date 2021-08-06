package model

import model.characters.Player
import model.nodes.StoryNode

/**
 * Trait that represents the StoryModel, the class that gives the controller all data about the model state.
 */
trait StoryModel {
  def player: Player
  def currentStoryNode: StoryNode
  def currentStoryNode_=(newNode: StoryNode): Unit
}

/**
 * The case class that implements the actual Story Model.
 * @param player is the main character of the game, it must be immutable.
 * @param currentStoryNode is the current node of the story. As the story goes on, the current node is always updated.
 */
case class StoryModelImpl (override val player: Player, override var currentStoryNode: StoryNode) extends StoryModel {

  require(checkNoDuplicateIdInNodes(getAllNodesStartingFromThis(currentStoryNode)))

  private def getAllNodesStartingFromThis(node: StoryNode): Set[StoryNode] = {
    def _visitAllPathways(node: StoryNode): Set[Set[StoryNode]] = {
      for(pathway <- node.pathways)
        yield getAllNodesStartingFromThis(pathway.destinationNode)
    }
    Set(node) ++ _visitAllPathways(node).foldLeft[Set[StoryNode]](Set.empty[StoryNode])(_ ++ _)
  }

  private def checkNoDuplicateIdInNodes(nodes: Set[StoryNode]): Boolean = nodes.size == nodes.map(n => n.id).size

}

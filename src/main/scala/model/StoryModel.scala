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

object StoryModel {

  /**
   * The actual implementation of the StoryModel.
   * @param player the main player of the game.
   * @param currentStoryNode the current node of the story the player is in. It's updated as the player goes
   *                         on in the story.
   * @return the StoryModel.
   */
  def apply(player: Player, currentStoryNode : StoryNode): StoryModel = new StoryModelImpl(player, currentStoryNode)

  private class StoryModelImpl (override val player: Player,
                                override var currentStoryNode: StoryNode) extends StoryModel {

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
}

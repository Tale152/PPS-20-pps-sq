package model

import model.characters.Player
import model.nodes.StoryNode

/**
 * Trait that represents the StoryModel, the class that gives the controller all data about the model state.
 */
trait StoryModel {

  /**
   * @return the name of this story
   */
  val storyName: String

  /**
   * @return the Player that is playing this story.
   * @see [[model.characters.Player]]
   */
  val player: Player

  /**
   * @return the story node where the player currently is (last node in history)
   * @see [[model.nodes.StoryNode]]
   */
  def currentStoryNode: StoryNode

  /**
   * @return a List of StoryNodes traversed by the Player so far.
   * @see [[model.characters.Player]]
   * @see [[model.nodes.StoryNode]]
   */
  def history: List[StoryNode]

  /**
   * Appends a new traversed StoryNode, updating the history.
   *
   * @param storyNode the new traversed StoryNode to append.
   * @see [[model.nodes.StoryNode]]
   */
  def appendToHistory(storyNode: StoryNode): Unit
}

object StoryModel {

  /**
   * Returns an implementation of StoryModel.
   *
   * @param storyName         the name of the story
   * @param player            the main player of the game.
   * @param startingStoryNode the starting node of the story.
   * @return the StoryModel.
   */
  def apply(storyName: String, player: Player, startingStoryNode: StoryNode): StoryModel =
    new StoryModelImpl(storyName, player, List(startingStoryNode))

  /**
   * Returns an implementation of StoryModel.
   *
   * @param storyName      the name of the story
   * @param player         the main player of the game.
   * @param currentHistory the history of traversed StoryNodes so far.
   * @return the StoryModel.
   */
  def apply(storyName: String, player: Player, currentHistory: List[StoryNode]): StoryModel =
    new StoryModelImpl(storyName, player, currentHistory)

  private class StoryModelImpl(override val storyName: String,
                               override val player: Player,
                               currentHistory: List[StoryNode]) extends StoryModel {

    ArgsChecker.check()

    private var _history: List[StoryNode] = currentHistory

    private def isStoryNodeToAppendValid(currentNode: StoryNode, nodeToAppend: StoryNode): Boolean =
      currentNode.pathways.count(p => p.destinationNode == nodeToAppend) == 1

    override def currentStoryNode: StoryNode = _history.last

    override def history: List[StoryNode] = _history

    override def appendToHistory(storyNode: StoryNode): Unit = {
      if (!isStoryNodeToAppendValid(currentStoryNode, storyNode)) throw new IllegalArgumentException(
        "Provided StoryNode is not reachable by any Pathway starting from the current StoryNode"
      )
      _history = _history :+ storyNode
    }

    private object ArgsChecker {

      private def checkNoDuplicateIdInNodes(nodes: Set[StoryNode]): Boolean = nodes.size == nodes.map(n => n.id).size

      private def reachableNodes(node: StoryNode): Set[StoryNode] = {

        def _visitAllPathways(node: StoryNode): Set[Set[StoryNode]] = {
          for (pathway <- node.pathways) yield reachableNodes(pathway.destinationNode)
        }

        Set(node) ++ _visitAllPathways(node).foldLeft[Set[StoryNode]](Set.empty[StoryNode])(_ ++ _)
      }

      private def isHistoryValid(history: List[StoryNode]): Boolean = history.size match {
        case 1 => true
        case _ => history.sliding(2).forall(p => isStoryNodeToAppendValid(p.head, p.last))
      }

      def check(): Unit =
        require(
            storyName.trim.nonEmpty
            && currentHistory.nonEmpty
            && checkNoDuplicateIdInNodes(reachableNodes(currentHistory.head))
            && isHistoryValid(currentHistory)
        )
    }
  }
}

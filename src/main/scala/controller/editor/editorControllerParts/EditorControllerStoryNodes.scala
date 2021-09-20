package controller.editor.editorControllerParts

import controller.editor.{EditorController, StoryNodeConverter}
import model.characters.Enemy
import model.items.KeyItem
import model.nodes.{Event, ItemEvent, StoryNode}
import model.nodes.StoryNode.MutableStoryNode

trait EditorControllerStoryNodes {

  /**
   * @param id the target node's id
   * @return the MutableStoryNode associated with the id (if present)
   * @see [[model.nodes.StoryNode.MutableStoryNode]]
   */
  def getStoryNode(id: Int): Option[MutableStoryNode]

  /**
   * Creates a new node in the story.
   *
   * @param startingPathwayId  the id of the node that originates the pathway leading to the new node
   * @param pathwayDescription the description of the pathway leading to the new node
   * @param newNodeNarrative   the narrative of the new node
   */
  def addNewStoryNode(startingPathwayId: Int, pathwayDescription: String, newNodeNarrative: String): Unit

  /**
   * Changes an existing node properties.
   *
   * @param id            the target node to edit
   * @param nodeNarrative the new narrative in the target node
   */
  def editExistingStoryNode(id: Int, nodeNarrative: String): Unit

  /**
   * Deletes an existing node.
   *
   * @param id the id of the target node
   */
  def deleteExistingStoryNode(id: Int): Unit

  def isStoryNodeDeletable(id: Int): Boolean

  def getNodesIds(filter: StoryNode => Boolean): List[Int]

  def addEventToNode(nodeId: Int, event: Event): Unit

  def deleteEventFromNode(nodeId: Int, event: Event): Unit

  def addEnemyToNode(nodeId: Int, enemy: Enemy): Unit

  def deleteEnemyFromNode(nodeId: Int): Unit

  def getAllKeyItemsBeforeNode(targetNode: MutableStoryNode): List[KeyItem]

}

object EditorControllerStoryNodes {

  private case class EditorControllerStoryNodesImpl(editorController: EditorController)
    extends EditorControllerStoryNodes {

    override def getStoryNode(id: Int): Option[MutableStoryNode] = editorController.nodes._2.find(n => n.id == id)

    override def addNewStoryNode(startNodeId: Int, pathwayDescription: String, newNodeNarrative: String): Unit = {
      val newId: Int = editorController.nodes._2.maxBy(n => n.id).id + 1
      val newNode = MutableStoryNode(newId, newNodeNarrative, None, Set(), List())
      editorController.nodes = (editorController.nodes._1, editorController.nodes._2 + newNode)
      editorController.graph.addNode(newId.toString)
      editorController.pathwaysControls
        .addNewPathway(startNodeId, newId, pathwayDescription) //decorateGraphGUI called here
    }

    override def editExistingStoryNode(id: Int, nodeNarrative: String): Unit = {
      getStoryNode(id).get.narrative = nodeNarrative
      editorController.decorateGraphGUI()
    }

    override def deleteExistingStoryNode(id: Int): Unit = {
      //removing all pathways leading to the target node
      editorController.nodes._2.filter(n => n.mutablePathways.exists(p => p.destinationNode.id == id))
        .foreach(n => n.mutablePathways = n.mutablePathways.filter(p => p.destinationNode.id != id))
      //recreating structure implicitly deleting unreachable nodes
      editorController.nodes = StoryNodeConverter.fromImmutableToMutable(editorController.nodes._1)
      //removing the target node from the GUI graph
      editorController.graph.removeNode(id.toString)
      //removing all other deleted nodes from the GUI graph
      var removedNodesId: Set[Int] = Set()
      editorController.graph.nodes().mapToInt(n => n.getId.toInt)
        .filter(id => !editorController.nodes._2.exists(sn => sn.id == id))
        .forEach(id => removedNodesId = removedNodesId + id) //removing nodes here throws null pointer
      removedNodesId.foreach(id => {
        if (editorController.graph.nodes.anyMatch(n => n.getId.toInt == id)) {
          editorController.graph.removeNode(id.toString)
        }
      })
      editorController.pathwaysControls.applyKeyItemPrerequisiteIntegrity()
      editorController.decorateGraphGUI()
    }

    def isStoryNodeDeletable(id: Int): Boolean = {
      val mutableStoryNode = getStoryNode(id)
      if (mutableStoryNode.isEmpty) {
        false
      } else {
        val precedingNodes = editorController.pathwaysControls.getAllOriginNodes(mutableStoryNode.get.id)
        if (precedingNodes.isEmpty) {
          //is route node
          false
        } else {
          /*only if all preceding nodes have at least one unconditional pathway
          or no pathways remaining after target node delete*/
          precedingNodes.forall(n => {
            val nodeRemainingPathways = n.mutablePathways
              .filter(p => p.destinationNode != mutableStoryNode.get)
            nodeRemainingPathways.exists(p => p.prerequisite.isEmpty) || nodeRemainingPathways.isEmpty
          })
        }
      }
    }

    override def getNodesIds(filter: StoryNode => Boolean): List[Int] =
      editorController.nodes._2.filter(n => filter(n)).map(n => n.id).toList.sortWith((i, j) => i < j)

    override def addEventToNode(nodeId: Int, event: Event): Unit = {
      val node = getStoryNode(nodeId)
      node.get.events = node.get.events :+ event
      editorController.decorateGraphGUI()
    }

    override def deleteEventFromNode(nodeId: Int, event: Event): Unit = {
      val node = getStoryNode(nodeId)
      node.get.events = node.get.events.filter(e => e != event)
      editorController.pathwaysControls.applyKeyItemPrerequisiteIntegrity()
      editorController.decorateGraphGUI()
    }

    override def addEnemyToNode(nodeId: Int, enemy: Enemy): Unit = {
      getStoryNode(nodeId).get.enemy = Some(enemy)
      editorController.decorateGraphGUI()
    }

    override def deleteEnemyFromNode(nodeId: Int): Unit = {
      getStoryNode(nodeId).get.enemy = None
      editorController.decorateGraphGUI()
    }

    override def getAllKeyItemsBeforeNode(targetNode: MutableStoryNode): List[KeyItem] = {

      def stepBack(node: MutableStoryNode,
                   visitedNodes: Set[MutableStoryNode]): (List[KeyItem], Set[MutableStoryNode]) = {
        var _visitedNodes: Set[MutableStoryNode] = visitedNodes + node //adding this node to the already visited
        //getting all key items in this node
        var keyItems: List[KeyItem] = node.events
          .collect { case itemEvent: ItemEvent => itemEvent.item }
          .collect { case keyItem: KeyItem => keyItem }
        //for each predecessor of this node
        editorController.pathwaysControls.getAllOriginNodes(node.id).foreach(n => {
          //only if the predecessor hasn't been visited yet
          if (!visitedNodes.contains(n)) {
            val nodeRes = stepBack(n, _visitedNodes)
            keyItems = keyItems ++ nodeRes._1 //adding the key items found exploring this predecessor
            _visitedNodes = _visitedNodes ++ nodeRes._2 //adding the visited nodes exploring the predecessor
          }
        })
        //returning the tuple
        (keyItems, visitedNodes)
      }

      stepBack(targetNode, Set())._1
    }
  }

  def apply(editorController: EditorController): EditorControllerStoryNodes =
    EditorControllerStoryNodesImpl(editorController)

}

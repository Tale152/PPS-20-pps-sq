package controller.editor.editorControllerParts

import controller.editor.EditorController
import controller.editor.graph.util.StringUtils
import model.items.Item
import model.nodes.StoryNode.MutableStoryNode
import model.nodes.util.{ItemPrerequisite, Prerequisite}
import model.nodes.{MutablePathway, StoryNode}

trait EditorControllerPathways {

  /**
   * @param startNodeId the id of the node that is at the begin of the pathway
   * @param endNodeId   the id of the node that is at the end of the pathway
   * @return the MutablePathway that connects the two nodes
   * @see [[model.nodes.StoryNode.MutableStoryNode]]
   * @see [[model.nodes.MutablePathway]]
   */
  def getPathway(startNodeId: Int, endNodeId: Int): Option[MutablePathway]

  /**
   * Creates a new pathway to connect two nodes.
   *
   * @param startNodeId        the id of the node that is at the begin of the pathway
   * @param endNodeId          the id of the node that is at the end of the pathway
   * @param pathwayDescription the description of the new pathway
   */
  def addNewPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit

  /**
   * Changes an existing pathway properties.
   *
   * @param startNodeId        the id of the node that is at the begin of the pathway
   * @param endNodeId          the id of the node that is at the end of the pathway
   * @param pathwayDescription the new description of the target pathway
   */
  def editExistingPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit

  /**
   * Deletes an existing pathway.
   *
   * @param startNodeId the id of the node that is at the begin of the pathway
   * @param endNodeId   the id of the node that is at the end of the pathway
   */
  def deleteExistingPathway(startNodeId: Int, endNodeId: Int): Unit

  /**
   * @param startNodeId the id of the node that is at the begin of a hypothetical new pathway
   * @param endNodeId   the id of the node that is at the end of a hypothetical new pathway
   * @return if a hypothetical new pathway is valid
   */
  def isNewPathwayValid(startNodeId: Int, endNodeId: Int): Boolean

  /**
   * Adds a Prerequisite to a Pathway.
   * @param originNodeId the id of the StoryNode that originates the Pathway
   * @param destinationNodeId the id of the StoryNode where the Pathway ends
   * @param prerequisite the Prerequisite to add to the Pathway
   */
  def addPrerequisiteToPathway(originNodeId: Int, destinationNodeId: Int, prerequisite: Prerequisite): Unit

  /**
   * Deletes a Prerequisite from a Pathway.
   * @param originNodeId the id of the StoryNode that originates the Pathway
   * @param destinationNodeId the id of the StoryNode where the Pathway ends
   */
  def deletePrerequisiteFromPathway(originNodeId: Int, destinationNodeId: Int): Unit

  /**
   * @return a list of StoryNodes that can be used to be the origin of a new Pathway without breaking integrity rules
   */
  def getValidNodesForPathwayOrigin: List[MutableStoryNode]

  /**
   * @param node the target StoryNode
   * @return if the target StoryNode contains at least a Pathway that, if deleted, won't break any integrity rule.
   */
  def containsDeletablePathways(node: StoryNode): Boolean

  /**
   * Example: giving pathways {(A -> B), (A -> C), (B -> D), (B -> E), (D -> F)},
   * targeting the node 'D' will return a list with StoryNodes D, B and A.
   * @param id the target StoryNode's id
   * @return the list of origin StoryNodes (including target)
   */
  def getAllOriginNodes(id: Int): List[MutableStoryNode]

  /**
   * Given an Item, returns all the Pathways containing a Prerequisite that depends on said Item.
   * @param item the item used to obtain the dependent Prerequisites
   * @return a Set of tuples with the Pathway containing the dependent Prerequisite (second)
   *         and the StoryNode that originates that Pathway (first)
   */
  def getAllDependentPrerequisites(item: Item): Set[(MutableStoryNode, MutablePathway)]

  /**
   * Iterates over the story structure deleting eventual Prerequisites
   * that depend on KeyItems that are not present anymore
   * or were the StoryNode were the KeyItem is placed cannot reach anymore the Pathway with the dependent Prerequisite.
   */
  def applyKeyItemPrerequisiteIntegrity(): Unit

}

object EditorControllerPathways {

  private case class EditorControllerPathwaysImpl(private val editorController: EditorController)
    extends EditorControllerPathways {

    override def getPathway(startNodeId: Int, endNodeId: Int): Option[MutablePathway] =
      editorController.nodesControls.getStoryNode(startNodeId) match {
        case None => None
        case _ => editorController.nodesControls.getStoryNode(startNodeId).get
          .mutablePathways.find(p => p.destinationNode.id == endNodeId)
      }

    override def addNewPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit = {
      val startNode: Option[MutableStoryNode] = editorController.nodesControls.getStoryNode(startNodeId)
      val endNode: Option[MutableStoryNode] = editorController.nodesControls.getStoryNode(endNodeId)
      startNode.get.mutablePathways =
        startNode.get.mutablePathways + MutablePathway(pathwayDescription, endNode.get, None)
      editorController.graph.addEdge(
        startNodeId + StringUtils.PathwayIdSeparator + endNodeId,
        startNodeId.toString,
        endNodeId.toString,
        true
      )
      editorController.decorateGraphGUI()
    }

    override def editExistingPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit = {
      getPathway(startNodeId, endNodeId).get.description = pathwayDescription
      editorController.decorateGraphGUI()
    }

    override def deleteExistingPathway(startNodeId: Int, endNodeId: Int): Unit = {
      val startNode = editorController.nodesControls.getStoryNode(startNodeId)
      startNode.get.mutablePathways = startNode.get.mutablePathways.filter(p => p.destinationNode.id != endNodeId)
      editorController.graph.removeEdge(startNodeId + StringUtils.PathwayIdSeparator + endNodeId)
      applyKeyItemPrerequisiteIntegrity()
      editorController.decorateGraphGUI()
    }

    override def isNewPathwayValid(startNodeId: Int, endNodeId: Int): Boolean = {

      def searchForDestination(searchedNode: MutableStoryNode, currentNode: MutableStoryNode): Boolean =
        currentNode != searchedNode && currentNode.mutablePathways.forall(
          p => searchForDestination(searchedNode, p.destinationNode)
        )

      val startNode = editorController.nodesControls.getStoryNode(startNodeId)
      val endNode = editorController.nodesControls.getStoryNode(endNodeId)
      if (startNode.isEmpty ||
        endNode.isEmpty ||
        /* cannot create two pathways with same origin and destination */
        startNode.get.mutablePathways.exists(p => p.destinationNode == endNode.get)
      ) {
        false
      } else {
        //searching if, from the end node, the start node is unreachable (preventing a loop)
        searchForDestination(startNode.get, endNode.get)
      }
    }

    override def addPrerequisiteToPathway(originNodeId: Int,
                                          destinationNodeId: Int,
                                          prerequisite: Prerequisite): Unit = {
      getPathway(originNodeId, destinationNodeId).get.prerequisite = Some(prerequisite)
      editorController.decorateGraphGUI()
    }

    override def deletePrerequisiteFromPathway(originNodeId: Int, destinationNodeId: Int): Unit = {
      getPathway(originNodeId, destinationNodeId).get.prerequisite = None
      editorController.decorateGraphGUI()
    }

    override def getValidNodesForPathwayOrigin: List[MutableStoryNode] =
      editorController.nodes._2
        .filter(i => editorController.nodes._2.exists(j => isNewPathwayValid(i.id, j.id)))
        .toList.sortWith((i, j) => i.id < j.id)

    override def containsDeletablePathways(node: StoryNode): Boolean = {
      //at least one conditional pathway (therefore unconditional pathways exists) or two conditional
      (node.pathways.count(p => p.prerequisite.nonEmpty) == 0 || node.pathways.size >= 2) &&
        /* and at least one pathway starting from the node points to a destination node that would still be reachable
        after deleting the pathway*/
        node.pathways.exists(p => getAllOriginNodes(p.destinationNode.id).size > 1)
    }

    override def getAllOriginNodes(id: Int): List[MutableStoryNode] = {
      val targetNode = editorController.nodesControls.getStoryNode(id).get
      editorController.nodes._2.filter(n => n.pathways.exists(p => p.destinationNode == targetNode)).toList
    }

    override def getAllDependentPrerequisites(item: Item): Set[(MutableStoryNode, MutablePathway)] = {
      def isSearchedItemPrerequisiteInPathway(pathway: MutablePathway): Boolean =
        pathway.prerequisite.nonEmpty && (pathway.prerequisite.get match {
          case itemPrerequisite: ItemPrerequisite => itemPrerequisite.item == item
          case _ => false
        })

      for (
        n <- editorController.nodes._2;
        p <- n.mutablePathways if isSearchedItemPrerequisiteInPathway(p)
      ) yield (n, p)
    }

    override def applyKeyItemPrerequisiteIntegrity(): Unit = {
      for (n <- editorController.nodes._2; p <- n.mutablePathways if p.prerequisite.nonEmpty) {
        p.prerequisite.get match {
          case itemPrerequisite: ItemPrerequisite
            if !editorController.nodesControls.getAllKeyItemsBeforeNode(n).contains(itemPrerequisite.item) =>
            editorController.pathwaysControls.deletePrerequisiteFromPathway(n.id, p.destinationNode.id)
          case _ => { /*does nothing on other type of prerequisite */ }
        }
      }
    }
  }

  def apply(editorController: EditorController): EditorControllerPathways =
    EditorControllerPathwaysImpl(editorController)
}

package controller.editor

import controller.editor.graph.GraphBuilder
import controller.editor.graph.util.{ElementLabel, ElementStyle, StringUtils}
import controller.util.serialization.StoryNodeSerializer
import controller.{ApplicationController, Controller}
import model.nodes.{MutablePathway, MutableStoryNode, StoryNode}
import org.graphstream.ui.view.Viewer
import view.editor.EditorView

trait EditorController extends Controller {

  def save(path: String): Unit

  def getPathway(starNodeId: Int, endNodeId: Int): MutablePathway

  def getStoryNode(id: Int): MutableStoryNode

  def changeNodesNarrativeVisibility(): Unit

  def changePathwaysDescriptionVisibility(): Unit

  def addNewStoryNode(startingPathwayId: Int, pathwayDescription: String, newNodeNarrative: String): Unit

  def addNewPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit

  def editExistingStoryNode(id: Int, nodeNarrative: String): Unit

  def editExistingPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit

  def deleteExistingStoryNode(id: Int): Unit

  def deleteExistingPathway(startNodeId: Int, endNodeId: Int): Unit
}

object EditorController {

  System.setProperty("org.graphstream.ui", "swing")

  private class EditorControllerImpl(routeNode: StoryNode) extends EditorController {

    private var printNodeNarrative: Boolean = false
    private var printEdgeLabel: Boolean = false

    private val editorView: EditorView = EditorView(this)
    private var nodes: (MutableStoryNode, Set[MutableStoryNode]) = StoryNodeConverter.fromImmutableToMutable(routeNode)
    private val graph = GraphBuilder.build(nodes._1, printNodeNarrative, printEdgeLabel)

    val graphViewer: Viewer = graph.display()

    override def execute(): Unit = editorView.render()

    override def close(): Unit = {
      graphViewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER)
      graphViewer.close()
      ApplicationController.execute()
    }

    override def deleteExistingStoryNode(id: Int): Unit = {
      nodes._2.filter(n => n.mutablePathways.exists(p => p.destinationNode.id == id)).foreach(n => {
        n.mutablePathways = n.mutablePathways.filter(p => p.destinationNode.id != id)
        if(n != nodes._1){
          setupNonRouteNode(n.id.toString)
        }
      })
      nodes = (nodes._1, nodes._2.filter(n => n.id != id))
      graph.removeNode(id.toString)
    }

    override def deleteExistingPathway(startNodeId: Int, endNodeId: Int): Unit = {
      val startNode: MutableStoryNode = nodes._2.find(n => n.id == startNodeId).get
      startNode.mutablePathways = startNode.mutablePathways.filter(p => p.destinationNode.id != endNodeId)
      if(startNode != nodes._1){
        setupNonRouteNode(startNodeId.toString)
      }
      graph.removeEdge(startNodeId + " to " + endNodeId)
    }

    override def addNewStoryNode(startNodeId: Int, pathwayDescription: String, newNodeNarrative: String): Unit = {
      val newId: String = (nodes._2.maxBy(n => n.id).id + 1).toString
      val newNode = MutableStoryNode(newId.toInt, newNodeNarrative, None, Set(), List())
      nodes = (nodes._1, nodes._2 + newNode)
      val startingNode = nodes._2.find(n => n.id == startNodeId).get
      startingNode.mutablePathways = startingNode.mutablePathways + MutablePathway(pathwayDescription, newNode, None)
      graph.addNode(newId)
      setupNonRouteNode(newId)
      if(startNodeId != nodes._1.id){
        setupNonRouteNode(startNodeId.toString)
      }
      graph.addEdge(startNodeId + " to " + newId, startNodeId.toString, newId)
      setupEdge(startNodeId.toString, newId)
    }

    override def addNewPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit = {
      val startNode: MutableStoryNode = nodes._2.find(n => n.id == startNodeId).get
      val endNode: MutableStoryNode = nodes._2.find(n => n.id == endNodeId).get
      startNode.mutablePathways = startNode.mutablePathways + MutablePathway(pathwayDescription, endNode, None)
      if(startNodeId != nodes._1.id){
        setupNonRouteNode(startNodeId.toString)
      }
      graph.addEdge(startNodeId + " to " + endNodeId, startNodeId.toString, endNodeId.toString)
      setupEdge(startNodeId.toString, endNodeId.toString)
    }

    override def editExistingStoryNode(id: Int, nodeNarrative: String): Unit = {
      nodes._2.find(n => n.id == id).get.narrative = nodeNarrative
      if(id != nodes._1.id){
        setupNonRouteNode(id.toString)
      } else {
        setupRouteNode()
      }
    }

    override def editExistingPathway(startNodeId: Int, endNodeId: Int, pathwayDescription: String): Unit ={
      nodes._2.find(n => n.id == startNodeId).get
        .mutablePathways.find(p => p.destinationNode.id == endNodeId).get.description = pathwayDescription
      setupEdge(startNodeId.toString, endNodeId.toString)
    }

    private def setupRouteNode(): Unit = {
      ElementStyle.decorateRouteNode(
        graph.getNode(nodes._1.id.toString),
        nodes._1.events.nonEmpty,
        nodes._1.enemy.nonEmpty
      )
      ElementLabel.putLabelOnElement(graph.getNode(nodes._1.id.toString), printNodeNarrative)(
        StringUtils.truncateString(StringUtils.buildLabel(nodes._1.id.toString, nodes._1.narrative)),
        nodes._1.id.toString
      )
    }

    private def setupNonRouteNode(nodeId: String): Unit = {
      val mutableStoryNode = nodes._2.find(n => n.id.toString == nodeId).get
      ElementStyle.decorateNode(
        graph.getNode(nodeId),
        mutableStoryNode.events.nonEmpty,
        mutableStoryNode.enemy.nonEmpty,
        mutableStoryNode.pathways.isEmpty
      )
      ElementLabel.putLabelOnElement(graph.getNode(nodeId), printNodeNarrative)(
        StringUtils.truncateString(StringUtils.buildLabel(mutableStoryNode.id.toString, mutableStoryNode.narrative)),
        mutableStoryNode.id.toString
      )
    }

    private def setupEdge(startNodeId: String, endNodeId: String): Unit = {
      val startNode = nodes._2.find(n => n.id.toString == startNodeId).get
      ElementStyle.decorateEdge(
        graph.getEdge(startNodeId + " to " + endNodeId),
        startNode.pathways.find(p => p.destinationNode.id.toString == endNodeId).get.prerequisite.nonEmpty
      )
      ElementLabel.putLabelOnElement(graph.getEdge(startNodeId + " to " + endNodeId), printEdgeLabel)(
        StringUtils.buildLabel(
          startNodeId + " to " + endNodeId,
          startNode.pathways.find(p => p.destinationNode.id.toString == endNodeId).get.description
        ),
        startNodeId + " to " + endNodeId
      )
    }

    override def changeNodesNarrativeVisibility(): Unit = {
      printNodeNarrative = !printNodeNarrative
      graph.nodes().forEach(n => {
        if(n.getId != nodes._1.id.toString) {
          setupNonRouteNode(n.getId)
        } else {
          setupRouteNode()
        }
      })
    }

    override def changePathwaysDescriptionVisibility(): Unit = {
      printEdgeLabel = !printEdgeLabel
      graph.edges().forEach(e => setupEdge(e.getId.split(" to ")(0), e.getId.split(" to ")(1)))
    }

    override def getStoryNode(id: Int): MutableStoryNode = nodes._2.find(n => n.id == id).get

    override def getPathway(starNodeId: Int, endNodeId: Int): MutablePathway =
      nodes._2.find(n => n.id == starNodeId).get.mutablePathways.find(p => p.destinationNode.id == endNodeId).get

    override def save(path: String): Unit =
      StoryNodeSerializer.serializeStory(StoryNodeConverter.fromMutableToImmutable(nodes._1)._1, path)
  }

  def apply(routeNode: StoryNode): EditorController = new EditorControllerImpl(routeNode)
}

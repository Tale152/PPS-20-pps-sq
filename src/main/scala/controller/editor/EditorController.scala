package controller.editor

import controller.editor.StoryNodeConverter.fromMutableToImmutable
import controller.editor.editorControllerParts.{EditorControllerPathways, EditorControllerStoryNodes}
import controller.editor.graph.GraphBuilder
import controller.editor.graph.util.{ElementLabel, ElementStyle, StringUtils}
import controller.util.serialization.StoryNodeSerializer
import controller.{ApplicationController, Controller, InfoController}
import model.nodes.StoryNode
import model.nodes.StoryNode.MutableStoryNode
import org.graphstream.graph.Graph
import org.graphstream.ui.view.Viewer
import view.editor.EditorView

trait EditorController extends Controller {

  var nodes: (MutableStoryNode, Set[MutableStoryNode])

  val graph: Graph

  val nodesControls: EditorControllerStoryNodes

  val pathwaysControls: EditorControllerPathways

  /**
   * Saves the current nodes structure serializing said structure in the provided path.
   * @param path where to serialize the nodes structure
   */
  def save(path: String): Unit

  /**
   * Switches the visibility of the nodes narrative in the graph view
   */
  def switchNodesNarrativeVisibility(): Unit

  /**
   * Switches the visibility of the pathways description in the graph view
   */
  def switchPathwaysDescriptionVisibility(): Unit

  /**
   * Go to the Info page using the editor route node.
   */
  def goToInfo(): Unit

  def decorateGraphGUI(): Unit

}

object EditorController {

  System.setProperty("org.graphstream.ui", "swing")

  private class EditorControllerImpl(routeNode: StoryNode) extends EditorController {

    private var printNodeNarrative: Boolean = false
    private var printEdgeLabel: Boolean = false
    private val editorView: EditorView = EditorView(this)
    override val nodesControls: EditorControllerStoryNodes = EditorControllerStoryNodes(this)
    override val pathwaysControls: EditorControllerPathways = EditorControllerPathways(this)
    override var nodes: (MutableStoryNode, Set[MutableStoryNode]) = StoryNodeConverter.fromImmutableToMutable(routeNode)
    override val graph: Graph = GraphBuilder.build(nodes._1)

    val graphViewer: Viewer = graph.display()
    decorateGraphGUI()

    override def execute(): Unit = editorView.render()

    override def close(): Unit = {
      graphViewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER)
      graphViewer.close()
      ApplicationController.execute()
    }

    override def save(path: String): Unit =
      StoryNodeSerializer.serializeStory(StoryNodeConverter.fromMutableToImmutable(nodes._1)._1, path)

    override def switchNodesNarrativeVisibility(): Unit = {
      printNodeNarrative = !printNodeNarrative
      decorateGraphGUI()
    }

    override def switchPathwaysDescriptionVisibility(): Unit = {
      printEdgeLabel = !printEdgeLabel
      decorateGraphGUI()
    }

    def decorateGraphGUI(): Unit = {
      graph.nodes().forEach(n => {
        if(n.getId != nodes._1.id.toString) {
          setupNonRouteNode(n.getId)
        } else {
          setupRouteNode()
        }
      })
      graph.edges().forEach(e => setupEdge(
        e.getId.split(StringUtils.pathwayIdSeparator)(0),
        e.getId.split(StringUtils.pathwayIdSeparator)(1))
      )
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
        graph.getEdge(startNodeId + StringUtils.pathwayIdSeparator + endNodeId),
        startNode.pathways.find(p => p.destinationNode.id.toString == endNodeId).get.prerequisite.nonEmpty
      )
      ElementLabel.putLabelOnElement(
        graph.getEdge(startNodeId + StringUtils.pathwayIdSeparator + endNodeId), printEdgeLabel
      )(
        StringUtils.buildLabel(
          startNodeId + StringUtils.pathwayIdSeparator + endNodeId,
          startNode.pathways.find(p => p.destinationNode.id.toString == endNodeId).get.description
        ),
        ""
      )
    }

    override def goToInfo(): Unit =
      InfoController(this, fromMutableToImmutable(nodes._1)._1).execute()

  }

  def apply(routeNode: StoryNode): EditorController = new EditorControllerImpl(routeNode)
}

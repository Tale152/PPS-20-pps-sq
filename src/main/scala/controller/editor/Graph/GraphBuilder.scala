package controller.editor.Graph

import controller.editor.Graph.EdgeInfo.EdgeInfo
import controller.editor.Graph.GraphBuilderUtils._
import model.nodes.StoryNode
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

object GraphBuilder {

  private val RouteNodeColor: String = "red"
  private val MidNodeColor: String = "black"
  private val FinalNodeColor: String = "blue"

  private val LabelAttribute: String = "ui.label"
  private val StyleLabel: String = "ui.style"

  def build(routeNode: StoryNode,
            printNodeNarrative: Boolean = true,
            printEdgeLabel: Boolean = true): Graph =
    populateGraph(createGraph(), routeNode, printNodeNarrative, printEdgeLabel)

  private def createGraph(): Graph = {
    val graph: Graph = new SingleGraph("g")
    graph.setStrict(false)
    graph.setAutoCreate(true)
    graph
  }

  private def populateGraph(graph: Graph,
                    routeNode: StoryNode,
                    printNodeNarrative: Boolean,
                    printEdgeLabel: Boolean): Graph = {

    def computeNode (node: StoryNode, computed: Set[StoryNode]): (Set[EdgeInfo], Set[StoryNode]) = {
      var computedResult = computed
      var edgesResult = Set[EdgeInfo]()
      for(pathway <- node.pathways) {
        if(!computed.contains(pathway.destinationNode)){
          val c = computeNode(pathway.destinationNode, computedResult)
          computedResult = computedResult ++ c._2
          edgesResult = edgesResult ++ c._1
        }
        edgesResult = edgesResult + EdgeInfo(node, pathway)
      }
      computedResult = computedResult + node
      (edgesResult, computedResult)
    }

    for(edge <- computeNode(routeNode, Set())._1){
      graph.addEdge(edge.getEdgeId, edge.startNodeId, edge.endNodeId)
      val newEdge = graph.getEdge(edge.getEdgeId)
      newEdge.setAttribute(LabelAttribute, getCorrectString(printEdgeLabel, edge.getPathwayLabel, ""))
      newEdge.setAttribute(StyleLabel, getCorrectEdgeColor(edge.isConditionalEdge, "purple", "black"))
      val newNode = graph.getNode(edge.endNodeId)
      newNode.setAttribute(LabelAttribute, getCorrectString(printNodeNarrative, edge.getEndNodeLabel, edge.endNodeId))
      newNode.setAttribute(StyleLabel, getCorrectNodeColor(edge.isFinalNode, FinalNodeColor, MidNodeColor))
    }
    val rNode = graph.getNode(routeNode.id.toString)
    rNode.setAttribute(StyleLabel, getNodeStyle(RouteNodeColor))
    rNode.setAttribute(
      LabelAttribute,
      getCorrectString(
        printNodeNarrative,
        buildLabel(routeNode.id.toString, truncateString(routeNode.narrative)),
        routeNode.id.toString
      )
    )
    graph
  }
}

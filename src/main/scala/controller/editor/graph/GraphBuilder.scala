package controller.editor.graph

import controller.editor.graph.EdgeInfo.EdgeInfo
import controller.editor.graph.util.{ElementLabel, ElementStyle, StringUtils}
import model.nodes.StoryNode
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

object GraphBuilder {

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

    if(routeNode.pathways.nonEmpty){
      for(edgeInfo <- computeNode(routeNode, Set())._1){
        graph.addEdge(edgeInfo.getEdgeId, edgeInfo.startNodeId, edgeInfo.endNodeId)
        val newEdge = graph.getEdge(edgeInfo.getEdgeId)
        ElementLabel.putLabelOnElement(newEdge, printEdgeLabel)(edgeInfo.getPathwayLabel, edgeInfo.getEdgeId)
        ElementStyle.decorateEdge(newEdge, edgeInfo.isConditionalEdge)
        val newNode = graph.getNode(edgeInfo.endNodeId)
        ElementLabel.putLabelOnElement(newNode, printNodeNarrative)(edgeInfo.getEndNodeLabel, edgeInfo.endNodeId)
        ElementStyle.decorateNode(newNode, edgeInfo.isNodeWithEvents, edgeInfo.isNodeWithEnemy, edgeInfo.isFinalNode)
      }
    } else {
      graph.addNode(routeNode.id.toString)
    }

    val rNode = graph.getNode(routeNode.id.toString)
    ElementStyle.decorateRouteNode(rNode, routeNode.events.nonEmpty, routeNode.enemy.nonEmpty)
    ElementLabel.putLabelOnElement(rNode, printNodeNarrative)(
      StringUtils.buildLabel(routeNode.id.toString, StringUtils.truncateString(routeNode.narrative)),
      routeNode.id.toString
    )
    graph
  }
}

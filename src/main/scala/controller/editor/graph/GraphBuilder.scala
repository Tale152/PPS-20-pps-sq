package controller.editor.graph

import controller.editor.graph.EdgeInfo.EdgeInfo
import model.nodes.StoryNode
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

object GraphBuilder {

  def build(routeNode: StoryNode): Graph = {
    val res = populateGraph(createGraph(), routeNode)
    res.setAutoCreate(false)
    res
  }

  private def createGraph(): Graph = {
    val graph: Graph = new SingleGraph("g")
    graph.setStrict(false)
    graph.setAutoCreate(true)
    graph
  }

  private def populateGraph(graph: Graph, routeNode: StoryNode): Graph = {

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
      computeNode(routeNode, Set())._1.foreach(e => graph.addEdge(e.getEdgeId, e.startNodeId, e.endNodeId, true))
    } else {
      graph.addNode(routeNode.id.toString)
    }
    graph
  }
}

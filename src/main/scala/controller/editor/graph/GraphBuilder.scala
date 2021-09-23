package controller.editor.graph

import model.nodes.StoryNode
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph

/**
Used to build a GraphStream's Graph starting from a StoryNode that will be used as root.
 */
object GraphBuilder {

  /**
   * @param routeNode the StoryNode that will be used as root for the GraphStream's Graph
   * @return the GraphStream's Graph builded using the routeNode
   */
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

    def _computeNode (node: StoryNode, computed: Set[StoryNode]): (Set[EdgeInfo], Set[StoryNode]) = {
      var computedResult = computed
      var edgesResult = Set[EdgeInfo]()
      for(pathway <- node.pathways) { //TODO for comprehension?
        if(!computed.contains(pathway.destinationNode)){
          val c = _computeNode(pathway.destinationNode, computedResult)
          computedResult ++= c._2
          edgesResult ++= c._1
        }
        edgesResult = edgesResult + EdgeInfo(node, pathway)
      }
      computedResult = computedResult + node
      (edgesResult, computedResult)
    }

    if(routeNode.pathways.nonEmpty){
      _computeNode(routeNode, Set())._1.foreach(e => graph.addEdge(e.getEdgeId, e.startNodeId, e.endNodeId, true))
    } else {
      graph.addNode(routeNode.id.toString)
    }
    graph
  }
}

package controller.editor

import model.nodes.{MutablePathway, MutableStoryNode, Pathway, StoryNode}

object StoryNodeConverter {

  def fromImmutableToMutable(routeNode: StoryNode): (MutableStoryNode, Set[MutableStoryNode]) = {
    def traverse(node: StoryNode, traversed: Set[MutableStoryNode]): Set[MutableStoryNode] = {
      var t = traversed
      var pathways = Set[MutablePathway]()
      for(p <- node.pathways){
        if(!t.exists(n => n.id == p.destinationNode.id)){
          val traversedNodeSet = traverse(p.destinationNode, t)
          t = t ++ traversedNodeSet
        }
        pathways = pathways + MutablePathway(
          p.description,
          t.filter(n => n.id == p.destinationNode.id).last,
          p.prerequisite
        )
      }
      t + MutableStoryNode(node.id, node.narrative, node.enemy, pathways, node.events)
    }
    val t = traverse(routeNode, Set())
    (t.filter(n => n.id == routeNode.id).last, t)
  }

  def fromMutableToImmutable(routeNode: MutableStoryNode): (StoryNode, Set[StoryNode]) = {
    def traverse(node: MutableStoryNode, traversed: Set[StoryNode]): Set[StoryNode] = {
      var t = traversed
      var pathways = Set[Pathway]()
      for(p <- node.mutablePathways){
        if(!t.exists(n => n.id == p.destinationNode.id)){
          val traversedNodeSet = traverse(p.destinationNode, t)
          t = t ++ traversedNodeSet
        }
        pathways = pathways + Pathway(
          p.description,
          t.filter(n => n.id == p.destinationNode.id).last,
          p.prerequisite
        )
      }
      t + StoryNode(node.id, node.narrative, node.enemy, pathways, node.events)
    }
    val t = traverse(routeNode, Set())
    (t.filter(n => n.id == routeNode.id).last, t)
  }

}

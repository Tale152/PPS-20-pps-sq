package controller.editor

import model.nodes.StoryNode.MutableStoryNode
import model.nodes.{MutablePathway, Pathway, StoryNode}

/**
 * Utility object used to convert stories that use StoryNodes and Pathways
 * to stories that use MutableStoryNodes and MutablePathways and the other way around.
 */
object StoryNodeConverter {

  /**
   * Taking a StoryNode as a route node, recreates the same nodes structures using MutableStoryNodes.
   * @param routeNode the StoryNode that is the route of a story
   * @return a pair containing the new mutable route node and a set with all mutable nodes composing the story
   */
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

  /**
   * Taking a MutableStoryNode as a route node, recreates the same nodes structures using
   * StoryNodes that aren't mutable.
   * @param routeNode the MutableStoryNode that is the route of a story
   * @return a pair containing the new immutable route node and a set with all immutable nodes composing the story
   */
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

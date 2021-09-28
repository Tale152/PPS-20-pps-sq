package controller.editor

import controller.editor.StoryNodeConverter.StoryNodeConverterType.{ConversionType, Immutable, Mutable}
import model.nodes.StoryNode.MutableStoryNode
import model.nodes.{MutablePathway, Pathway, StoryNode}

/**
 * Utility object used to convert stories that use StoryNodes and Pathways
 * to stories that use MutableStoryNodes and MutablePathways and the other way around.
 */
object StoryNodeConverter {

  object StoryNodeConverterType {

    sealed trait ConversionType

    case object Mutable extends ConversionType

    case object Immutable extends ConversionType

  }

  /**
   * Implicit class used to do conversion operation of an Immutable [[model.nodes.StoryNode]].
   *
   * @param routeNode the [[model.nodes.StoryNode]]
   */
  implicit class ImmutableStoryNodeConverter(routeNode: StoryNode) {
    /**
     * Taking a StoryNode as a route node, recreates the same nodes structures using MutableStoryNodes.
     *
     * @return a pair containing the new mutable route node and a set with all mutable nodes composing the story
     */
    def toMutable: (MutableStoryNode, Set[MutableStoryNode]) = {
      val t = traverse(routeNode, Set(), Mutable)
      (t.filter(n => n.id == routeNode.id).last.asInstanceOf[MutableStoryNode], t.asInstanceOf[Set[MutableStoryNode]])
    }
  }

  /**
   * Implicit class used to do conversion operation of a [[model.nodes.StoryNode]].
   *
   * @param routeNode the MutableStoryNode that is the route of a story
   */
  implicit class MutableStoryNodeConverter(routeNode: MutableStoryNode) {
    /**
     * Taking a MutableStoryNode as a route node, recreates the same nodes structures using
     * StoryNodes that aren't mutable.
     *
     * @return a pair containing the new immutable route node and a set with all immutable nodes composing the story
     */
    def toImmutable: (StoryNode, Set[StoryNode]) = {
      val t = traverse(routeNode, Set(), Immutable)
      (t.filter(n => n.id == routeNode.id).last, t)
    }
  }

  private def traverse(node: StoryNode, traversed: Set[StoryNode], conversionType: ConversionType): Set[StoryNode] = {
    var t = traversed
    val pathways = for (p <- node.pathways) yield {
      if (!t.exists(n => n.id == p.destinationNode.id)) {
        t ++= traverse(p.destinationNode, t, conversionType)
      }
      instantiatePathway(p, t, conversionType)
    }
    enrichTraversed(node, t, pathways, conversionType)
  }

  private def instantiatePathway(p: Pathway, t: Set[StoryNode], conversionType: ConversionType): Pathway = {
    val description = p.description
    val destinationNode = t.filter(n => n.id == p.destinationNode.id).last
    val prerequisite = p.prerequisite
    conversionType match {
      case Immutable => Pathway(description, destinationNode, prerequisite)
      case Mutable => MutablePathway(description, destinationNode.asInstanceOf[MutableStoryNode], prerequisite)
    }
  }

  private def enrichTraversed(node: StoryNode,
                              traversed: Set[StoryNode],
                              pathways: Set[Pathway],
                              conversionType: ConversionType): Set[StoryNode] =
    conversionType match {
      case Immutable => traversed + StoryNode(
        node.id, node.narrative, node.enemy, pathways, node.events
      )
      case Mutable => traversed + MutableStoryNode(
        node.id, node.narrative, node.enemy, pathways.asInstanceOf[Set[MutablePathway]], node.events
      )
    }

}

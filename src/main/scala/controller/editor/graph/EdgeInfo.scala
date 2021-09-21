package controller.editor.graph

import controller.editor.graph.util.StringUtils
import controller.editor.graph.util.StringUtils.{buildLabel, truncateString}
import model.nodes.{Pathway, StoryNode}

protected case class EdgeInfo(private val startingNode: StoryNode, private val pathway: Pathway) {

  val isFinalNode: Boolean = pathway.destinationNode.pathways.isEmpty

  val isConditionalEdge: Boolean = pathway.prerequisite.nonEmpty

  val startNodeId: String = startingNode.id.toString

  val endNodeId: String = pathway.destinationNode.id.toString

  val getEdgeId: String = startNodeId + StringUtils.pathwayIdSeparator + endNodeId

  val endNodeNarrative:String = truncateString(pathway.destinationNode.narrative)

  val pathwayDescription: String = truncateString(pathway.description)

  val getEndNodeLabel: String = buildLabel(endNodeId, endNodeNarrative)

  val getPathwayLabel: String = buildLabel(getEdgeId, pathwayDescription)

  val isNodeWithEnemy: Boolean = pathway.destinationNode.enemy.nonEmpty

  val isNodeWithEvents: Boolean = pathway.destinationNode.events.nonEmpty

}

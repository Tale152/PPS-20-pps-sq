package controller.editor.graph

import controller.editor.graph.util.StringUtils
import controller.editor.graph.util.StringUtils.{buildLabel, truncateString}
import model.nodes.{Pathway, StoryNode}

protected object EdgeInfo {
  class EdgeInfo(startingNode: StoryNode, pathway: Pathway) {

    def isFinalNode: Boolean = pathway.destinationNode.pathways.isEmpty

    def isConditionalEdge: Boolean = pathway.prerequisite.nonEmpty

    def startNodeId: String = startingNode.id.toString

    def endNodeId: String = pathway.destinationNode.id.toString

    def getEdgeId: String = startNodeId + StringUtils.pathwayIdSeparator + endNodeId

    def endNodeNarrative:String = truncateString(pathway.destinationNode.narrative)

    def pathwayDescription: String = truncateString(pathway.description)

    def getEndNodeLabel: String = buildLabel(endNodeId, endNodeNarrative)

    def getPathwayLabel: String = buildLabel(getEdgeId, pathwayDescription)

    def isNodeWithEnemy: Boolean = pathway.destinationNode.enemy.nonEmpty

    def isNodeWithEvents: Boolean = pathway.destinationNode.events.nonEmpty

  }

  def apply(startingNode: StoryNode, pathway: Pathway): EdgeInfo = new EdgeInfo(startingNode, pathway)
}

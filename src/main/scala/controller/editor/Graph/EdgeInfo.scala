package controller.editor.Graph

import controller.editor.Graph.GraphBuilderUtils.{buildLabel, truncateString}
import model.nodes.{Pathway, StoryNode}

protected object EdgeInfo {
  class EdgeInfo(startingNode: StoryNode, pathway: Pathway) {

    def isFinalNode: Boolean = pathway.destinationNode.pathways.isEmpty

    def startNodeId: String = startingNode.id.toString

    def endNodeId: String = pathway.destinationNode.id.toString

    def getEdgeId: String = startNodeId + " to " + endNodeId

    def endNodeNarrative:String = truncateString(pathway.destinationNode.narrative)

    def pathwayDescription: String = truncateString(pathway.description)

    def getEndNodeLabel: String = buildLabel(endNodeId, endNodeNarrative)

    def getPathwayLabel: String = buildLabel(getEdgeId, pathwayDescription)
  }

  def apply(startingNode: StoryNode, pathway: Pathway): EdgeInfo = new EdgeInfo(startingNode, pathway)
}

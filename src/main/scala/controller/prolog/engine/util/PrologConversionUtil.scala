package controller.prolog.engine.util

import controller.prolog.PrologNames.Predicates.StoryNodePredicate
import controller.prolog.PrologNames.Records.PathwayRecord
import model.nodes.{Pathway, StoryNode}

/**
 * Utility classes that adds method to StoryNode and Pathway. Useful for Prolog conversion.
 */
object PrologConversionUtil {

  implicit class PrologStoryNode(storyNode: StoryNode) {

    /**
     * @return a string formatted as a prolog fact for the [[model.nodes.StoryNode]] class:
     *         story_node(id,narrative,[pathway]).
     */
    def toPrologFact: String = {
      StoryNodePredicate + "(" + storyNode.id + ",'" + storyNode.narrative + "',[" +
        storyNode.pathways.map(p => p.toPrologRecord).mkString(",") + "])."
    }

  }

  implicit class PrologPathway(pathway: Pathway) {

    /**
     * @return a string formatted as a prolog record for the [[model.nodes.StoryNode]] class:
     *         pathway(toId, description).
     */
    def toPrologRecord: String = {
      PathwayRecord + "(" + pathway.destinationNode.id + ",'" + pathway.description + "')"
    }
  }

}

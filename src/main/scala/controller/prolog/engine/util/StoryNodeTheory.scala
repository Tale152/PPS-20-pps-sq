package controller.prolog.engine.util

import controller.prolog.engine.util.PrologConversionUtil.PrologStoryNode
import model.nodes.StoryNode

object StoryNodeTheory {

  /**
   * @param storyNode A StoryNode
   * @return a Set of prolog facts containing all the story_node reachable from the passed StoryNode.
   */
  def apply(storyNode: StoryNode): Set[String] = {

    var acc: Set[String] = Set()

    def _generateFacts(storyNode: StoryNode): Set[String] = {
      if (storyNode.pathways.nonEmpty) {
        storyNode.pathways.foreach(p => acc = acc ++ _generateFacts(p.destinationNode))
      }
      acc + storyNode.toPrologFact
    }
    _generateFacts(storyNode)
  }

}

package controller.prolog

import alice.tuprolog.Theory
import controller.prolog.util.PrologImplicits.PrologStoryNode
import controller.util.Resources.ResourceName.prologEngineTheoryPath
import controller.util.Resources.resourcesAsLines
import model.nodes.StoryNode

/**
 * Object that encapsulates the Theory classes used by the [[controller.prolog.engine.SqPrologEngine]].
 */
object EngineTheory {

  /**
   * Loads the theory from Prolog file. This theory is in any [[controller.prolog.engine.SqPrologEngine]].
   */
  case class EngineFileTheory() extends Theory(resourcesAsLines(prologEngineTheoryPath).mkString("\n") + "\n")

  /**
   * Generate facts from the [[model.nodes.StoryNode]] passed.
   *
   * @param storyNode the [[model.nodes.StoryNode]] used to generate the facts for the theory.
   */
  case class StoryNodeTheory(storyNode: StoryNode) extends Theory(generateFacts(storyNode).mkString("\n") + "\n")

  def generateFacts(storyNode: StoryNode): Set[String] = {
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

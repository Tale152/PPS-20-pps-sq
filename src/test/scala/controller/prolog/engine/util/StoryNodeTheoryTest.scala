package controller.prolog.engine.util

import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec
import controller.prolog.PrologNames.Predicates.StoryNodePredicate

class StoryNodeTheoryTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(1, "narrative", None, Set.empty, List())
  val pathway: Pathway = Pathway("description", destinationNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(pathway), List())

  "The theory" should "be formatted correctly" in {
    val theory: Set[String] = StoryNodeTheory(startingNode)
    theory.size shouldEqual 2
    theory should contain (StoryNodePredicate + "(0,'narrative',[pathway(1,'description')]).")
    theory should contain (StoryNodePredicate + "(1,'narrative',[]).")
  }
}

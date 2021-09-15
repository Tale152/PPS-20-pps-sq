package controller.prolog.predicates

import controller.prolog.PrologNames.Predicates.StoryNodePredicate
import controller.prolog.engine.SqPrologEngine
import model.nodes.{Pathway, StoryNode}
import controller.prolog.engine.util.PrologEngineUtil._
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class StoryNodeNarrativePredicateTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(2, "narrative", None, Set.empty, List())
  val destinationPathway: Pathway = Pathway("description", destinationNode, None)
  val middleNode: StoryNode = StoryNode(1, "narrative", None, Set(destinationPathway), List())
  val middlePathway: Pathway = Pathway("description", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(middlePathway), List())

  var engine: SqPrologEngine =  SqPrologEngine(startingNode)

  it should "not find solution for a node with index 3" in {
    engine(StoryNodePredicate + "(3,N,P)").size shouldEqual 0
  }

  it should "find one solution for a node with index 0 and 1 and 2" in {
    engine(StoryNodePredicate + "(0,N,P)").size shouldEqual 1
    engine(StoryNodePredicate + "(1,N,P)").size shouldEqual 1
    engine(StoryNodePredicate + "(2,N,P)").size shouldEqual 1
  }

  it should "find all the solution passing only vars" in {
    engine(StoryNodePredicate + "(X,Y,Z)").size shouldEqual 3
  }

}

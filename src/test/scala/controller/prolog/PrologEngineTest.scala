package controller.prolog

import alice.tuprolog.Term
import controller.prolog.PrologNames.Predicates.StoryNodePredicate
import controller.prolog.engine.PrologEngine
import controller.prolog.engine.util.StoryNodeTheory
import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec
import controller.prolog.engine.util.PrologEngineUtil._

class PrologEngineTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(1, "narrative", None, Set.empty, List())
  val pathway: Pathway = Pathway("description", destinationNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(pathway), List())

  var engine: PrologEngine = _

  "The prolog engine" should "load a theory correctly" in {
    engine = PrologEngine(StoryNodeTheory(startingNode))
  }

  it should "not find solution for a node with index 2" in {
    engine(StoryNodePredicate + "(2,N,P)").size shouldEqual 0
  }

  it should "find one solution for a node with index 0" in {
    val solutions: Stream[Term] = engine(StoryNodePredicate + "(0,N,P)")
    solutions.size shouldEqual 1
  }

  it should "find all the solution passing only vars" in {
    engine(StoryNodePredicate + "(I,N,P)").size shouldEqual 2
  }

}

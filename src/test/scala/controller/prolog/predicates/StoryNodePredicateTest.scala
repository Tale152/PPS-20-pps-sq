package controller.prolog.predicates

import alice.tuprolog.{Term, Var}
import controller.prolog.engine.SqPrologEngine
import controller.prolog.engine.predicates.Predicates.StoryNodePredicate
import controller.prolog.engine.util.PrologEngineUtil._
import model.nodes.{Pathway, StoryNode}
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class StoryNodePredicateTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(2, "narrative", None, Set.empty, List())
  val destinationPathway: Pathway = Pathway("description", destinationNode, None)
  val middleNode: StoryNode = StoryNode(1, "narrative", None, Set(destinationPathway), List())
  val middlePathway: Pathway = Pathway("description", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(middlePathway), List())

  var engine: SqPrologEngine =  SqPrologEngine(startingNode)

  it should "not find solution for a node with index 3" in {
    engine(StoryNodePredicate(3, new Var(), new Var())).size shouldEqual 0
  }

  it should "find one solution for a node with index 0 and 1 and 2" in {
    val predicates = for(i <- 0 to 2) yield StoryNodePredicate(i, new Var(), new Var)
    predicates.foreach(p => {
      engine(p).head.extractTerm(1).toString shouldEqual "narrative"
      engine(p).size shouldEqual 1
    })
    engine(predicates(0)).head
      .extractTerm(2)
      .extractTerm(0)
      .extractTerm(0)
      .toString.toInt shouldEqual 1
    engine(predicates(1)).head
      .extractTerm(2)
      .extractTerm(0)
      .extractTerm(0)
      .toString.toInt shouldEqual 2
    engine(predicates(2)).head.extractTerm(2).isEmptyList shouldBe true
  }

  it should "find all the solution passing only vars" in {
    val solutions : Stream[Term] = engine(StoryNodePredicate(new Var(), new Var(), new Var()))
    solutions.size shouldEqual 3
    solutions.foreach(s => s.extractTerm(1).toString shouldEqual "narrative")
  }

}

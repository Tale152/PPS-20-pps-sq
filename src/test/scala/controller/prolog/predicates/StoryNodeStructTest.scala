package controller.prolog.predicates

import alice.tuprolog.Var
import controller.prolog.engine.SqPrologEngine
import controller.prolog.engine.structs.StoryNodeStruct
import controller.prolog.engine.util.PrologImplicits._
import model.nodes.{Pathway, StoryNode}
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class StoryNodeStructTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(2, "narrative", None, Set.empty, List())
  val destinationPathway: Pathway = Pathway("description", destinationNode, None)
  val middleNode: StoryNode = StoryNode(1, "narrative", None, Set(destinationPathway), List())
  val middlePathway: Pathway = Pathway("description", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(middlePathway), List())

  var engine: SqPrologEngine =  SqPrologEngine(startingNode)

  it should "not find solution for a node with index 3" in {
    engine.resolve(StoryNodeStruct(3, new Var(), new Var())).size shouldEqual 0
  }

  it should "find one solution for a node with index 0 and 1 and 2" in {
    val predicates = for(i <- 0 to 2) yield StoryNodeStruct(i, new Var(), new Var)
    predicates.foreach(p => {
      engine.resolve(p).head.narrative shouldEqual "narrative"
      engine.resolve(p).size shouldEqual 1
    })
    engine.resolve(predicates(0)).head.pathways.head._1 shouldEqual 1
    engine.resolve(predicates(1)).head.pathways.head._1 shouldEqual 2
    engine.resolve(predicates(2)).head.pathways shouldBe empty
  }

  it should "find all the solution passing only vars" in {
    val solutions = engine.resolve(StoryNodeStruct(new Var(), new Var(), new Var()))
    solutions.size shouldEqual 3
    solutions.foreach(s => s.narrative shouldEqual "narrative")
  }

}

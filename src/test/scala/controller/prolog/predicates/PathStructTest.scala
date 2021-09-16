package controller.prolog.predicates

import alice.tuprolog.Var
import controller.prolog.engine.SqPrologEngine
import controller.prolog.engine.structs.PathStruct
import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec
import controller.prolog.engine.util.PrologImplicits._
import org.scalatest.DoNotDiscover

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class PathStructTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(2, "narrative", None, Set.empty, List())
  val destinationPathway: Pathway = Pathway("description", destinationNode, None)
  val middleNode: StoryNode = StoryNode(1, "narrative", None, Set(destinationPathway), List())
  val middlePathway: Pathway = Pathway("description", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(middlePathway), List())

  var engine: SqPrologEngine =  SqPrologEngine(startingNode)
  it should "find a path that leads from 0 to 1 and a path that leads from 1 to 2" in {
    val zeroToOneSolutions = engine.resolve(PathStruct(0, 1, new Var()))
    val oneToTwoSolutions =  engine.resolve(PathStruct(1, 2, new Var()))

    zeroToOneSolutions.size shouldEqual 1
    oneToTwoSolutions.size shouldEqual 1
    zeroToOneSolutions.head.crossedIds shouldEqual Seq(0, 1)
    oneToTwoSolutions.head.crossedIds shouldEqual Seq(1, 2)
  }

  it should "find a path that leads from 0 to 2" in {
    val solutions = engine.resolve(PathStruct(0, 2, new Var()))
    solutions.head.crossedIds shouldEqual Seq(0, 1, 2)
    solutions.size shouldEqual 1
  }

  it should "not find a path that leads from 2 to 1" in {
    engine.resolve(PathStruct(2, 1, new Var())).size shouldEqual 0
  }

}

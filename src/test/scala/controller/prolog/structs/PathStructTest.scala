package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
import controller.prolog.structs.StructUtil.storyNodeWithAMiddleNodeAndTwoFinalNodes
import controller.prolog.{SqPrologEngine, structs}
import model.nodes.StoryNode
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._
import org.scalatest.DoNotDiscover

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class PathStructTest extends FlatTestSpec {

  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()

  var engine: SqPrologEngine = prolog.SqPrologEngine(startingNode)

  "The Prolog engine" should "find a path that leads from 0 to 1 and a path that leads from 1 to 2" in {
    val zeroToOneSolutions = engine.resolve(PathStruct(0, 1, new Var()))
    val oneToTwoSolutions = engine.resolve(structs.PathStruct(1, 2, new Var()))

    zeroToOneSolutions.size shouldEqual 1
    oneToTwoSolutions.size shouldEqual 1
    zeroToOneSolutions.head.crossedIds shouldEqual Seq(0, 1)
    oneToTwoSolutions.head.crossedIds shouldEqual Seq(1, 2)
  }

  it should "find a path that leads from 0 to 2" in {
    val solutions = engine.resolve(structs.PathStruct(0, 2, new Var()))
    solutions.head.crossedIds shouldEqual Seq(0, 1, 2)
    solutions.size shouldEqual 1
  }

  it should "not find a path that leads from 2 to 1" in {
    engine.resolve(structs.PathStruct(2, 1, new Var())).size shouldEqual 0
  }

}

package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog.SqPrologEngine
import controller.prolog.structs.StructUtil.storyNodeWithAMiddleNodeAndTwoFinalNodes
import model.nodes.StoryNode
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class PathwayDescriptionStructTest extends FlatTestSpec {
  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()
  val engine: SqPrologEngine = SqPrologEngine(startingNode)

  "The Prolog engine" should "find the description of the pathway" in {
    val solutions = engine.resolve(PathwayDescriptionStruct(0, 1, new Var()))
    solutions.size shouldEqual 1
    solutions.head.description shouldEqual "description(0-1)"
  }

  it should "find no solution using wrong ids" in {
    engine.resolve(PathwayDescriptionStruct(1, 0, new Var())).size shouldEqual 0
  }
}

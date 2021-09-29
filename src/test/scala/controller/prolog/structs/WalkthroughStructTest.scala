package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
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
class WalkthroughStructTest extends FlatTestSpec {

  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()

  val engine: SqPrologEngine = prolog.SqPrologEngine(startingNode)

  "The Prolog Engine" should "output the correct walkthrough" in {
    val solutions = engine.resolve(WalkthroughStruct(Seq(0, 1, 2), new Var()))
    solutions.size shouldEqual 1
    solutions.head.walkthrough shouldEqual Seq(
      "narrative(0)",
      "description(0-1)",
      "narrative(1)",
      "description(1-2)",
      "narrative(2)"
    )
  }
}

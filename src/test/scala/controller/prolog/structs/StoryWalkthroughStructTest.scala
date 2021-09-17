package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
import controller.prolog.SqPrologEngine
import controller.prolog.structs.StructUtil.{areAllSolutionPresent, storyNodeWithAMiddleNodeAndTwoFinalNodes}
import model.nodes.StoryNode
import specs.FlatTestSpec
import org.scalatest.DoNotDiscover
import controller.prolog.util.PrologImplicits._

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class StoryWalkthroughStructTest extends FlatTestSpec {

  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()

  var engine: SqPrologEngine = prolog.SqPrologEngine(startingNode)

  "The Prolog Engine" should "calculate correctly the stories walkthrough" in {
    val solutions = engine.resolve(StoryWalkthroughStruct(0, new Var()))
    solutions.size shouldEqual 2
    areAllSolutionPresent[String](
      Set(
        Seq("narrative(0)", "description(0-1)", "narrative(1)", "description(1-2)", "narrative(2)"),
        Seq("narrative(0)", "description(0-1)", "narrative(1)", "description(1-3)", "narrative(3)")
      ),
      solutions.head.walkthrough,
      solutions(1).walkthrough) shouldBe true
  }
}

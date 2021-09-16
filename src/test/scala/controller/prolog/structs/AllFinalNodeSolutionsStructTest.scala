package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
import controller.prolog.SqPrologEngine
import controller.prolog.structs.StructUtil.{areAllSolutionPresent, storyNodeWithAMiddleNodeAndTwoFinalNodes}
import model.nodes.StoryNode
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class AllFinalNodeSolutionsStructTest extends FlatTestSpec {
  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()

  var engine: SqPrologEngine =  prolog.SqPrologEngine(startingNode)

  "The prolog engine" should " find all the solutions calling AllFinalNodeSolutions on the starting node" in {
    val solutions = engine.resolve(AllFinalNodeSolutionsStruct(0, new Var()))
    solutions.size shouldEqual 1
    solutions.head.allCrossedNodes.size shouldEqual 2
    areAllSolutionPresent[Int](
      Set(Seq(0,1,2), Seq(0,1,3)),
      solutions.head.allCrossedNodes.head,
      solutions.head.allCrossedNodes(1)
    ) shouldBe true
  }
}

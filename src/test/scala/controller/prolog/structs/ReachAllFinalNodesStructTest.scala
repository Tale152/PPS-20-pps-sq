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
class ReachAllFinalNodesStructTest extends FlatTestSpec {

  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()

  val engine: SqPrologEngine =  prolog.SqPrologEngine(startingNode)

  "The prolog engine" should "find 2 solution calling ReachAllFinalNodes on the starting node" in {
    val solutions = engine.resolve(ReachAllFinalNodesStruct(0, new Var()))
    solutions.size shouldEqual 2
    areAllSolutionPresent[Int](
      Set(Seq(0,1,2), Seq(0,1,3)),
      solutions.head.crossedNodes,
      solutions(1).crossedNodes
    ) shouldBe true
  }

  "The prolog engine" should " find 2 solution calling ReachAllFinalNodes on the middle node" in {
    val solutions = engine.resolve(ReachAllFinalNodesStruct(1, new Var()))
    solutions.size shouldEqual 2
    areAllSolutionPresent[Int](
      Set(Seq(1,2), Seq(1,3)),
      solutions.head.crossedNodes,
      solutions(1).crossedNodes
    ) shouldBe true
  }

  "The prolog engine" should " find 0 solution calling ReachAllFinalNodes on the final nodes" in {
    engine.resolve(ReachAllFinalNodesStruct(2, new Var())).size shouldEqual 0
    engine.resolve(ReachAllFinalNodesStruct(3, new Var())).size shouldEqual 0
  }

}

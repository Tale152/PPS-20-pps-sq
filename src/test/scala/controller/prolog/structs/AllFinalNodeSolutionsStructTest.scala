package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
import controller.prolog.SqPrologEngine
import controller.prolog.structs.StructUtil.areAllSolutionPresent
import model.nodes.{Pathway, StoryNode}
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class AllFinalNodeSolutionsStructTest extends FlatTestSpec {
  val secondDestinationNode: StoryNode = StoryNode(3, "narrative", None, Set.empty, List())
  val secondDestinationPathway: Pathway = Pathway("description", secondDestinationNode, None)
  val firstDestinationNode: StoryNode = StoryNode(2, "narrative", None, Set.empty, List())
  val firstDestinationPathway: Pathway = Pathway("description", firstDestinationNode, None)
  val middleNode: StoryNode = StoryNode(
    1, "narrative", None,
    Set(firstDestinationPathway, secondDestinationPathway),
    List()
  )
  val middlePathway: Pathway = Pathway("description", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(middlePathway), List())

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

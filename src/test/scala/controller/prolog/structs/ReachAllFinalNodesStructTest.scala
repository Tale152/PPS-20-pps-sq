package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
import controller.prolog.SqPrologEngine
import model.nodes.{Pathway, StoryNode}
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._
/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class ReachAllFinalNodesStructTest extends FlatTestSpec {

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

  "The prolog engine" should " find 2 solution calling ReachAllFinalNodes on the starting node" in {
    val solutions = engine.resolve(ReachAllFinalNodesStruct(0, new Var()))
    solutions.size shouldEqual 2
    checkSolutionsPresence(
      Set(Seq(0,1,2), Seq(0,1,3)),
      solutions.head.crossedNodes,
      solutions(1).crossedNodes
    )
  }

  "The prolog engine" should " find 2 solution calling ReachAllFinalNodes on the middle node" in {
    val solutions = engine.resolve(ReachAllFinalNodesStruct(1, new Var()))
    solutions.size shouldEqual 2
    checkSolutionsPresence(
      Set(Seq(1,2), Seq(1,3)),
      solutions.head.crossedNodes,
      solutions(1).crossedNodes
    )
  }

  "The prolog engine" should " find 0 solution calling ReachAllFinalNodes on the final nodes" in {
    engine.resolve(ReachAllFinalNodesStruct(2, new Var())).size shouldEqual 0
    engine.resolve(ReachAllFinalNodesStruct(3, new Var())).size shouldEqual 0
  }

  def checkSolutionsPresence(_expected: Set[Seq[Int]], solutions: Seq[Int]*): Unit = {
    var expected = _expected
    solutions.foreach(s => {
      expected should contain (s)
      expected -= s
    })
  }

}

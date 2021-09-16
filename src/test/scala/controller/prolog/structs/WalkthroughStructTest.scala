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
class WalkthroughStructTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(2, "narrative(2)", None, Set.empty, List())
  val destinationPathway: Pathway = Pathway("description(1-2)", destinationNode, None)
  val middleNode: StoryNode = StoryNode(1, "narrative(1)", None, Set(destinationPathway), List())
  val middlePathway: Pathway = Pathway("description(0-1)", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative(0)", None, Set(middlePathway), List())

  var engine: SqPrologEngine =  prolog.SqPrologEngine(startingNode)

  "The Prolog Engine" should "output the correct walkthrough" in {
    val solutions = engine.resolve(WalkthroughStruct(Seq(0,1,2), new Var()))
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

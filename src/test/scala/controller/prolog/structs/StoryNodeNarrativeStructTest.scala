package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog.SqPrologEngine
import model.nodes.StoryNode
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class StoryNodeNarrativeStructTest extends FlatTestSpec {
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(), List())

  val engine: SqPrologEngine = SqPrologEngine(startingNode)

  "The Prolog engine" should "find the narrative of the StoryNode" in {
    val solutions = engine.resolve(StoryNodeNarrativeStruct(0, new Var()))
    solutions.size shouldEqual 1
    solutions.head.narrative shouldEqual "narrative"
  }

  it should "find the ids of the pathway" in {
    val solutions = engine.resolve(StoryNodeNarrativeStruct(new Var(), "narrative"))
    solutions.size shouldEqual 1
    solutions.head.id shouldEqual 0

  }

  it should "find no solution using wrong ids or description" in {
    engine.resolve(StoryNodeNarrativeStruct(1, new Var())).size shouldEqual 0
    engine.resolve(StoryNodeNarrativeStruct(new Var(), "jojo")).size shouldEqual 0
  }
}

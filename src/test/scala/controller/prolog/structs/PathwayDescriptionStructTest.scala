package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog.SqPrologEngine
import model.nodes.{Pathway, StoryNode}
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec
import controller.prolog.util.PrologImplicits._

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class PathwayDescriptionStructTest extends FlatTestSpec {
  val finalNode: StoryNode = StoryNode(1, "narrative", None, Set(), List())
  val pathway: Pathway = Pathway("description", finalNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(pathway), List())

  val engine: SqPrologEngine = SqPrologEngine(startingNode)

  "The Prolog engine" should "find the description of the pathway" in {
    val solutions = engine.resolve(PathwayDescriptionStruct(0, 1, new Var()))
    solutions.size shouldEqual 1
    solutions.head.description shouldEqual "description"
  }

  it should "find no solution using wrong ids" in {
    engine.resolve(PathwayDescriptionStruct(1, 0, new Var())).size shouldEqual 0
  }
}

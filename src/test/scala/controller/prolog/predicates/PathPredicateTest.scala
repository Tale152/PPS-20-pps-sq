package controller.prolog.predicates

import controller.prolog.engine.SqPrologEngine
import model.nodes.{Pathway, StoryNode}
import specs.FlatTestSpec
import controller.prolog.engine.util.PrologEngineUtil._
import org.scalatest.DoNotDiscover

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class PathPredicateTest extends FlatTestSpec {

  val destinationNode: StoryNode = StoryNode(2, "narrative", None, Set.empty, List())
  val destinationPathway: Pathway = Pathway("description", destinationNode, None)
  val middleNode: StoryNode = StoryNode(1, "narrative", None, Set(destinationPathway), List())
  val middlePathway: Pathway = Pathway("description", middleNode, None)
  val startingNode: StoryNode = StoryNode(0, "narrative", None, Set(middlePathway), List())

  var engine: SqPrologEngine =  SqPrologEngine(startingNode)

  it should "find a path that leads from 0 to 1 and a path that leads from 1 to 2" in {
    engine("path(0,1,X)").size shouldEqual 1
    engine("path(1,2,X)").size shouldEqual 1
  }

  it should "find a path that leads from 0 to 2" in {
    engine("path(0,2,X)").size shouldEqual 1
  }

  it should "not find a path that leads from 2 to 1" in {
    engine("path(2,1,X)").size shouldEqual 0
  }

}

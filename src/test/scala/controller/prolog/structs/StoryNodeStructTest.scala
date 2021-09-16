package controller.prolog.structs

import alice.tuprolog.Var
import controller.prolog
import controller.prolog.structs.StructUtil.storyNodeWithAMiddleNodeAndTwoFinalNodes
import controller.prolog.{SqPrologEngine, structs}
import controller.prolog.util.PrologImplicits._
import model.nodes.StoryNode
import org.scalatest.DoNotDiscover
import specs.FlatTestSpec

/**
 * Tested in [[suites.PrologEngineSuite]].
 */
@DoNotDiscover
class StoryNodeStructTest extends FlatTestSpec {
  /**
   * Please @see [[controller.prolog.structs.StructUtil]] for a better understanding of the structure.
   */
  val startingNode: StoryNode = storyNodeWithAMiddleNodeAndTwoFinalNodes()
  var engine: SqPrologEngine =  prolog.SqPrologEngine(startingNode)

  "The Prolog engine" should "not find solution for a node with index 3" in {
    val invalidIndex = 4
    engine.resolve(StoryNodeStruct(invalidIndex, new Var(), new Var())).size shouldEqual 0
  }

  it should "find one solution for a node with index 0 and 1 and 2 and 3" in {
    val predicates = for(i <- 0 to 3) yield structs.StoryNodeStruct(i, new Var(), new Var)
    predicates.foreach(p => {
      engine.resolve(p).size shouldEqual 1
    })
    engine.resolve(predicates(0)).head.pathways.head._1 shouldEqual 1
    engine.resolve(predicates(1)).head.pathways.head._1 shouldEqual 2
    engine.resolve(predicates(2)).head.pathways shouldBe empty
    engine.resolve(predicates(2)).head.pathways shouldBe empty
  }

  it should "find all the solution passing only vars" in {
    val solutions = engine.resolve(structs.StoryNodeStruct(new Var(), new Var(), new Var()))
    solutions.size shouldEqual 4
  }

}

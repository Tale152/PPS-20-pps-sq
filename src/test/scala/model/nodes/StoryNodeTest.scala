package model.nodes

import org.scalatest.{FlatSpec, Matchers}

class StoryNodeTest extends FlatSpec with Matchers {

  val id: Int = 0
  var undefinedId: Int = _

  val narrative: String = "storyNodeNarrative"
  val emptyNarrative: String = ""
  var undefinedNarrative: String = _

  val pathways: Set[Pathway] = Set.empty
  var undefinedPathways: Set[Pathway] = _

  val node: StoryNode = StoryNode(id, narrative, pathways)

  "The story node" should "have an id" in {
    node.id shouldEqual id
  }

  it should "have a narrative" in {
    node.narrative shouldEqual narrative
  }

  it should "have a pathway" in {
    node.pathways shouldEqual pathways
  }

  it should "have a defined narrative" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, undefinedNarrative, pathways)
    }
  }

  it should "not have a empty narrative" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, pathways)
    }
  }

  it should "have a defined set of pathways" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, undefinedPathways)
    }
  }

}
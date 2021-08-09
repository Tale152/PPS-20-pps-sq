package model.nodes

import org.scalatest.{FlatSpec, Matchers}

class StoryNodeTest extends FlatSpec with Matchers {

  val id: Int = 0
  var undefinedId: Int = _

  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"
  val emptyNarrative: String = ""
  var undefinedNarrative: String = _

  val pathways: Set[Pathway] = Set.empty
  var undefinedPathways: Set[Pathway] = _

  val node: StoryNode = StoryNode(id, storyNodeNarrative, pathways)

  "The story node" should "have an id" in {
    node.id shouldEqual id
  }

  it should "have a narrative" in {
    node.narrative shouldEqual storyNodeNarrative
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

  it should "not contain multiple Pathways with same destination StoryNode" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, storyNodeNarrative, Set(Pathway(pathwayDescription, node), Pathway(pathwayDescription, node)))
    }
  }

}
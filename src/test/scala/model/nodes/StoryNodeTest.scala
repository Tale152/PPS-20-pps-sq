package model.nodes

import model.StoryModel
import specs.{FlatTestSpec, SerializableSpec}

class StoryNodeTest extends FlatTestSpec with SerializableSpec {

  val id: Int = 0
  var undefinedId: Int = _

  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"
  val emptyNarrative: String = ""
  var undefinedNarrative: String = _

  val pathways: Set[Pathway] = Set.empty
  var undefinedPathways: Set[Pathway] = _

  val events: List[StoryModel => Unit] = List()
  val undefinedEvents: List[StoryModel => Unit] = null

  val node: StoryNode = StoryNode(id, storyNodeNarrative, pathways, events)

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
      StoryNode(id, undefinedNarrative, pathways, events)
    }
  }

  it should "not have a empty narrative" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, pathways, events)
    }
  }

  it should "have a defined set of pathways" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, undefinedPathways, events)
    }
  }

  it should "not contain multiple Pathways with same destination StoryNode" in {
    intercept[IllegalArgumentException] {
      StoryNode(
        id,
        storyNodeNarrative,
        Set(Pathway(pathwayDescription, node, None), Pathway(pathwayDescription, node, None)),
        events
      )
    }
  }

  it should "contain at least one pathway with no condition if NOT final node" in {
    intercept[IllegalArgumentException] {
      StoryNode(
        id,
        storyNodeNarrative,
        Set(Pathway(pathwayDescription, node, Some(_ => true))),
        events
      )
    }
  }

  it should "have a defined list of events" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, pathways, undefinedEvents)
    }
  }

  it should behave like serializationTest(node)

}
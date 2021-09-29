package model.nodes

import mock.MockFactory.CharacterFactory
import model.characters.Enemy
import model.characters.properties.stats.StatName.Dexterity
import model.nodes.StoryNode.MutableStoryNode
import specs.{FlatTestSpec, SerializableSpec}

class MutableStoryNodeTest extends FlatTestSpec with SerializableSpec {

  val id: Int = 0
  var undefinedId: Int = _

  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"
  val emptyNarrative: String = ""
  var undefinedNarrative: String = _

  val enemy: Option[Enemy] = Some(CharacterFactory.mockEnemy())
  val emptyEnemy: Option[Enemy] = None
  var undefinedEnemy: Option[Enemy] = _

  val pathways: Set[MutablePathway] = Set.empty
  var undefinedPathways: Set[MutablePathway] = _

  val events: List[Event] = List()
  var undefinedEvents: List[Event] = _

  val node: MutableStoryNode = MutableStoryNode(id, storyNodeNarrative, enemy, pathways, events)

  "The story node" should "have an id" in {
    node.id shouldEqual id
  }

  it should "have a narrative" in {
    node.narrative shouldEqual storyNodeNarrative
  }

  it should "have an optional enemy" in {
    node.enemy shouldEqual enemy
  }

  it should "accept an empty enemy" in {
    val nodeWithEmptyEnemy: MutableStoryNode = MutableStoryNode(id, storyNodeNarrative, emptyEnemy, pathways, events)
    nodeWithEmptyEnemy.enemy shouldEqual None
  }

  it should "have a pathway" in {
    node.pathways shouldEqual pathways
  }

  it should "have a defined narrative" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(id, undefinedNarrative, enemy, pathways, events)
    }
  }

  it should "not have a empty narrative" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(id, emptyNarrative, enemy, pathways, events)
    }
  }

  it should "not have an undefined enemy" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(id, storyNodeNarrative, undefinedEnemy, pathways, events)
    }
  }

  it should "have a defined set of pathways" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(id, emptyNarrative, enemy, undefinedPathways, events)
    }
  }

  it should "not contain multiple Pathways with same destination StoryNode" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(
        id,
        storyNodeNarrative,
        enemy,
        Set(MutablePathway(pathwayDescription, node, None), MutablePathway(pathwayDescription, node, None)),
        events
      )
    }
  }

  it should "contain at least one pathway with no condition if NOT final node" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(
        id,
        storyNodeNarrative,
        enemy,
        Set(MutablePathway(pathwayDescription, node, Some(StatPrerequisite(Dexterity, 1)))),
        events
      )
    }
  }

  it should "have a defined list of events" in {
    intercept[IllegalArgumentException] {
      MutableStoryNode(id, emptyNarrative, enemy, pathways, undefinedEvents)
    }
  }

  it should behave like serializationTest(node)

}

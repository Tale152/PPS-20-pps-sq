package model.nodes

import model.characters.Enemy
import model.characters.properties.stats.{Stat, StatName}
import specs.{FlatTestSpec, SerializableSpec}

class StoryNodeTest extends FlatTestSpec with SerializableSpec {

  val id: Int = 0
  var undefinedId: Int = _

  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"
  val emptyNarrative: String = ""
  var undefinedNarrative: String = _

  val maxPS: Int = 100
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val enemy: Option[Enemy] = Some(Enemy("Enemy", maxPS, stats))
  val emptyEnemy: Option[Enemy] = None
  var undefinedEnemy: Option[Enemy] = _

  val pathways: Set[Pathway] = Set.empty
  var undefinedPathways: Set[Pathway] = _

  val events: List[Event] = List()
  var undefinedEvents: List[Event] = _

  val node: StoryNode = StoryNode(id, storyNodeNarrative, enemy, pathways, events)

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
    val nodeWithEmptyEnemy: StoryNode = StoryNode(id, storyNodeNarrative, emptyEnemy, pathways, events)
    nodeWithEmptyEnemy.enemy shouldEqual None
  }

  it should "have a pathway" in {
    node.pathways shouldEqual pathways
  }

  it should "have a defined narrative" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, undefinedNarrative, enemy, pathways, events)
    }
  }

  it should "not have a empty narrative" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, enemy, pathways, events)
    }
  }

  it should "not have an undefined enemy" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, storyNodeNarrative, undefinedEnemy, pathways, events)
    }
  }

  it should "have a defined set of pathways" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, enemy, undefinedPathways, events)
    }
  }

  it should "not contain multiple Pathways with same destination StoryNode" in {
    intercept[IllegalArgumentException] {
      StoryNode(
        id,
        storyNodeNarrative,
        enemy,
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
        enemy,
        Set(Pathway(pathwayDescription, node, Some(_ => true))),
        events
      )
    }
  }

  it should "have a defined list of events" in {
    intercept[IllegalArgumentException] {
      StoryNode(id, emptyNarrative, enemy, pathways, undefinedEvents)
    }
  }

  it should behave like serializationTest(node)

}
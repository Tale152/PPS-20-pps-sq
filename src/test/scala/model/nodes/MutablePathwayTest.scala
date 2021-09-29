package model.nodes

import mock.MockFactory.{CharacterFactory, ItemFactory}
import model.StoryModel
import model.items.KeyItem
import model.nodes.StoryNode.MutableStoryNode
import model.nodes.util.{ItemPrerequisite, Prerequisite}
import specs.{FlatTestSpec, SerializableSpec}

class MutablePathwayTest extends FlatTestSpec with SerializableSpec {

  val item: KeyItem = ItemFactory.mockKeyItem
  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"

  var undefinedDestinationNode: MutableStoryNode = _
  val emptyPathwayDescription: String = ""
  var undefinedPathwayDescription: String = _

  val destinationNodePrerequisite: MutableStoryNode = MutableStoryNode(2, storyNodeNarrative, None, Set.empty, List())
  val destinationNodeNoPrerequisite: MutableStoryNode = MutableStoryNode(1, storyNodeNarrative, None, Set.empty, List())
  val prerequisite: Option[Prerequisite] = Some(ItemPrerequisite(item))
  val pathwayPrerequisite: MutablePathway =
    MutablePathway(pathwayDescription, destinationNodePrerequisite, prerequisite)
  val pathwayNoPrerequisite: MutablePathway =
    MutablePathway(pathwayDescription, destinationNodeNoPrerequisite, None)
  val startingNode: MutableStoryNode =
    MutableStoryNode(0, storyNodeNarrative, None, Set(pathwayPrerequisite, pathwayNoPrerequisite), List())

  "A mutable pathway" should "have description \"pathwayDescription\"" in {
    pathwayPrerequisite.description shouldEqual pathwayDescription
  }

  it should "have a reference to the destination node" in {
    pathwayPrerequisite.destinationNode shouldEqual destinationNodePrerequisite
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      MutablePathway(emptyPathwayDescription, destinationNodePrerequisite, prerequisite)
    }
  }

  it should "have a defined description" in {
    intercept[IllegalArgumentException] {
      MutablePathway(undefinedPathwayDescription, destinationNodePrerequisite, prerequisite)
    }
  }

  it should "have a defined destination node" in {
    intercept[IllegalArgumentException] {
      MutablePathway(pathwayDescription, undefinedDestinationNode, prerequisite)
    }
  }

  "The prerequisite" can "be empty" in {
    pathwayNoPrerequisite.prerequisite shouldEqual None
  }

  it should "be true if condition is present and is met" in {
    pathwayPrerequisite.prerequisite.nonEmpty shouldEqual true
    val player = CharacterFactory.mockPlayer()
    player.inventory = List(item)
    pathwayPrerequisite
      .prerequisite
      .get(StoryModel("s", player, startingNode)) shouldEqual true
  }

  it should "be false if condition is present and isn't met" in {
    pathwayPrerequisite.prerequisite.nonEmpty shouldEqual true
    pathwayPrerequisite
      .prerequisite
      .get(StoryModel("s", CharacterFactory.mockPlayer(), startingNode)) shouldEqual false
  }

  it should behave like serializationTest(pathwayPrerequisite)

  it should behave like serializationTest(pathwayNoPrerequisite)

}

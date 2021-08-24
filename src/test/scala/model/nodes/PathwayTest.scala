package model.nodes

import model.characters.Player
import model.StoryModel
import model.characters.properties.stats.{Stat, StatName}
import specs.{FlatTestSpec, SerializableSpec}

class PathwayTest extends FlatTestSpec with SerializableSpec {

  val playerName: String = "prerequisite"
  val maxPS: Int = 100
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"

  var undefinedDestinationNode: StoryNode = _
  val emptyPathwayDescription: String = ""
  var undefinedPathwayDescription: String = _

  val destinationNodePrerequisite: StoryNode = StoryNode(2, storyNodeNarrative, Set.empty)
  val destinationNodeNoPrerequisite: StoryNode = StoryNode(1, storyNodeNarrative, Set.empty)
  val prerequisite: Option[StoryModel => Boolean] = Some(m => m.player.name == "prerequisite")
  val pathwayPrerequisite: Pathway = Pathway(pathwayDescription, destinationNodePrerequisite, prerequisite)
  val pathwayNoPrerequisite: Pathway = Pathway(pathwayDescription, destinationNodeNoPrerequisite, None)
  val startingNode: StoryNode = StoryNode(0, storyNodeNarrative, Set(pathwayPrerequisite, pathwayNoPrerequisite))

  "The pathway" should "have description \"pathwayDescription\"" in {
    pathwayPrerequisite.description shouldEqual pathwayDescription
  }

  it should "have a reference to the destination node" in {
    pathwayPrerequisite.destinationNode shouldEqual destinationNodePrerequisite
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      Pathway(emptyPathwayDescription, destinationNodePrerequisite, prerequisite)
    }
  }

  it should "have a defined description" in {
    intercept[IllegalArgumentException] {
      Pathway(undefinedPathwayDescription, destinationNodePrerequisite, prerequisite)
    }
  }

  it should "have a defined destination node" in {
    intercept[IllegalArgumentException] {
      Pathway(pathwayDescription, undefinedDestinationNode, prerequisite)
    }
  }

  "The prerequisite" can "be empty" in {
    pathwayNoPrerequisite.prerequisite shouldEqual None
  }

  it should "be true if condition is present and is met" in {
    pathwayPrerequisite.prerequisite.nonEmpty shouldEqual true
    pathwayPrerequisite.prerequisite.get(StoryModel(Player(playerName, maxPS, stats), startingNode)) shouldEqual true
  }

  it should "be false if condition is present and isn't met" in {
    pathwayPrerequisite.prerequisite.nonEmpty shouldEqual true
    pathwayPrerequisite.prerequisite.get(StoryModel(
      Player("should be false", maxPS, stats), startingNode)) shouldEqual false
  }

  it should behave like serializationTest(pathwayPrerequisite)

  it should behave like serializationTest(pathwayNoPrerequisite)

}

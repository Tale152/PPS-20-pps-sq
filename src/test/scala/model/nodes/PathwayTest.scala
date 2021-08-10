package model.nodes

import model.characters.Player
import model.StoryModel
import org.scalatest.{FlatSpec, Matchers}

class PathwayTest extends FlatSpec with Matchers {

  val playerName: String = "prerequisite"
  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"

  var undefinedDestinationNode: StoryNode = _
  val emptyPathwayDescription: String = ""
  var undefinedPathwayDescription: String = _

  val destinationNodePrerequisite: StoryNode = StoryNode(2, storyNodeNarrative, Set.empty)
  val destinationNodeNoPrerequisite: StoryNode = StoryNode(1, storyNodeNarrative, Set.empty)
  val prerequisite: Option[StoryModel => Boolean] = Some(m => m.player.name == playerName)
  val pathwayPrerequisite: Pathway = Pathway(pathwayDescription, destinationNodePrerequisite, prerequisite)
  val pathwayNoPrerequisite: Pathway = Pathway(pathwayDescription, destinationNodeNoPrerequisite, None)
  val startingNode: StoryNode = StoryNode(0, storyNodeNarrative, Set(pathwayPrerequisite, pathwayNoPrerequisite))

  "The pathway" should "have description \"pathwayDescription\"" in {
    pathwayPrerequisite.description shouldEqual pathwayDescription
  }

  "The pathway" should "have a reference to the destination node" in {
    pathwayPrerequisite.destinationNode shouldEqual destinationNodePrerequisite
  }

  "The prerequisite" can "be empty" in {
    pathwayNoPrerequisite.prerequisite shouldEqual None
  }

  "The prerequisite" should "be true if condition is present and is met" in {
    pathwayPrerequisite.prerequisite.nonEmpty shouldEqual true
    pathwayPrerequisite.prerequisite.get(StoryModel(Player(playerName), startingNode)) shouldEqual true
  }

  "The prerequisite" should "be false if condition is present and isn't met" in {
    pathwayPrerequisite.prerequisite.nonEmpty shouldEqual true
    pathwayPrerequisite.prerequisite.get(StoryModel(Player("should be false"), startingNode)) shouldEqual false
  }

  it should "have a defined description" in {
    intercept[IllegalArgumentException] {
      Pathway(undefinedPathwayDescription, destinationNodePrerequisite, prerequisite)
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      Pathway(emptyPathwayDescription, destinationNodePrerequisite, prerequisite)
    }
  }

  it should "have a defined destination node" in {
    intercept[IllegalArgumentException] {
      Pathway(pathwayDescription, undefinedDestinationNode, prerequisite)
    }
  }

}

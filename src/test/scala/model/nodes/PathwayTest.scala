package model.nodes

import model.characters.Player
import model.{StoryModel, StoryModelImpl}
import org.scalatest.{FlatSpec, Matchers}

class PathwayTest extends FlatSpec with Matchers {

  val playerName: String = "prerequisite"
  val storyNodeNarrative: String = "storyNodeNarrative"
  val pathwayDescription: String = "pathwayDescription"

  var undefinedDestinationNode: StoryNode = _
  val emptyPathwayDescription: String = ""
  var undefinedPathwayDescription: String = _

  val destinationNode: StoryNode = StoryNode(1, storyNodeNarrative, Set.empty)
  val prerequisite: StoryModel => Boolean = m => m.player.name == playerName
  val pathway: Pathway = Pathway(pathwayDescription, destinationNode, prerequisite)
  val startingNode: StoryNode = StoryNode(0, storyNodeNarrative, Set(pathway))

  "The pathway" should "have description \"pathwayDescription\"" in {
    pathway.description shouldEqual pathwayDescription
  }

  "The pathway" should "have a reference to the destination node" in {
    pathway.destinationNode shouldEqual destinationNode
  }

  "The prerequisite" should "be true if condition is met" in {
    pathway.prerequisite(StoryModelImpl(Player(playerName), startingNode)) shouldEqual true
  }

  "The prerequisite" should "be false if condition isn't met" in {
    pathway.prerequisite(StoryModelImpl(Player("should be false"), startingNode)) shouldEqual false
  }

  it should "have a defined description" in {
    intercept[IllegalArgumentException] {
      Pathway(undefinedPathwayDescription, destinationNode, prerequisite)
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      Pathway(emptyPathwayDescription, destinationNode, prerequisite)
    }
  }

  it should "have a defined destination node" in {
    intercept[IllegalArgumentException] {
      Pathway(pathwayDescription, undefinedDestinationNode, prerequisite)
    }
  }

}

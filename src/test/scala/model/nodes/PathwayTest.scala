package model.nodes

import org.scalatest.{FlatSpec, Matchers}

class PathwayTest extends FlatSpec with Matchers {

  val destinationNode: StoryNode = StoryNode(0, "narrative", Set.empty)
  var undefinedDestinationNode: StoryNode = _

  val pathwayDescription: String = "pathwayDescription"
  val emptyPathwayDescription: String = ""
  var undefinedPathwayDescription: String = _

  val pathway: Pathway = Pathway(pathwayDescription, destinationNode)

  "The pathway" should "have description pathwayDescription" in {
    pathway.description shouldEqual pathwayDescription
  }

  it should "have a reference to the destination node" in {
    pathway.destinationNode shouldEqual destinationNode
  }

  it should "have a defined description" in {
    intercept[IllegalArgumentException] {
      Pathway(undefinedPathwayDescription, destinationNode)
    }
  }

  it should "not have an empty description" in {
    intercept[IllegalArgumentException] {
      Pathway(emptyPathwayDescription, destinationNode)
    }
  }

  it should "have a defined destination node" in {
    intercept[IllegalArgumentException] {
      Pathway(pathwayDescription, undefinedDestinationNode)
    }
  }
}

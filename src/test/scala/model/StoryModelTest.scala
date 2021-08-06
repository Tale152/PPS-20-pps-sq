package model

import model.characters.Player
import model.nodes.{Pathway, StoryNode}
import org.scalatest.{FlatSpec, Matchers}

class StoryModelTest extends FlatSpec with Matchers {

  val mainPlayer: Player = Player("Jonathan")

  val storyNode: StoryNode = StoryNode(0, "narrative", Set.empty)
  val nextStoryNode: StoryNode = StoryNode(1, "nextNarrative", Set.empty)

  val storyModel: StoryModel = StoryModelImpl(mainPlayer, storyNode)

  "The story model" should "know who is the player" in {
    storyModel.player shouldEqual mainPlayer
  }

  it should "know which is the current story node" in {
    storyModel.currentStoryNode shouldEqual storyNode
  }

  it should "update the current story node" in {
    storyModel.currentStoryNode = nextStoryNode
    storyModel.currentStoryNode shouldEqual nextStoryNode
  }

  it should "not contain nodes with same ID" in {
    val endNodeA: StoryNode = StoryNode(0, "narrative", Set.empty)
    val pathwayMidToEndA: Pathway = Pathway("description", endNodeA)
    val endNodeB: StoryNode = StoryNode(2, "narrative", Set.empty)
    val pathwayMidToEndB: Pathway = Pathway("description", endNodeB)
    val midNode: StoryNode = StoryNode(1, "narrative", Set(pathwayMidToEndA, pathwayMidToEndB))
    val pathwayStartToMid: Pathway = Pathway("description", midNode)
    val startingNode: StoryNode = StoryNode(0, "narrative", Set(pathwayStartToMid))
    intercept[IllegalArgumentException] {
      StoryModelImpl(mainPlayer, startingNode)
    }
  }

}

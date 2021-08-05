package model

import model.characters.Player
import model.nodes.StoryNode
import org.scalatest.{FlatSpec, Matchers}

class StoryModelTest extends FlatSpec with Matchers {

  val mainPlayer: Player = Player("Jonathan")
  val storyNode: StoryNode = StoryNode(0, "narrative", Set.empty)
  val storyNode1: StoryNode = StoryNode(1, "narrative1", Set.empty)
  val storyModel: StoryModel = StoryModelImpl(mainPlayer, storyNode)

  "The story model" should "know who is the player" in {
    storyModel.player shouldEqual mainPlayer
  }

  it should "know which is the current story node" in {
    storyModel.currentStoryNode shouldEqual storyNode
  }

  it should "update the current story node" in {
    storyModel.currentStoryNode = storyNode1
    storyModel.currentStoryNode shouldEqual storyNode1
  }
}

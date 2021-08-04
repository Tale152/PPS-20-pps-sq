package model

import model.characters.Player
import org.scalatest.{FlatSpec, Matchers}

class StoryModelTest extends FlatSpec with Matchers {

  val mainPlayer: Player = Player("Jonathan")
  val storyModel: StoryModel = StoryModel(mainPlayer)

  "The story model" should "know who is the player" in {
    storyModel.player shouldEqual mainPlayer
  }
}

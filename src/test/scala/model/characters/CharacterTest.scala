package model.characters

import org.scalatest.{FlatSpec, Matchers}

class CharacterTest extends FlatSpec with Matchers {

  val mainPlayer: Player = Player("Jonathan")

  "The player" should "have name Jonathan" in {
    mainPlayer.name shouldEqual "Jonathan"
  }

  it should "throw IllegalArgumentException if the player name is left empty" in {
    intercept[IllegalArgumentException] {
      Player("")
    }
  }

}

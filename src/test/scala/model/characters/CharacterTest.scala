package model.characters

import model.characters.properties.stats.{Stat, StatName}
import org.scalatest.{FlatSpec, Matchers}

class CharacterTest extends FlatSpec with Matchers {

  val maxPS: Int = 100
  val wrongMaxPS: Int = 100
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val mainPlayer: Player = Player("Jonathan", maxPS, stats)

  "The player" should "have name Jonathan" in {
    mainPlayer.name shouldEqual "Jonathan"
  }

  it should "throw IllegalArgumentException if the player name is left empty" in {
    intercept[IllegalArgumentException] {
      Player("", maxPS, stats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is zero" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", 0, stats)
    }
  }

  it should "throw IllegalArgumentException if stats are empty" in {
    intercept[IllegalArgumentException] {
      Player("Joseph",maxPS, Set())
    }
  }

}

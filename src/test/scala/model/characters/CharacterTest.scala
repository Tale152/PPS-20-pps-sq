package model.characters

import model.characters.properties.stats.{Stat, StatName}
import specs.FlatTestSpec

class CharacterTest extends FlatTestSpec {

  val maxPS: Int = 100
  val wrongMaxPS: Int = -3
  val stats: Set[Stat] = Set(Stat(1, StatName.Speed))
  val mainPlayer: Player = Player("Jonathan", maxPS, stats)
  val easyEnemy: Enemy = Enemy("Yoshikage Kira", maxPS, stats)

  "The player" should "have name Jonathan" in {
    mainPlayer.name shouldEqual "Jonathan"
  }

  "The easy enemy" should "have name Yoshikage Kira" in {
    easyEnemy.name shouldEqual "Yoshikage Kira"
  }

  it should "throw IllegalArgumentException if the player name is left empty" in {
    intercept[IllegalArgumentException] {
      Player("", maxPS, stats)
    }
  }


  it should "throw IllegalArgumentException if the enemy name is left empty" in {
    intercept[IllegalArgumentException] {
      Enemy("", maxPS, stats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is zero" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", 0, stats)
    }
  }

  it should "throw IllegalArgumentException if maxPS is negative" in {
    intercept[IllegalArgumentException] {
      Player("Jolyne", wrongMaxPS, stats)
    }
  }

  it should "throw IllegalArgumentException if stats are empty" in {
    intercept[IllegalArgumentException] {
      Player("Joseph", maxPS, Set())
    }
  }

}

package model.progress

import model.characters.Player
import model.characters.properties.stats.{Stat, StatName}
import specs.{FlatTestSpec, SerializableSpec}

class ProgressTest extends FlatTestSpec with SerializableSpec{

  val serializableHistory: SerializableHistory = SerializableHistory(List(0,1,2))
  val player: Player = Player("player", 1, Set(Stat(1, StatName.Wisdom)))
  val progress: Progress = Progress(serializableHistory, player)

  "The Progress" should "contain the player" in {
    progress.player shouldEqual player
  }

  "The progress" should "contain a SerializableHistory" in {
    progress.serializableHistory shouldEqual serializableHistory
  }

  "The progress" should "not contain a null player" in {
    intercept[IllegalArgumentException]{
      Progress(serializableHistory, null)
    }
  }

  "The progress" should "not contain a null SerializableHistory" in {
    intercept[IllegalArgumentException]{
      Progress(null, player)
    }
  }

  it should behave like serializationTest(progress)


}

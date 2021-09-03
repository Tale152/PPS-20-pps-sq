package model.progress

import mock.MockFactory
import model.characters.Player
import specs.{FlatTestSpec, SerializableSpec}

class ProgressTest extends FlatTestSpec with SerializableSpec{

  val serializableHistory: SerializableHistory = SerializableHistory(List(0,1,2))
  var undefinedSerializableHistory: SerializableHistory = _
  val player: Player = Player("player", 1, MockFactory.mockSetOfStats())
  var undefinedPlayer: Player = _
  val progress: Progress = Progress(serializableHistory, player)

  "The Progress" should "contain the player" in {
    progress.player shouldEqual player
  }

  "The progress" should "contain a SerializableHistory" in {
    progress.serializableHistory shouldEqual serializableHistory
  }

  "The progress" should "not contain a null player" in {
    intercept[IllegalArgumentException]{
      Progress(serializableHistory, undefinedPlayer)
    }
  }

  "The progress" should "not contain a null SerializableHistory" in {
    intercept[IllegalArgumentException]{
      Progress(undefinedSerializableHistory, player)
    }
  }

  it should behave like serializationTest(progress)


}

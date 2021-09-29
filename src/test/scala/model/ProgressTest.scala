package model

import mock.MockFactory.CharacterFactory
import model.characters.Player
import specs.{FlatTestSpec, SerializableSpec}

class ProgressTest extends FlatTestSpec with SerializableSpec{

  val serializableHistory: List[Int] = List(0,1,2)
  var undefinedSerializableHistory: List[Int] = _
  val player: Player = CharacterFactory.mockPlayer()
  var undefinedPlayer: Player = _
  val progress: Progress = Progress(serializableHistory, player)

  "The Progress" should "contain the player" in {
    progress.player shouldEqual player
  }

  it should "contain a SerializableHistory" in {
    progress.serializableHistory shouldEqual serializableHistory
  }

  it should "not contain a null player" in {
    intercept[IllegalArgumentException]{
      Progress(serializableHistory, undefinedPlayer)
    }
  }

  it should "not contain a null SerializableHistory" in {
    intercept[IllegalArgumentException]{
      Progress(undefinedSerializableHistory, player)
    }
  }

  it should "not contain an empty visited nodes id list" in {
    intercept[IllegalArgumentException]{
      Progress(List[Int](), player)
    }
  }

  it should "not contain a visited nodes id list with duplicate ids" in {
    intercept[IllegalArgumentException]{
      Progress(List[Int](0,1,0,2), player)
    }
  }

  it should behave like serializationTest(progress)

}

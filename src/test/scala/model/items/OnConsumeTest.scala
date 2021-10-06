package model.items

import mock.MockFactory.CharacterFactory
import model.characters.Player
import specs.{FlatTestSpec, SerializableSpec}

class OnConsumeTest extends FlatTestSpec with SerializableSpec {

  val playerPS: Int = 100
  val player: Player = Player("jojo", playerPS, CharacterFactory.mockSetOfStats())

  "The DecrementHealthOnConsume" should "work properly" in {
    val decrementValue: Int = 50
    val decrementHealthOnConsume = DecrementHealthOnConsume(decrementValue)
    decrementHealthOnConsume(player)
    player.properties.health.currentPS shouldEqual 50
  }

  "The IncrementHealthOnConsume" should "work properly" in {
    val incrementValue: Int = 10
    val incrementHealthOnConsume = IncrementHealthOnConsume(incrementValue)
    incrementHealthOnConsume(player)
    player.properties.health.currentPS shouldEqual 60
  }

}

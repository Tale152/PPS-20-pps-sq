package model.characters.properties

import mock.MockFactory.CharacterFactory.maxPs
import mock.MockFactory.CharacterFactory
import model.characters.properties.stats.StatName
import model.characters.properties.stats.{Stat, StatModifier}
import specs.{FlatTestSpec, SerializableSpec}

class PropertiesContainerTest extends FlatTestSpec with SerializableSpec {

  val stats: Set[Stat] = CharacterFactory.mockSetOfStats()
  val propContainer: PropertiesContainer = PropertiesContainer(maxPs, stats)

  val modifierStrategy: Int => Int = value => value * 2
  val statMods: Set[StatModifier] = Set(
    StatModifier(StatName.Intelligence, modifierStrategy),
    StatModifier(StatName.Intelligence, modifierStrategy),
    StatModifier(StatName.Wisdom, modifierStrategy),
    StatModifier(StatName.Dexterity, modifierStrategy)
  )

  "The Properties Container" should "initialize maxPS to 100" in {
    propContainer.health shouldEqual Health(maxPs)
  }

  it should "return the correct set of stats on creation" in {
    propContainer.stats shouldEqual stats
  }

  it should "return the stat with the specific StatName" in {
    propContainer.stat(StatName.Intelligence) shouldEqual Stat(1, StatName.Intelligence)
  }

  it should "return an empty set calling StatModifiers on creation" in {
    propContainer.statModifiers shouldEqual Set()
  }

  it should "be able to add a statModifier" in {
    propContainer.statModifiers ++= statMods
    propContainer.statModifiers shouldEqual statMods
  }

  it should "return every modifier for the specific statName" in {
    propContainer.statModifiers(StatName.Intelligence) shouldEqual Set(
      StatModifier(StatName.Intelligence, modifierStrategy), StatModifier(StatName.Intelligence, modifierStrategy))
  }

  it should "return the actual value of a stat with its modifiers applied" in {
    val calculatedValue: Int = 2
    propContainer.modifiedStat(StatName.Wisdom) shouldEqual Stat(calculatedValue, StatName.Wisdom)
  }

  it should behave like serializationTest(propContainer)

}

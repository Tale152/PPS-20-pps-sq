package model.characters.properties

import mock.MockFactory.CharacterFactory.maxPs
import mock.MockFactory.{CharacterFactory, StatFactory, StatModifierFactory}
import model.characters.properties.stats.StatName
import model.characters.properties.stats.{Stat, StatModifier}
import specs.{FlatTestSpec, SerializableSpec}

class PropertiesContainerTest extends FlatTestSpec with SerializableSpec {

  val stats: Set[Stat] = CharacterFactory.mockSetOfStats()
  val propContainer: PropertiesContainer = PropertiesContainer(maxPs, stats)

  val statMods: Set[StatModifier] = StatModifierFactory.statModifiers(
    StatName.Intelligence, StatName.Intelligence, StatName.Wisdom, StatName.Dexterity)

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
      StatModifierFactory.statModifier(StatName.Intelligence), StatModifierFactory.statModifier(StatName.Intelligence))
  }

  it should "return the actual value of a stat with its modifiers applied" in {
    val calculatedValue: Int = 2
    propContainer.modifiedStat(StatName.Wisdom) shouldEqual StatFactory.wisdomStat(calculatedValue)
  }

  it should behave like serializationTest(propContainer)

}

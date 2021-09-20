package model.characters.properties

import model.characters.properties.stats.StatName
import model.characters.properties.stats.{Stat, StatModifier}
import specs.{FlatTestSpec, SerializableSpec}

class PropertiesContainerTest extends FlatTestSpec with SerializableSpec {

  val maximumPS = 100
  val stats: Set[Stat] = Set(
    Stat(1, StatName.Intelligence),
    Stat(1, StatName.Wisdom),
    Stat(0, StatName.Strength),
    Stat(0, StatName.Charisma),
    Stat(1, StatName.Dexterity))
  val propContainer: PropertiesContainer = PropertiesContainer(maximumPS, stats)
  val modifierStrategy: Int => Int = value => value * 2
  val statMods: Set[StatModifier] = Set(
    StatModifier(StatName.Intelligence, modifierStrategy),
    StatModifier(StatName.Intelligence, modifierStrategy),
    StatModifier(StatName.Wisdom, modifierStrategy),
    StatModifier(StatName.Dexterity, modifierStrategy)
  )

  "The Properties Container" should "initialize maxPS to 100" in {
    propContainer.health shouldEqual Health(maximumPS)
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
    propContainer.statModifiers += StatModifier(StatName.Intelligence, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Intelligence, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Intelligence, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Wisdom, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Dexterity, modifierStrategy)

    propContainer.statModifiers shouldEqual statMods

  }

  it should "return every modifier for the specific statName" in {
    propContainer.statModifiers += StatModifier(StatName.Intelligence, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Intelligence, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Wisdom, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Dexterity, modifierStrategy)

    propContainer.statModifiers(StatName.Intelligence) shouldEqual Set(
      StatModifier(StatName.Intelligence, modifierStrategy), StatModifier(StatName.Intelligence, modifierStrategy))
  }

  it should behave like serializationTest(propContainer)

}

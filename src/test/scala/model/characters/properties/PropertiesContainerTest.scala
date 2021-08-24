package model.characters.properties

import model.characters.properties.stats.{Stat, StatModifier, StatName}
import specs.{FlatTestSpec, SerializableSpec}

class PropertiesContainerTest extends FlatTestSpec with SerializableSpec {

  val maximumPS = 100
  val stats: Set[Stat] = Set(
    Stat(1, StatName.Speed),
    Stat(1, StatName.Wisdom),
    Stat(0, StatName.Strength),
    Stat(0, StatName.Charisma),
    Stat(1, StatName.Dexterity))
  val propContainer: PropertiesContainer = PropertiesContainer(maximumPS, stats)
  val modifierStrategy: Int => Int = value => value * 2
  val statMods: Set[StatModifier] = Set(
    StatModifier(StatName.Speed, modifierStrategy),
    StatModifier(StatName.Speed, modifierStrategy),
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
    propContainer.stat(StatName.Speed) shouldEqual Stat(1, StatName.Speed)
  }

  it should "return an empty set calling StatModifiers on creation" in {
    propContainer.statModifiers shouldEqual Set()
  }

  it should "be able to add a statModifier" in {
    propContainer.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Wisdom, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Dexterity, modifierStrategy)

    propContainer.statModifiers shouldEqual statMods

  }

  it should "return every modifier for the specific statName" in {
    propContainer.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Wisdom, modifierStrategy)
    propContainer.statModifiers += StatModifier(StatName.Dexterity, modifierStrategy)

    propContainer.statModifiers(StatName.Speed) shouldEqual Set(
      StatModifier(StatName.Speed, modifierStrategy), StatModifier(StatName.Speed, modifierStrategy))
  }

  it should behave like serializationTest(propContainer)

}

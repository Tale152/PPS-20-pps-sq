package model.characters.properties

import model.characters.properties.stats.{Stat, StatModifier, StatName}
import specs.FlatTestSpec

class PropertiesContainerTest extends FlatTestSpec {

  val maximumPS = 100
  val stats: Set[Stat] = Set(
    Stat(1, StatName.Speed),
    Stat(1, StatName.Wisdom),
    Stat(0, StatName.Strength),
    Stat(0, StatName.Charisma),
    Stat(1, StatName.Dexterity))
  val prop: PropertiesContainer = PropertiesContainer(maximumPS, stats)
  val modifierStrategy: Int => Int = value => value * 2
  val statMods: Set[StatModifier] = Set(
    StatModifier(StatName.Speed, modifierStrategy),
    StatModifier(StatName.Speed, modifierStrategy),
    StatModifier(StatName.Wisdom, modifierStrategy),
    StatModifier(StatName.Dexterity, modifierStrategy)
  )

  "Initial maxPS" should "be equal to 100" in {
    prop.health shouldEqual Health(maximumPS)
  }

  "Initial Stats" should "return the correct set of stats" in {
    prop.stats shouldEqual stats
  }

  "Stat call" should "return the stat with the specific StatName" in {
    prop.stat(StatName.Speed) shouldEqual Stat(1, StatName.Speed)
  }

  "StatModifiers" should "return an empty set" in {
    prop.statModifiers shouldEqual Set()
  }

  "PropertiesContainer" should "be able to add a statModifier" in {
    prop.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Wisdom, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Dexterity, modifierStrategy)

    prop.statModifiers shouldEqual statMods

  }

  "StatModifier with parameter statName" should "return every modifier for the specific statName" in {
    prop.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Speed, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Wisdom, modifierStrategy)
    prop.statModifiers += StatModifier(StatName.Dexterity, modifierStrategy)

    prop.statModifiers(StatName.Speed) shouldEqual Set(
      StatModifier(StatName.Speed, modifierStrategy), StatModifier(StatName.Speed, modifierStrategy))
  }

}

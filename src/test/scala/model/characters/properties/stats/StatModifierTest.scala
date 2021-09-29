package model.characters.properties.stats

import mock.MockFactory.{StatFactory, StatModifierFactory}
import specs.{FlatTestSpec, SerializableSpec}

class StatModifierTest extends FlatTestSpec with SerializableSpec {

  val wisdomStat: Stat = StatFactory.wisdomStat()
  val wisdomStatModifier: StatModifier = StatModifierFactory.statModifier(StatName.Wisdom)

  "The stat modifier" should "have a stat name it is referred to" in {
    wisdomStatModifier.statName shouldEqual StatName.Wisdom
  }

  it should "change correctly the value of the stat it is referred to" in {
    wisdomStatModifier.onApply(wisdomStat.value) shouldEqual 20
  }

  it should "not match other stat name" in {
    wisdomStatModifier.statName should not equal StatName.Constitution
  }

  "The stat name" should "not be undefined" in {
    intercept[IllegalArgumentException] {
      StatModifier(StatFactory.undefinedStatName, StatModifierFactory.modifierStrategy)
    }
  }

  "The modifier strategy" should "not be undefined" in {
    intercept[IllegalArgumentException]{
      StatModifier(StatName.Dexterity, StatModifierFactory.undefinedModifierStrategy)
    }
  }

  val dexterityStat: Stat = StatFactory.dexterityStat()
  val dexterityStatModifier: StatModifier = StatModifierFactory.statModifier(StatName.Dexterity)

  "The stat modifier equals" should "work properly passing equal stat modifier" in {
    val rightDexterityStatModifier: StatModifier = StatModifierFactory.statModifier(StatName.Dexterity)
    dexterityStatModifier == rightDexterityStatModifier shouldBe true
    dexterityStatModifier.hashCode() shouldEqual rightDexterityStatModifier.hashCode()
  }

  it should "fail passing different stat modifier" in {
    val wrongDexterityStatModifier: StatModifier = StatModifierFactory.statModifier(StatName.Wisdom)
    dexterityStatModifier == wrongDexterityStatModifier shouldBe false
    dexterityStatModifier.hashCode() should not equal wrongDexterityStatModifier.hashCode()
  }

  it should "fail passing different object" in {
    dexterityStatModifier should not equal "otherObject"
  }

  it should behave like serializationTest(wisdomStatModifier)

}

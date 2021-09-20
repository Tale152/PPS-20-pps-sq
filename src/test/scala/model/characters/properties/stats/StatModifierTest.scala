package model.characters.properties.stats

import model.characters.properties.stats.StatName.StatName
import specs.{FlatTestSpec, SerializableSpec}

class StatModifierTest extends FlatTestSpec with SerializableSpec {

  val wisdomValue: Int = 10

  var undefinedStatName: StatName = _
  var wisdomStatName: StatName = StatName.Wisdom

  var undefinedModifierStrategy: Int => Int = _
  val modifierStrategy: Int => Int = value => value * 2

  val wisdomStat: Stat = Stat(wisdomValue, wisdomStatName)
  val wisdomStatModifier: StatModifier = StatModifier(wisdomStatName, modifierStrategy)

  "The stat modifier" should "have a stat name it is referred to" in {
    wisdomStatModifier.statName shouldEqual wisdomStatName
  }

  it should "change correctly the value of the stat it is referred to" in {
    wisdomStatModifier.modifyStrategy(wisdomStat.value) shouldEqual 20
  }

  it should behave like serializationTest(wisdomStatModifier)

  it should "not match other stat name" in {
    wisdomStatModifier.statName should not equal StatName.Constitution
  }

  "The stat name" should "not be undefined" in {
    intercept[IllegalArgumentException] {
      StatModifier(undefinedStatName, modifierStrategy)
    }
  }

  "The modifier strategy" should "not be undefined" in {
    intercept[IllegalArgumentException]{
      StatModifier(wisdomStatName, undefinedModifierStrategy)
    }
  }

  val dexterityValue: Int = 10
  var dexterityStatName: StatName = StatName.Dexterity
  val dexterityModifierStrategy: Int => Int = value => value * 2
  val dexterityStat: Stat = Stat(wisdomValue, dexterityStatName)

  val dexterityStatModifier: StatModifier = StatModifier(dexterityStatName, dexterityModifierStrategy)

  "The stat modifier equals" should "work properly passing equal stat modifier" in {
    val rightDexterityStatModifier: StatModifier = StatModifier(dexterityStatName, dexterityModifierStrategy)
    dexterityStatModifier == rightDexterityStatModifier shouldBe true
    dexterityStatModifier.hashCode() shouldEqual rightDexterityStatModifier.hashCode()
  }

  it should "fail passing different stat modifier" in {
    val wrongDexterityStatModifier: StatModifier = StatModifier(wisdomStatName, modifierStrategy)
    dexterityStatModifier == wrongDexterityStatModifier shouldBe false
    dexterityStatModifier.hashCode() should not equal wrongDexterityStatModifier.hashCode()
  }

  it should "fail passing different object" in {
    dexterityStatModifier should not equal "otherObject"
  }
}

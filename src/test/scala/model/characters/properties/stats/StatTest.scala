package model.characters.properties.stats

import model.characters.properties.stats.StatName.StatName
import specs.{FlatTestSpec, SerializableSpec}

class StatTest extends FlatTestSpec with SerializableSpec {

  val defaultStrengthValue: Int = 10
  val incorrectStrengthValue: Int = 5

  var undefinedStatName: StatName = _

  val strengthStat: Stat = Stat(defaultStrengthValue, StatName.Strength)

  "The stat" should "have a value" in {
    strengthStat.value() shouldEqual defaultStrengthValue
  }

  it should "have a stat name it is referred to" in {
    strengthStat.statName() shouldEqual StatName.Strength
  }

  it should "not match other stat name" in {
    strengthStat.statName() should not equal StatName.Wisdom
  }

  it should "not have undefined name" in {
    intercept[IllegalArgumentException] {
      Stat(defaultStrengthValue, undefinedStatName)
    }
  }

  it should behave like serializationTest(strengthStat)

  val statEquals: Stat = Stat(defaultStrengthValue, StatName.Strength)

  "Stat equals" should "work properly passing equal stat" in {
    val statRight: Stat = Stat(defaultStrengthValue, StatName.Strength)
    statEquals == statRight shouldBe true
    statEquals.hashCode() shouldEqual statRight.hashCode()
  }

  it should "fail passing different stat" in {
    val statWrong: Stat = Stat(incorrectStrengthValue, StatName.Strength)
    statEquals == statWrong shouldBe false
    statEquals.hashCode() should not equal statWrong.hashCode()
  }

  it should "fail passing different object" in {
    statEquals should not equal "otherObject"
  }
}

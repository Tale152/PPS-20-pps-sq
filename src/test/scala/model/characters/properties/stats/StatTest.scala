package model.characters.properties.stats

import mock.MockFactory.StatFactory
import specs.{FlatTestSpec, SerializableSpec}

class StatTest extends FlatTestSpec with SerializableSpec {

  val strengthStat: Stat = StatFactory.strengthStat()

  "The stat" should "have a value" in {
    strengthStat.value shouldEqual StatFactory.defaultStrengthValue
  }

  it should "have a stat name it is referred to" in {
    strengthStat.statName shouldEqual StatName.Strength
  }

  it should "not match other stat name" in {
    strengthStat.statName should not equal StatName.Wisdom
  }

  it should "not have undefined name" in {
    intercept[IllegalArgumentException] {
      Stat(StatFactory.defaultStrengthValue, StatFactory.undefinedStatName)
    }
  }

  it should "work properly passing equal stat" in {
    val statRight: Stat = StatFactory.strengthStat()
    strengthStat == statRight shouldBe true
    strengthStat.hashCode() shouldEqual statRight.hashCode()
  }

  it should "fail passing different stat" in {
    val incorrectStatValue: Int = 5
    val statWrong: Stat = StatFactory.strengthStat(incorrectStatValue)
    strengthStat == statWrong shouldBe false
    strengthStat.hashCode() should not equal statWrong.hashCode()
  }

  it should "fail passing different object" in {
    strengthStat should not equal "otherObject"
  }

  it should behave like serializationTest(strengthStat)

}

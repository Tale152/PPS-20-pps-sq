package model.characters.properties.stats

import model.characters.properties.stats.StatName.StatName
import specs.FlatTestSpec

class StatTest extends FlatTestSpec {

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

  "The stat name" should "not be undefined" in {
    intercept[IllegalArgumentException] {
      Stat(defaultStrengthValue, undefinedStatName)
    }
  }

  val statEquals: Stat = Stat(defaultStrengthValue, StatName.Strength)

  "Stat equals" should "work properly passing equal stat" in {
    val statRight: Stat = Stat(defaultStrengthValue, StatName.Strength)
    statEquals == statRight shouldBe true
    statEquals.hashCode() shouldEqual statRight.hashCode()
  }

  "Stat equals" should "fail passing different sta" in {
    val statWrong: Stat = Stat(incorrectStrengthValue, StatName.Strength)
    statEquals == statWrong shouldBe false
    statEquals.hashCode() should not equal statWrong.hashCode()
  }

  "Stat equals" should "fail passing different object" in {
    statEquals should not equal "otherObject"
  }
}

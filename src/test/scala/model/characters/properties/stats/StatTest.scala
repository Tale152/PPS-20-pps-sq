package model.characters.properties.stats

import model.characters.properties.stats.StatName.StatName
import org.scalatest.{FlatSpec, Matchers}

class StatTest extends FlatSpec with Matchers{

  val strengthValue: Int = 10

  var undefinedStatName: StatName = _

  val strengthStat: Stat = Stat(strengthValue, StatName.Strength)

  "The stat" should "have a value" in {
    strengthStat.value() shouldEqual strengthValue
  }

  it should "have a stat name it is referred to" in {
    strengthStat.statName() shouldEqual StatName.Strength
  }

  it should "not match other stat name" in {
    strengthStat.statName() should not equal StatName.Wisdom
  }

  "The stat name" should "not be undefined" in {
    intercept[IllegalArgumentException] {
      Stat(strengthValue, undefinedStatName)
    }
  }
}

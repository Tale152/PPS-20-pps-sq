package model.characters.properties.stats

import model.characters.properties.stats.StatName.StatName
import org.scalatest.{FlatSpec, Matchers}

class StatModifierTest extends FlatSpec with Matchers {

  val defenceValue: Int = 10

  var undefinedStatName: StatName = _
  var defenceStatName: StatName = StatName.Defence

  var undefinedModifierStrategy: Int => Int = _
  val modifierStrategy: Int => Int = value => value * 2

  val defenceStat: Stat = Stat(defenceValue, defenceStatName)
  val defenceStatModifier: StatModifier = StatModifier(defenceStatName, modifierStrategy)

  "The stat modifier" should "have a stat name it is referred to" in {
    defenceStatModifier.statName() shouldEqual defenceStatName
  }

  it should "not match other stat name" in {
    defenceStatModifier.statName() should not equal StatName.Constitution
  }

  "The stat name" should "not be undefined" in {
    intercept[IllegalArgumentException] {
      StatModifier(undefinedStatName, modifierStrategy)
    }
  }

  "The modifier strategy" should "not be undefined" in {
    intercept[IllegalArgumentException]{
      StatModifier(defenceStatName, undefinedModifierStrategy)
    }
  }

  "The stat modifier" should "change correctly the value of the stat it is referred to" in {
    defenceStatModifier.modifyStrategy(defenceStat.value()) shouldEqual 20
  }
}

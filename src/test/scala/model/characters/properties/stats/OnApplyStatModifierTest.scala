package model.characters.properties.stats

import specs.{FlatTestSpec, SerializableSpec}

class OnApplyStatModifierTest extends FlatTestSpec with SerializableSpec {

  val initialValue = 100

  "The DecrementOnApplyStatModifier" should "work properly" in {
    val decrementValue: Int = 60
    val decrementOnApplyStatModifier = DecrementOnApplyStatModifier(decrementValue)
    decrementOnApplyStatModifier(initialValue) shouldEqual 40
  }

  "The IncrementOnApplyStatModifier" should "work properly" in {
    val incrementValue: Int = 20
    val incrementOnApplyStatModifier = IncrementOnApplyStatModifier(incrementValue)
    incrementOnApplyStatModifier(initialValue) shouldEqual 120
  }

}

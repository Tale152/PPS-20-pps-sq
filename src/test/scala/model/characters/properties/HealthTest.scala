package model.characters.properties

import specs.{FlatTestSpec, SerializableSpec}
import mock.MockFactory.CharacterFactory.{maxPs, negativeMaxPs}

class HealthTest extends FlatTestSpec with SerializableSpec {

  val health: Health = Health(maxPs)

  "The Health" should "have maximum PS" in {
    health.maxPS shouldEqual maxPs
  }

  "The current PS" should "be equal to maxPs at the beginning" in {
    health.currentPS shouldEqual health.maxPS
  }

  it should "take maxPs value if set higher than max PS" in {
    val higherPs: Int = 110
    health.currentPS = higherPs
    health.currentPS shouldEqual health.maxPS
  }

  it should "take value 0 if set lower than 0" in {
    health.currentPS = negativeMaxPs
    health.currentPS shouldEqual 0
  }

  it should "update value" in {
    val correctCurrentPS = 50
    health.currentPS = correctCurrentPS
    health.currentPS shouldEqual correctCurrentPS
  }

  it should "throw IllegalArgumentException if max PS value is zero" in {
    intercept[IllegalArgumentException] {
      Health(0)
    }
  }

  val healthEquals: Health = Health(maxPs)

  "Health equals" should "work properly passing equal health" in {
    val healthRight: Health = Health(maxPs)
    healthEquals == healthRight shouldBe true
    healthEquals.hashCode() shouldEqual healthRight.hashCode()
  }


  it should "fail passing different health" in {
    val incorrectCurrentPS: Int = 30
    val healthWrong: Health = Health(incorrectCurrentPS)
    healthEquals == healthWrong shouldBe false
    healthEquals.hashCode() should not equal healthWrong.hashCode()
  }

  it should "work properly if the current PS are the same" in {
    val healthRight: Health = Health(maxPs)
    healthRight.currentPS = 1
    healthEquals.currentPS = 1
    healthEquals == healthRight shouldBe true
    healthEquals.hashCode() shouldEqual healthRight.hashCode()
  }

  it should "fail passing different object" in {
    healthEquals should not equal "jojo"
  }

  it should behave like serializationTest(health)

}

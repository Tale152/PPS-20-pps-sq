package model.characters.properties

import model.characters.properties.Health.Health
import specs.{FlatTestSpec, SerializableSpec}

class HealthTest extends FlatTestSpec with SerializableSpec {

  val defaultPlayerMaxPS = 100
  val incorrectCurrentPS = 110
  val incorrectNegativeCurrentPs: Int = -10
  val correctCurrentPS = 50

  val health: Health = Health(defaultPlayerMaxPS)

  "The Health" should "have maximum PS" in {
    health.maxPS shouldEqual defaultPlayerMaxPS
  }

  it should behave like serializationTest(health)

  "The current PS" should "be equal to max PS at the beginning" in {
    health.currentPS shouldEqual health.maxPS
  }

  it should "take maxPS value if set higher than max PS" in {
    health.currentPS = incorrectCurrentPS
    health.currentPS shouldEqual health.maxPS
  }

  it should "take value 0 if set lower than 0" in {
    health.currentPS = incorrectNegativeCurrentPs
    health.currentPS shouldEqual 0
  }

  it should "update value" in {
    health.currentPS = correctCurrentPS
    health.currentPS shouldEqual correctCurrentPS
  }

  it should "throw IllegalArgumentException if max PS value is zero" in {
    intercept[IllegalArgumentException] {
      Health(0)
    }
  }

  val healthEquals: Health = Health(defaultPlayerMaxPS)

  "Health equals" should "work properly passing equal health" in {
    val healthRight: Health = Health(defaultPlayerMaxPS)
    healthEquals == healthRight shouldBe true
    healthEquals.hashCode() shouldEqual healthRight.hashCode()
  }

  it should "fail passing different health" in {
    val healthWrong: Health = Health(incorrectCurrentPS)
    healthEquals == healthWrong shouldBe false
    healthEquals.hashCode() should not equal healthWrong.hashCode()
  }

  it should "fail passing different object" in {
    healthEquals should not equal "jojo"
  }

}

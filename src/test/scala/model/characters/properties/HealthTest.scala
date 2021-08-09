package model.characters.properties

import model.characters.properties.Health.Health
import org.scalatest.{FlatSpec, Matchers}

class HealthTest extends FlatSpec with Matchers {

  val defaultPlayerMaxPS = 100
  val incorrectCurrentPS = 110

  val health: Health = Health(defaultPlayerMaxPS)

  "The Health" should "have maximum PS" in {
    health.maxPS shouldEqual defaultPlayerMaxPS
  }

  "The current PS at the beginning" should "be equal to max PS" in {
    health.currentPS shouldEqual health.maxPS
  }

  "current PS" should "take maxPS value if set higher than max PS" in {
    health.currentPS = incorrectCurrentPS
    health.currentPS shouldEqual health.maxPS
  }

  it should "throw IllegalArgumentException if max PS value is zero" in {
    intercept[IllegalArgumentException] {
      Health(0)
    }
  }

}

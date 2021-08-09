package model.characters.properties

import model.characters.properties.StatName.StatName

trait StatDescriptor {
  def value(): Int
  def statName(): StatName
}

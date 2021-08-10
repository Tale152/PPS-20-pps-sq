package model.characters.properties

import model.characters.properties.StatName.StatName

sealed trait StatDescriptor {
  def value(): Int
  def statName(): StatName
}

case class Stat(override val value: Int, override val statName: StatName) extends StatDescriptor

case class StatModifier(override val value: Int,
                        override val statName: StatName,
                        modifierStrategy: Int => Int) extends StatDescriptor
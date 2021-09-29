package model.characters.properties.stats

sealed trait StatModifierStrategy extends (Int => Int)

case class IncrementStatModifierStrategy(value: Int) extends StatModifierStrategy{
  override def apply(v: Int): Int = v + value
}

case class DecrementStatModifierStrategy(value: Int) extends StatModifierStrategy{
  override def apply(v: Int): Int = v - value
}

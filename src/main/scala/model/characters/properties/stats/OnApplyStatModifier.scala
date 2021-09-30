package model.characters.properties.stats

sealed trait OnApplyStatModifier extends (Int => Int)

case class IncrementOnApplyStatModifier(value: Int) extends OnApplyStatModifier{
  override def apply(v: Int): Int = v + value
}

case class DecrementOnApplyStatModifier(value: Int) extends OnApplyStatModifier{
  override def apply(v: Int): Int = v - value
}

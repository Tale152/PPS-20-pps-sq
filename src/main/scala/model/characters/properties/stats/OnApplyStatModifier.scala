package model.characters.properties.stats

/**
 * Trait that represents the strategy to apply on a [[model.characters.properties.stats.StatModifier]].
 */
sealed trait OnApplyStatModifier extends (Int => Int)

/**
 * Increment the Stat by some value.
 *
 * @param value the value to add to the current Stat value.
 */
case class IncrementOnApplyStatModifier(private val value: Int) extends OnApplyStatModifier {
  override def apply(v: Int): Int = v + value
}

/**
 * Decrement the Stat by some value.
 *
 * @param value the value to subtract to the current Stat value.
 */
case class DecrementOnApplyStatModifier(private val value: Int) extends OnApplyStatModifier {
  override def apply(v: Int): Int = v - value
}

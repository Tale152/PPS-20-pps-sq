package model.characters.properties

/**
 * Trait that represents the Health of a Character.
 */
sealed trait Health extends Serializable {

  /**
   * The character's maximum health value.
   */
  val maxPS: Int

  /**
   * @return The character's current health value, ranging from 0 to maxPs (inclusive)
   */
  def currentPS: Int

  /**
   * Changes the value of character's current health.
   * If the provided value is less than zero, it will be set to zero;
   * similarly if the provided value is greater than maxPs than it will be set to maxPs
   * @param newCurrentPS the new currentPs value
   */
  def currentPS_=(newCurrentPS: Int): Unit
}

object Health {

  def apply(maxPS: Int): Health = HealthImpl(maxPS: Int)

  private case class HealthImpl(override val maxPS: Int) extends Health {
    require(maxPS > 0)
    private var _currentPS = maxPS

    override def currentPS: Int = _currentPS

    override def currentPS_=(newCurrentPS: Int): Unit =
      if (newCurrentPS > maxPS) {
        _currentPS = maxPS
      } else if (newCurrentPS < 0) {
        _currentPS = 0
      } else {
        _currentPS = newCurrentPS
      }
  }

}

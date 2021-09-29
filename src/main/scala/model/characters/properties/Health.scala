package model.characters.properties

/**
 * Trait that represents the Health of a Character.
 */
sealed trait Health extends Serializable {
  val maxPS: Int

  def currentPS: Int

  def currentPS_=(newCurrentPS: Int): Unit
}

object Health {

  def apply(maxPS: Int): Health = HealthImpl(maxPS: Int)

  /**
   * The implementation of Health.
   *
   * @param maxPS the max amount of total PS reachable.
   */
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

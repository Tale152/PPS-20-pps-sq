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

  def apply(maxPS: Int): Health = new HealthImpl(maxPS: Int)

  /**
   * The implementation of Health.
   *
   * @param maxPS the max amount of total PS reachable.
   */
  private class HealthImpl(override val maxPS: Int) extends Health {
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

    def canEqual(other: Any): Boolean = other.isInstanceOf[HealthImpl]

    override def equals(other: Any): Boolean = other match {
      case that: HealthImpl =>
        (that canEqual this) &&
          _currentPS == that._currentPS &&
          maxPS == that.maxPS
      case _ => false
    }

    override def hashCode(): Int =
      Seq(_currentPS, maxPS).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

}

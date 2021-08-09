package model.characters.properties

object Health {

  /**
   * Trait that represents the Health of a Character.
   */
  sealed trait Health {
    val maxPS: Int

    def currentPS: Int

    def currentPS_=(newCurrentPS: Int): Unit
  }

  def apply(maxPS: Int): Health = new HealthImpl(maxPS: Int)

  /**
   * the implementation of Health
   *
   * @param maxPS the max amount of total PS reachable
   */
  private class HealthImpl(override val maxPS: Int) extends Health {
    require(maxPS > 0)
    private var _currentPS = maxPS

    override def currentPS: Int = _currentPS

    override def currentPS_=(newCurrentPS: Int): Unit = newCurrentPS match {
      case _ if (newCurrentPS > maxPS) => _currentPS = maxPS
      case _ if (newCurrentPS < 0) => _currentPS = 0
      case _ => _currentPS = newCurrentPS
    }

  }

}

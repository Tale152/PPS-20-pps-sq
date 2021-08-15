package view.statStatus

import controller.game.subcontroller.StatStatusController
import model.characters.properties.stats.StatName.StatName
import view.statStatus.panels.{ControlsPanel, HealthPanel, PlayerNamePanel, StatValuePanel}
import view.AbstractView

import javax.swing.BoxLayout

/**
 * Is a GUI that allows the user to check his player's stats. Associated with a StatStatusController.
 * @see [[controller.game.subcontroller.StatStatusController]]
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
trait StatStatusView extends AbstractView {
  /**
   * Allow to set the stats to be rendered.
   * @param stats list of stats, containing a pair with the StatName
   * associated to a pair of original stat value and current stat value
   * @see [[model.characters.properties.stats.Stat]]
   */
  def setStats(stats: List[(StatName, (Int, Int))]): Unit

  /**
   * Allow to set the player name to be rendered.
   * @param name player name to be rendered
   */
  def setPlayerName(name: String): Unit

  /**
   * Allow to set the health  of the player to be rendered.
   * @param health a pair with current health and max health
   */
  def setHealth(health: (Int, Int)): Unit
}

object StatStatusView {
  def apply(statStatusController: StatStatusController): StatStatusView = new StatStatusViewSwing(statStatusController)
}

private class StatStatusViewSwing(private val statStatusController: StatStatusController) extends StatStatusView {
  private var _stats: List[(StatName, (Int, Int))] = List()
  private var _playerName: String = ""
  private var _health: (Int, Int) = (0,0)

  this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))

  override def setStats(stats: List[(StatName, (Int, Int))]): Unit = _stats = stats

  override def setPlayerName(name: String): Unit = _playerName = name

  override def setHealth(health: (Int, Int)): Unit = _health = health

  def populateView(): Unit = {
    this.add(PlayerNamePanel(_playerName))
    this.add(HealthPanel(_health))
    for(stat <- _stats) this.add(StatValuePanel(stat))
    this.add(ControlsPanel(_ => statStatusController.close()))
  }
}

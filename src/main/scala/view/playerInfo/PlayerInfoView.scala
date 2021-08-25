package view.playerInfo

import controller.game.subcontroller.PlayerInfoController
import model.characters.properties.stats.StatName.StatName
import view.playerInfo.panels.{HealthPanel, PlayerNamePanel, StatValuePanel}
import view.AbstractView
import view.util.common.ControlsPanel

import javax.swing.BoxLayout

/**
 * Is a GUI that allows the user to check his player's stats. Associated with a PlayerInfoController.
 *
 * @see [[controller.game.subcontroller.PlayerInfoController]]
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
trait PlayerInfoView extends AbstractView {
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
   * Allow to set the health of the player to be rendered.
   * @param health a pair with current health and max health
   */
  def setHealth(health: (Int, Int)): Unit
}

object PlayerInfoView {
  def apply(playerInfoController: PlayerInfoController): PlayerInfoView = new PlayerInfoViewSwing(playerInfoController)
}

private class PlayerInfoViewSwing(private val playerInfoController: PlayerInfoController) extends PlayerInfoView {
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
    this.add(ControlsPanel(List(("b", ("[B] Back" , _ => playerInfoController.close())))))
  }
}

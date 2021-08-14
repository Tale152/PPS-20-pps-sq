package view.statStatus

import controller.game.subcontroller.StatStatusController
import model.characters.properties.stats.Stat
import model.characters.properties.stats.StatName.StatName
import view.statStatus.panels.{ControlsPanel, HealthPanel, PlayerNamePanel, StatValuePanel}
import view.{Frame, View}

import java.awt.Color
import javax.swing.{BoxLayout, JPanel}

/**
 * Is a GUI that allows the user to check his player's stats. Associated with a StatStatusController.
 * @see [[controller.game.subcontroller.StatStatusController]]
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
trait StatStatusView extends View {
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

  class StatStatusViewImpl(private val statStatusController: StatStatusController) extends JPanel with StatStatusView {

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
    this.setBackground(Color.BLACK)

    private var _stats: List[(StatName, (Int, Int))] = List()
    private var _playerName: String = ""
    private var _health: (Int, Int) = (0,0)

    /**
     * Allow to set the stats to be rendered.
     * @param stats list of stats, containing a pair with the StatName
     * associated to a pair of original stat value and current stat value
     * @see [[model.characters.properties.stats.Stat]]
     */
    override def setStats(stats: List[(StatName, (Int, Int))]): Unit = _stats = stats

    /**
     * Renders the view
     */
    override def render(): Unit = {
      this.updateUI()
      populateView()
      Frame.setPanel(this)
      Frame.setVisible(true)
    }

    private def populateView(): Unit = {
      this.removeAll()
      this.add(PlayerNamePanel(_playerName))
      this.add(HealthPanel(_health))
      for(stat <- _stats) this.add(StatValuePanel(stat))
      this.add(ControlsPanel(_ => statStatusController.close()))
    }

    /**
     * Allow to set the player name to be rendered.
     *
     * @param name player name to be rendered
     */
    override def setPlayerName(name: String): Unit = _playerName = name

    /**
     * Allow to set the health  of the player to be rendered.
     *
     * @param health a pair with current health and max health
     */
    override def setHealth(health: (Int, Int)): Unit = _health = health
}

  def apply(statStatusController: StatStatusController): StatStatusView = new StatStatusViewImpl(statStatusController)
}

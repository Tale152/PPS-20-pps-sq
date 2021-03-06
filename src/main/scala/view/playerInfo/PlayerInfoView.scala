package view.playerInfo

import controller.game.subcontroller.PlayerInfoController
import model.characters.properties.stats.StatName.StatName
import view.AbstractView
import view.playerInfo.panels.StatValuePanel
import view.util.common.{CharacterHealthPanel, CharacterNamePanel, ControlsPanel}
import view.util.scalaQuestSwingComponents.{SqSwingBorderPanel, SqSwingGridPanel}
import java.awt.{BorderLayout, Color}

import javax.swing.BorderFactory
import javax.swing.border.TitledBorder

/**
 * It is a GUI that allows the user to check his player's stats. Associated with a PlayerInfoController.
 *
 * @see [[controller.game.subcontroller.PlayerInfoController]]
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
sealed trait PlayerInfoView extends AbstractView {
  /**
   * Allows to set the stats to be rendered.
   *
   * @param stats list of stats, containing a pair with the StatName
   *              associated to a pair of original stat value and current stat value.
   * @see [[model.characters.properties.stats.Stat]]
   */
  def setStats(stats: List[(StatName, (Int, Int))]): Unit

  /**
   * Allows to set the player name to be rendered.
   *
   * @param name player name to be rendered.
   */
  def setPlayerName(name: String): Unit

  /**
   * Allow to set the health of the player to be rendered.
   *
   * @param health a pair with current health and max health.
   */
  def setHealth(health: (Int, Int)): Unit
}

object PlayerInfoView {

  private class PlayerInfoViewSwing(private val playerInfoController: PlayerInfoController) extends PlayerInfoView {
    private var _stats: List[(StatName, (Int, Int))] = List()
    private var _playerName: String = ""
    private var _health: (Int, Int) = (0, 0)

    private val border: TitledBorder =
      BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Current Health and Stats")
    border.setTitleColor(Color.WHITE)
    this.setLayout(new BorderLayout())

    override def setStats(stats: List[(StatName, (Int, Int))]): Unit = _stats = stats

    override def setPlayerName(name: String): Unit = _playerName = name

    override def setHealth(health: (Int, Int)): Unit = _health = health

    def populateView(): Unit = {
      val centerPanel = new SqSwingBorderPanel {}
      centerPanel.setBorder(border)
      val statPanel = new SqSwingGridPanel(0, 2) {}

      for (stat <- _stats) statPanel.add(StatValuePanel(stat))
      this.add(CharacterNamePanel("Player", _playerName), BorderLayout.NORTH)
      centerPanel.add(CharacterHealthPanel(_health), BorderLayout.SOUTH)
      centerPanel.add(statPanel, BorderLayout.CENTER)
      this.add(centerPanel, BorderLayout.CENTER)
      this.add(ControlsPanel(List(("b", ("[B] Back", _ => playerInfoController.close())))), BorderLayout.SOUTH)
    }
  }

  def apply(playerInfoController: PlayerInfoController): PlayerInfoView =
    new PlayerInfoViewSwing(playerInfoController)

}

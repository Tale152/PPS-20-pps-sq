package view.playerConfiguration

import controller.game.PlayerConfigurationController
import model.characters.properties.stats.Stat
import view.AbstractView
import view.playerConfiguration.panels.{InstructionPanel, PlayerNamePanel, RemainingPointsPanel, StatEditPanel}
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import javax.swing.BoxLayout

/**
 * It is a GUI that allows the user to set his properties. Associated with a PlayerConfigurationController.
 *
 * @see [[PlayerConfigurationController]]
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
sealed trait PlayerConfigurationView extends AbstractView {

  /**
   * Allows to set the number of remaining points to be rendered.
   *
   * @param points the number of remaining points.
   */
  def setRemainingPoints(points: Int): Unit

  /**
   * Allows to set the stats to be rendered; the user will be able to increase (unless the remaining points are zero)
   * or decrease the stat value (unless the stat value is one).
   *
   * @param stats list of stats to render.
   * @see [[model.characters.properties.stats.Stat]]
   * @see [[setRemainingPoints]]
   */
  def setStats(stats: List[Stat]): Unit
}

object PlayerConfigurationView {

  private class PlayerConfigurationViewSwing(private val playerConfigurationController: PlayerConfigurationController)
    extends PlayerConfigurationView {

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))

    private var _stats: List[Stat] = List()
    private var _remainingPoints: Int = 0
    private val playerNamePanel = PlayerNamePanel()
    private val nameCharactersLimit = 20

    override def setStats(stats: List[Stat]): Unit = _stats = stats

    override def setRemainingPoints(points: Int): Unit = _remainingPoints = points

    def populateView(): Unit = {
      this.add(InstructionPanel())
      this.add(RemainingPointsPanel(_remainingPoints))
      this.add(playerNamePanel)
      for (stat <- _stats) {
        this.add(StatEditPanel(playerConfigurationController, stat, _remainingPoints))
      }
      this.add(ControlsPanel(List(
        ("b", ("[B] Back", _ => playerConfigurationController.close())),
        ("c", ("[C] Confirm", _ => {
          playerNamePanel.playerName.trim match {
            case s: String if s == null || s == "" =>
              SqSwingDialog("Empty name", "Insert a valid name for the main character!",
                List(SqSwingButton("ok", _ => {})))
            case s: String if s.length > nameCharactersLimit => SqSwingDialog("Invalid name",
              "The name inserted is longer than " + nameCharactersLimit + " characters",
              List(SqSwingButton("ok", _ => {})))
            case _ => playerConfigurationController.confirm(playerNamePanel.playerName.trim)
          }
        })
        ))))
    }

  }

  def apply(playerConfigurationController: PlayerConfigurationController): PlayerConfigurationView =
    new PlayerConfigurationViewSwing(playerConfigurationController)
}

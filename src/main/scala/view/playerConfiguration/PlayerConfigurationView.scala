package view.playerConfiguration

import controller.PlayerConfigurationController
import model.characters.properties.stats.Stat
import view.playerConfiguration.panels.{InstructionPanel, RemainingPointsPanel, StatEditPanel}
import view.AbstractView
import view.util.common.ControlsPanel

import javax.swing.BoxLayout

/**
 * Is a GUI that allows the user to set his properties. Associated withe a PlayerConfigurationController.
 *
 * @see [[controller.PlayerConfigurationController]]
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
trait PlayerConfigurationView extends AbstractView {

  /**
   * Allow to set the number of remaining points to be rendered
   * @param points the number of remaining points
   */
  def setRemainingPoints(points: Int): Unit

  /**
   * Allow to set the stats to be rendered; the user will be able to increase (unless the remaining points are zero)
   * or decrease the stat value (unless the stat value is one)
   * @param stats list of stats to render
   * @see [[model.characters.properties.stats.Stat]]
   * @see [[setRemainingPoints]]
   */
  def setStats(stats: List[Stat]): Unit

}

object PlayerConfigurationView {
  def apply(playerConfigurationController: PlayerConfigurationController): PlayerConfigurationView =
    new PlayerConfigurationViewSwing(playerConfigurationController)
}

private class PlayerConfigurationViewSwing(private val playerConfigurationController: PlayerConfigurationController)
  extends PlayerConfigurationView {

  this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))

  private var _stats: List[Stat] = List()
  private var _remainingPoints: Int = 0

  override def setStats(stats: List[Stat]): Unit = _stats = stats

  override def setRemainingPoints(points: Int): Unit = _remainingPoints = points

  def populateView(): Unit = {
    this.add(InstructionPanel())
    this.add(RemainingPointsPanel(_remainingPoints))
    for(stat <- _stats) {
      this.add(
        StatEditPanel(
          stat,
          _remainingPoints,
          _ => playerConfigurationController.setStatValue(stat.statName(), stat.value() - 1),
          _ => playerConfigurationController.setStatValue(stat.statName(), stat.value() + 1),
        )
      )
    }
    this.add(ControlsPanel(List(
      ("b", ("[B] Back", _ => playerConfigurationController.close())),
      ("c", ("[C] Confirm", _ => playerConfigurationController.confirm()))
    )))
  }

}

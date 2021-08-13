package view.statConfiguration

import controller.StatConfigurationController
import model.characters.properties.stats.Stat
import view.statConfiguration.panels.{ButtonsPanel, InstructionPanel, RemainingPointsPanel, StatEditPanel}
import view.{Frame, View}

import java.awt.Color
import javax.swing.{BoxLayout, JPanel}

/**
 * Is a GUI that allows the user to set his stats.
 * @see [[model.characters.properties.stats.Stat]]
 * @see [[model.characters.properties.PropertiesContainer]]
 * @see [[model.characters.Player]]
 */
trait StatConfigurationView extends View {

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

object StatConfigurationView {

  class StatConfigurationViewImpl(private val statConfigurationController: StatConfigurationController)
    extends JPanel with StatConfigurationView {

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
    this.setBackground(Color.BLACK)

    private var _stats: List[Stat] = List()
    private var _remainingPoints: Int = 0

    /**
     * Renders the view
     */
    override def render(): Unit = {
      this.updateUI()
      populateView()
      Frame.setPanel(this)
      Frame.setVisible(true)
    }

    override def setStats(stats: List[Stat]): Unit = _stats = stats

    override def setRemainingPoints(points: Int): Unit = _remainingPoints = points

    private def populateView(): Unit = {
      this.removeAll()
      this.add(InstructionPanel())
      this.add(RemainingPointsPanel(_remainingPoints))
      for(stat <- _stats) {
        this.add(
          StatEditPanel(
            stat,
            _remainingPoints,
            _ => statConfigurationController.setStatValue(stat.statName(), stat.value() - 1),
            _ => statConfigurationController.setStatValue(stat.statName(), stat.value() + 1),
          )
        )
      }
      this.add(ButtonsPanel(_ => statConfigurationController.close(), _ => statConfigurationController.confirm()))
    }

  }

  def apply(statConfigurationController: StatConfigurationController): StatConfigurationView =
    new StatConfigurationViewImpl(statConfigurationController)
}

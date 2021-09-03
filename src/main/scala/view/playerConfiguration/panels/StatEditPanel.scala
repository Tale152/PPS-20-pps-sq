package view.playerConfiguration.panels

import controller.PlayerConfigurationController
import model.characters.properties.stats.Stat
import view.util.scalaQuestSwingComponents.{SqSwingBorderPanel, SqSwingButton, SqSwingLabel}

import java.awt.BorderLayout
import javax.swing.SwingConstants

object StatEditPanel {

  /**
   * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders a form containing the stat name
   * and value, alongside a minus button and a plus button.
   * When stat value equals one the minus button will be disabled; similarly when remaining points equals zero the plus
   * button will be disabled.
   *
   * @param controller      the controller passed from the parent view.
   * @param stat            the stat which name and value will be rendered.
   * @param remainingPoints the number of remaining points used for button enable/disable.
   * @see [[model.characters.properties.stats.Stat]]
   */
  class StatEditPanel(controller: PlayerConfigurationController,
                      stat: Stat,
                      remainingPoints: Int)
    extends SqSwingBorderPanel {
    this.add(
      SqSwingButton("-", _ => controller.setStatValue(stat.statName, stat.value - 1), stat.value != 1),
      BorderLayout.WEST
    )
    this.add(SqSwingLabel(
      stat.statName.toString + " [" + stat.value.toString + "]",
      alignment = SwingConstants.CENTER
    ), BorderLayout.CENTER)
    this.add(
      SqSwingButton("+", _ => controller.setStatValue(stat.statName, stat.value + 1), remainingPoints != 0),
      BorderLayout.EAST)
  }

  def apply(controller: PlayerConfigurationController,
            stat: Stat,
            remainingPoints: Int): StatEditPanel =
    new StatEditPanel(controller, stat, remainingPoints)
}

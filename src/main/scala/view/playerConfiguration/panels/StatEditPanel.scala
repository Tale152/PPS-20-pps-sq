package view.playerConfiguration.panels

import model.characters.properties.stats.Stat
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingFlowPanel, SqSwingLabel}

object StatEditPanel {

  /**
   * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders a form containing the stat name
   * and value, alongside a minus button and a plus button.
   * When stat value equals one the minus button will be disabled; similarly when remaining points equals zero the plus
   * button will be disabled.
   *
   * @param stat the stat which name and value will be rendered
   * @param remainingPoints the number of remaining points used for button enable/disable
   * @param onMinus strategy to apply on minus button click
   * @param onPlus strategy to apply on plus button click
   * @see [[model.characters.properties.stats.Stat]]
   */
  class StatEditPanel(stat: Stat, remainingPoints: Int, onMinus: Unit => Unit, onPlus: Unit => Unit)
    extends SqSwingFlowPanel {
    this.add(SqSwingButton("-", _ => onMinus(stat.value()), stat.value() != 1))
    this.add(SqSwingLabel(stat.statName().toString + " [" + stat.value().toString + "]"))
    this.add(SqSwingButton("+", _ => onPlus(stat.value()), remainingPoints != 0))
  }

  def apply(stat: Stat, remainingPoints: Int, onMinus: Unit => Unit, onPlus: Unit => Unit): StatEditPanel =
    new StatEditPanel(stat, remainingPoints, onMinus, onPlus)
}

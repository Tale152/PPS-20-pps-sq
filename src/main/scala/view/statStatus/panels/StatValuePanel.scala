package view.statStatus.panels

import model.characters.properties.stats.StatName.StatName
import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

import java.awt.Color

object StatValuePanel {

  /**
   * Panel contained int [[view.statStatus.StatStatusView]].
   * Renders the current stat value; if current stat value is higher than the original value it will be displayed green,
   * red if lower, white otherwise.
   * @param stat a pair with the StatName associated to a pair of original stat value and current stat value
   */
  class StatValuePanel(stat: (StatName, (Int, Int)))
    extends SqSwingFlowPanel{
    this.add(SqSwingLabel(stat._1.toString + "["))
    addValueLabel(stat)
    this.add(SqSwingLabel("]"))

    private def addValueLabel(stat: (StatName, (Int, Int))): Unit = {
      if(stat._2._2 > stat._2._1) { this.add(SqSwingLabel(stat._2._2.toString, Color.GREEN)) }
      else if (stat._2._2 < stat._2._1) { this.add(SqSwingLabel(stat._2._2.toString, Color.RED)) }
      else { this.add(SqSwingLabel(stat._2._2.toString)) }
    }

  }

  def apply(stat: (StatName, (Int, Int))): StatValuePanel = new StatValuePanel(stat)
}

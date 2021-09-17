package view.playerInfo.panels

import model.characters.properties.stats.StatName.StatName
import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}
import java.awt.Color

import view.util.StringUtil.TitleSize

/**
 * Panel contained in [[view.playerInfo.PlayerInfoView]].
 * Renders the current stat value; if current stat value is higher than the original value it will be displayed green,
 * red if lower, white otherwise.
 *
 * @param stat a pair with the StatName associated to a pair of original stat value and current stat value.
 */
case class StatValuePanel(stat: (StatName, (Int, Int))) extends SqSwingFlowPanel {
  this.add(SqSwingLabel(stat._1.toString + "[", labelSize = TitleSize))
  addValueLabel(stat)
  this.add(SqSwingLabel("]", labelSize = TitleSize))

  private def addValueLabel(stat: (StatName, (Int, Int))): Unit = {
    var statColor: Color = Color.WHITE
    if (stat._2._2 > stat._2._1) {
      statColor = Color.GREEN
    } else if (stat._2._2 < stat._2._1) {
      statColor = Color.RED
    }
    this.add(SqSwingLabel(stat._2._2.toString, statColor, TitleSize))
  }

}

package view.playerConfiguration.panels

import view.util.scalaQuestSwingComponents.{SqSwingFlowPanel, SqSwingLabel}

import java.awt.Color

object RemainingPointsPanel {

  /**
   * Panel contained in [[view.playerConfiguration.PlayerConfigurationView]]; renders a label containing the number
   * of remaining points. If said number has reached zero it will be printed in red, green otherwise.
   *
   * @param remainingPoints number of remaining points to render
   */
  class RemainingPointsPanel(remainingPoints: Int) extends SqSwingFlowPanel {
    this.add(SqSwingLabel("Remaining points: "))
    if (remainingPoints > 0) {
      this.add(SqSwingLabel(remainingPoints.toString, Color.GREEN))
    } else {
      this.add(SqSwingLabel(remainingPoints.toString, Color.RED))
    }
  }

  def apply(remainingPoints: Int): RemainingPointsPanel = new RemainingPointsPanel(remainingPoints)

}

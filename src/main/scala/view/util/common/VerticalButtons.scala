package view.util.common

import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridBagPanel}
import java.awt.{GridBagConstraints, Insets}

/**
 * Panel that renders a list of buttons in a vertical style.
 * @param buttonsList the list of buttons that is needed to be render vertically.
 */
case class VerticalButtons(buttonsList: List[SqSwingButton]) extends SqSwingGridBagPanel {

  private val buttonHeight = 20
  private val verticalPadding = 5
  private val c = new GridBagConstraints

  c.fill = GridBagConstraints.HORIZONTAL
  c.ipady = buttonHeight
  c.weightx = 0.0
  c.gridwidth = 3
  c.gridx = 0
  c.insets = new Insets(verticalPadding, 0, verticalPadding, 0)

  buttonsList.foreach(b => {
    b.setText(b.getText)
    this.add(b, c)
  })

}

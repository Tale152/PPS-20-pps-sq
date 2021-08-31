package view.history

import controller.game.subcontroller.HistoryController
import view.AbstractView
import view.history.panels.RecapPanel
import view.util.common.{ControlsPanel, Scrollable}

import java.awt.BorderLayout
import javax.swing.BorderFactory

/**
 * Is a GUI that allows the user to check his previous choices traversing the story.
 * Associated with a HistoryController.
 *
 * @see [[controller.game.subcontroller.HistoryController]]
 * @see [[model.StoryModel]]
 * @see [[model.nodes.StoryNode]]
 * @see [[model.nodes.Pathway]]
 */
trait HistoryView extends AbstractView {
  /**
   * Allow to set the previous choices made by player to be rendered.
   *
   * @param choices a list of pair containing the node narrative and the chosen pathway description
   */
  def setPreviousChoices(choices: List[(String, String)]): Unit

  /**
   * Allow to set the current node narrative to be rendered.
   *
   * @param narrative the current node narrative to display
   */
  def setCurrentNodeNarrative(narrative: String): Unit
}

object HistoryView {
  def apply(historyController: HistoryController): HistoryView = new HistoryViewImpl(historyController)
}

private class HistoryViewImpl(private val historyController: HistoryController) extends HistoryView {
  this.setLayout(new BorderLayout())
  private var _previousChoices: List[(String, String)] = List()
  private var _currentNodeDescription: String = ""

  private object HistoryViewValues {
    val RecapBottomBorder: Int = 10
  }

  override def setPreviousChoices(choices: List[(String, String)]): Unit = _previousChoices = choices

  override def setCurrentNodeNarrative(description: String): Unit = _currentNodeDescription = description

  import HistoryViewValues.RecapBottomBorder

  override def populateView(): Unit = {
    val recap = Scrollable(RecapPanel(_previousChoices, _currentNodeDescription))
    recap.setBorder(BorderFactory.createEmptyBorder(0, 0, RecapBottomBorder, 0))
    this.add(recap, BorderLayout.CENTER)
    this.add(ControlsPanel(List(("b", ("[B] Back", _ => historyController.close())))), BorderLayout.SOUTH)
  }
}

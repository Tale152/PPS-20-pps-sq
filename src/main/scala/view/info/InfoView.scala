package view.info

import controller.InfoController
import view.AbstractView
import view.info.dialog.InfoDialogs.NoSolutionDialog
import view.info.forms.OutcomeFromId.showOutcomeFromIdForm
import view.info.forms.PathChecker.showPathCheckerForm
import view.info.forms.StoryWalkthroughFromId.showStoryWalkthroughFromIdForm
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.BorderLayout
import java.awt.event.{ActionEvent, ActionListener}

/**
 * A GUI that allows the user to view some Story Info.
 */
sealed trait InfoView extends AbstractView

object InfoView {

  private class InfoViewImpl(private val infoController: InfoController) extends InfoView {

    /**
     * Sub-portion of render() where graphical elements are added.
     */
    override def populateView(): Unit = {

      this.add(Scrollable(VerticalButtons(List(
        SqSwingButton("Path Checker", _ => showPathCheckerForm(infoController)),
        SqSwingButton("Outcomes from ID", _ => showOutcomeFromIdForm(infoController)),
        SqSwingButton("All possible Outcomes", _ => {}),
        SqSwingButton("Story walkthrough from ID", _ => showStoryWalkthroughFromIdForm(infoController)),
        SqSwingButton("All Possible stories walkthrough", _ => {})
      ))), BorderLayout.CENTER)

      this.add(ControlsPanel(List(
        ("q", ("[Q] Quit", _ => infoController.close())))),
        BorderLayout.SOUTH)
    }
  }

  def apply(infoController: InfoController): InfoView = new InfoViewImpl(infoController)

  abstract class InfoButtonActionListener(val infoController: InfoController) extends ActionListener

  case class AllPossibleOutcomeListener(override val infoController: InfoController)
    extends InfoButtonActionListener(infoController) {
    override def actionPerformed(e: ActionEvent): Unit = {
      val solutions: List[List[Int]] = infoController.allStoryOutcomes
      if (solutions.nonEmpty) {

      } else {
        NoSolutionDialog(infoController)
      }
    }
  }
}

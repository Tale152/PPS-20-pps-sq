package view.explorer

import controller.ExplorerController
import view.AbstractView
import view.explorer.forms.OutcomeFromId.showOutcomeFromIdForm
import view.explorer.forms.PathChecker.showPathCheckerForm
import view.explorer.forms.StoryWalkthroughFromId.showStoryWalkthroughFromIdForm
import view.explorer.okButtonListener.noForms._
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton

import java.awt.BorderLayout

/**
 * A GUI that allows the user to view some Story Info.
 */
sealed trait ExplorerView extends AbstractView

object ExplorerView {

  private class ExplorerViewImpl(private val explorerController: ExplorerController) extends ExplorerView {

    this.setLayout(new BorderLayout())

    /**
     * Sub-portion of render() where graphical elements are added.
     */
    override def populateView(): Unit = {

      this.add(Scrollable(VerticalButtons(List(
        SqSwingButton("Path Checker", _ => showPathCheckerForm(explorerController)),
        SqSwingButton("Outcomes from ID", _ => showOutcomeFromIdForm(explorerController)),
        SqSwingButton("All possible Outcomes", AllPossibleOutcomeListenerWithoutForm(explorerController)),
        SqSwingButton("Story walkthrough from ID", _ => showStoryWalkthroughFromIdForm(explorerController)),
        SqSwingButton("All Possible stories walkthrough", AllPossibleWalkthroughListenerWithoutForm(explorerController))
      ))), BorderLayout.CENTER)

      this.add(ControlsPanel(List(
        ("q", ("[Q] Quit", _ => explorerController.close())))),
        BorderLayout.SOUTH)
    }
  }

  def apply(explorerController: ExplorerController): ExplorerView = new ExplorerViewImpl(explorerController)

}

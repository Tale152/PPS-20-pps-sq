package view.progressSaver

import controller.game.subcontroller.ProgressSaverController
import view.util.common.ControlsPanel
import view.AbstractView
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

import java.awt.BorderLayout
import java.awt.event.ActionEvent

/**
 * It is a GUI that allows the user to save his progress.
 * Associated with a ProgressSaverController.
 *
 * @see [[controller.game.subcontroller.ProgressSaverController]]
 */
sealed trait ProgressSaverView extends AbstractView {
  def showSuccessFeedback(onOk: Unit => Unit): Unit

  def showFailureFeedback(onOk: Unit => Unit): Unit
}

object ProgressSaverView {

  private class ProgressSaverViewImpl(private val progressSaverController: ProgressSaverController)
    extends ProgressSaverView {

    this.setLayout(new BorderLayout())

    override def populateView(): Unit = {
      this.add(InstructionPanel(), BorderLayout.CENTER)
      this.add(ControlsPanel(List(
        ("b", ("[B] Back", _ => progressSaverController.close())),
        ("s", ("[S] Save", _ => progressSaverController.saveProgress()))
      )),
        BorderLayout.SOUTH
      )
    }

    private def showFeedBackAndExecute(message: String, onOk: Unit => Unit): Unit = {
      SqSwingDialog("Save progress", message,
        List(SqSwingButton("ok", (_: ActionEvent) => onOk())))
    }

    override def showSuccessFeedback(onOk: Unit => Unit): Unit =
      showFeedBackAndExecute("Progress saved successfully", onOk)

    override def showFailureFeedback(onOk: Unit => Unit): Unit =
      showFeedBackAndExecute("An error occurred while saving progress", onOk)
  }

  def apply(progressSaverController: ProgressSaverController): ProgressSaverView =
    new ProgressSaverViewImpl(progressSaverController)
}

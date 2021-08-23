package view.progressSaver

import controller.game.subcontroller.ProgressSaverController
import view.AbstractView
import view.progressSaver.panels.{ControlsPanel, InstructionPanel}

import javax.swing.BoxLayout

/**
 * Is a GUI that allows the user to save his progress.
 * Associated with a ProgressSaverController.
 * @see [[controller.game.subcontroller.ProgressSaverController]]
 */
trait ProgressSaverView extends AbstractView

object ProgressSaverView {
  def apply(progressSaverController: ProgressSaverController): ProgressSaverView =
    new ProgressSaverViewImpl(progressSaverController)
}

private class ProgressSaverViewImpl(private val progressSaverController: ProgressSaverController)
  extends ProgressSaverView {

  this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))

  override def populateView(): Unit = {
    this.add(InstructionPanel())
    this.add(ControlsPanel(_ => progressSaverController.close(), _ => progressSaverController.saveProgress()))
  }
}

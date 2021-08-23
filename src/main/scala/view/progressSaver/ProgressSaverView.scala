package view.progressSaver

import controller.game.subcontroller.ProgressSaverController
import view.AbstractView

trait ProgressSaverView extends AbstractView

object ProgressSaverView {
  def apply(progressSaverController: ProgressSaverController): ProgressSaverView =
    new ProgressSaverViewImpl(progressSaverController)
}

private class ProgressSaverViewImpl(private val progressSaverController: ProgressSaverController)
  extends ProgressSaverView {

  override def populateView(): Unit = ???
}

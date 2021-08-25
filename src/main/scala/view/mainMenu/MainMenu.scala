package view.mainMenu

import controller.ApplicationController
import view.AbstractView
import view.mainMenu.panels.StoriesPanel
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingCenteredLabel

import java.awt.BorderLayout
import javax.swing.JScrollPane

trait MainMenu extends AbstractView {

  def setStories(stories: Set[String]): Unit

}

object MainMenu {
  def apply(applicationController: ApplicationController): MainMenu = new MainMenuImpl(applicationController)
}

private class MainMenuImpl(applicationController: ApplicationController) extends MainMenu {
  private var _stories: Set[String] = Set()

  this.setLayout(new BorderLayout())

  override def setStories(stories: Set[String]): Unit = _stories = stories

  override def populateView(): Unit = {
    this.add(SqSwingCenteredLabel("Please select a story"), BorderLayout.NORTH)
    this.add(new JScrollPane(StoriesPanel(_stories)))
    this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => applicationController.close())))), BorderLayout.SOUTH)
  }
}

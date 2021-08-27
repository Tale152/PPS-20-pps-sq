package view.mainMenu

import controller.ApplicationController
import view.AbstractView
import view.mainMenu.panels.StoriesPanel
import view.util.common.ControlsPanel
import view.util.scalaQuestSwingComponents.SqSwingCenteredLabel

import java.awt.{BorderLayout, Dimension}
import javax.swing.{JScrollPane, ScrollPaneConstants}
import javax.swing.border.EmptyBorder

trait MainMenu extends AbstractView {

  def setStories(stories: Set[String]): Unit

}

object MainMenu {
  def apply(applicationController: ApplicationController): MainMenu = new MainMenuImpl(applicationController)
}

private class MainMenuImpl(applicationController: ApplicationController) extends MainMenu {
  private var _stories: Set[String] = Set()
  val scrollIncrement = 10
  val lblSize = 25
  this.setLayout(new BorderLayout())

  override def setStories(stories: Set[String]): Unit = _stories = stories

  override def populateView(): Unit = {
    this.add(SqSwingCenteredLabel("Please select a story", size = lblSize), BorderLayout.NORTH)
    val scrollPane: JScrollPane = new JScrollPane(
      StoriesPanel(_stories),
      ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
    scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0))
    scrollPane.getVerticalScrollBar.setUnitIncrement(scrollIncrement)
    scrollPane.getVerticalScrollBar.setPreferredSize(new Dimension(0, 0))
    this.add(scrollPane)
    this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => applicationController.close())))), BorderLayout.SOUTH)
  }
}

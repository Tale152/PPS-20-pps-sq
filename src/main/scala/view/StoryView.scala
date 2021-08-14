package view

import controller.game.subcontroller.StoryController
import model.nodes.Pathway
import view.util.ScalaQuestPanel

import java.awt.event.ActionEvent
import java.awt.{BorderLayout, Color, GridLayout}
import javax.swing.{JButton, JLabel, JPanel}

object StoryView {

  /**
   * The [[view.StoryView.StoryView]] represents the GUI for the navigation between [[model.nodes.StoryNode]]
   */
  trait StoryView extends View {
    def setNarrative(narrative: String): Unit

    def setPathways(pathways: Set[Pathway]): Unit
  }

  /**
   * Implementation of [[view.StoryView.StoryView]]
   *
   * @param storyController the [[controller.game.subcontroller.StoryController]] for navigating between nodes
   */
  class StoryViewImpl(val storyController: StoryController) extends ScalaQuestPanel
    with StoryView {

    private var _narrative: String = ""
    private var _pathways: Seq[Pathway] = Seq()

    val statsPanel: JPanel = new JPanel()
    val narrativePanel: JPanel = new JPanel()
    val pathwaysPanel: JPanel = new JPanel()
    pathwaysPanel.setLayout(new GridLayout(0, 2))

    statsPanel.setOpaque(false)
    narrativePanel.setOpaque(false)
    pathwaysPanel.setOpaque(false)

    /**
     * Represents graphically the different pathways
     *
     * @param paths the available paths
     */
    private def printPaths(paths: Seq[Pathway]): Unit = {
      paths.foreach(i => {
        val button: JButton = new JButton(i.description)
        button.setFocusPainted(false)
        button.setBackground(Color.LIGHT_GRAY)
        button.addActionListener((_: ActionEvent) => {
          storyController.choosePathWay(i)
          render()
        })
        pathwaysPanel.add(button)
      })
    }

    override def setNarrative(narrative: String): Unit = _narrative = narrative

    override def setPathways(pathways: Set[Pathway]): Unit = _pathways = pathways.toSeq

    /**
     * Removes all element from each panel
     */
    private def clear(): Unit = {
      this.removeAll()
      statsPanel.removeAll()
      pathwaysPanel.removeAll()
      narrativePanel.removeAll()
    }

    /**
     * Renders the entire view, made by narrative and pathways
     */
    override def render(): Unit = {
      this.clear()
      this.updateUI()

      val statsButton: JButton = new JButton("Stats")
      statsButton.addActionListener((_: ActionEvent) => storyController.goToStatStatus())
      statsPanel.add(statsButton)

      val narrativeLabel: JLabel = new JLabel(_narrative)
      narrativeLabel.setForeground(Color.WHITE)
      narrativePanel.add(narrativeLabel)

      this.add(statsPanel, BorderLayout.NORTH)
      this.add(narrativePanel, BorderLayout.CENTER)
      this.printPaths(_pathways)
      this.add(pathwaysPanel, BorderLayout.SOUTH)

      Frame.setPanel(this)
      Frame.setVisible(true)
    }
  }

  def apply(storyController: StoryController): StoryView =
    new StoryViewImpl(storyController)
}

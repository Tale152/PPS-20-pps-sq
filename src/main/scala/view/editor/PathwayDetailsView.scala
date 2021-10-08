package view.editor

import controller.editor.EditorController
import model.nodes.StatPrerequisite
import model.nodes.{ItemPrerequisite, Pathway, StoryNode}
import view.AbstractView
import view.util.common.{ControlsPanel, Scrollable}
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingTextArea}

import java.awt.BorderLayout
import javax.swing.BoxLayout

/**
 * A sub-view of the EditorView. Used to display info about a Pathway.
 */
sealed trait PathwayDetailsView extends AbstractView

object PathwayDetailsView {

  private case class PathwayDetailsViewImpl(private val originNode: StoryNode,
                                private val pathway: Pathway,
                                private val editorController: EditorController) extends PathwayDetailsView{

    this.setLayout(new BorderLayout())
    private val centerPanel = new SqSwingBoxPanel(BoxLayout.Y_AXIS){}

    override def populateView(): Unit = {
      centerPanel.add(SqSwingTextArea(
        "Origin node: " + originNode.id + "\nDestination node: " + pathway.destinationNode.id +
          "\nDescription:\n" + pathway.description +
          "\nHas prerequisite: " + pathway.prerequisite.nonEmpty
      ))
      if(pathway.prerequisite.nonEmpty){
        pathway.prerequisite.get match {
          case statPrerequisite: StatPrerequisite => centerPanel.add(
            SqSwingTextArea("Prerequisite on stat: " + statPrerequisite.statName + " " + statPrerequisite.value)
          )
          case itemPrerequisite: ItemPrerequisite => centerPanel.add(
            SqSwingTextArea("Prerequisite on key item: " + itemPrerequisite.item.name)
          )
        }
      }
      this.add(Scrollable(centerPanel))
      this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => editorController.execute())))), BorderLayout.SOUTH)
    }
  }

  def apply(originNode: StoryNode, pathway: Pathway, editorController: EditorController): PathwayDetailsView =
    PathwayDetailsViewImpl(originNode, pathway, editorController)
}

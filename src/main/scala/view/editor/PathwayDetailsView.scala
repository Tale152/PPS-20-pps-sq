package view.editor

import controller.editor.EditorController
import model.nodes.util.{ItemPrerequisite, StatPrerequisite}
import model.nodes.{Pathway, StoryNode}
import view.AbstractView
import view.util.common.{ControlsPanel, Scrollable}
import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqTextArea}

import java.awt.BorderLayout
import javax.swing.BoxLayout

case class PathwayDetailsView(private val originNode: StoryNode,
                              private val pathway: Pathway,
                              private val editorController: EditorController) extends AbstractView{

  this.setLayout(new BorderLayout())
  private val centerPanel = new SqSwingBoxPanel(BoxLayout.Y_AXIS){}

  override def populateView(): Unit = {
    centerPanel.add(SqTextArea(
      "Origin node: " + originNode.id + "\nDestination node: " + pathway.destinationNode.id +
      "\nDescription:\n" + pathway.description +
      "\nHas prerequisite: " + pathway.prerequisite.nonEmpty
    ))
    if(pathway.prerequisite.nonEmpty){
      pathway.prerequisite.get match {
        case statPrerequisite: StatPrerequisite => centerPanel.add(
          SqTextArea("Prerequisite on stat: " + statPrerequisite.statName + " " + statPrerequisite.value)
        )
        case itemPrerequisite: ItemPrerequisite => centerPanel.add(
          SqTextArea("Prerequisite on key item: " + itemPrerequisite.item.name)
        )
      }
    }
    this.add(Scrollable(centerPanel))
    this.add(ControlsPanel(List(("q", ("[Q] Quit", _ => editorController.execute())))), BorderLayout.SOUTH)
  }
}

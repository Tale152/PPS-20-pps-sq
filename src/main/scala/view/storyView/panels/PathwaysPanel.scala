package view.storyView.panels

import model.nodes.Pathway
import view.util.scalaQuestSwingComponents.{SqSwingButton, SqSwingGridPanel}

object PathwaysPanel {

  class PathwaysPanel(paths: Seq[Pathway], onPathwayChosen: Pathway => Unit) extends SqSwingGridPanel(0,2){
    paths.foreach(p => this.add(SqSwingButton(p.description, _ => onPathwayChosen(p))))
  }

  def apply(paths: Seq[Pathway], onPathwayChosen: Pathway => Unit): PathwaysPanel =
    new PathwaysPanel(paths, onPathwayChosen)
}

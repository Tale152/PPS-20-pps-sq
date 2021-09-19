package view.info

import controller.InfoController
import controller.prolog.structs.StructsNames.Predicates._
import controller.util.Resources.ResourceName.FileExtensions.TxtExtension
import controller.util.serialization.StringUtil.listFormattedLikeArray
import view.AbstractView
import view.info.dialog.InfoDialogs.{FileCreatedDialog, NoSolutionDialog}
import view.info.forms.OutcomeFromId.showOutcomeFromIdForm
import view.info.forms.PathChecker.showPathCheckerForm
import view.info.forms.StoryWalkthroughFromId.showStoryWalkthroughFromIdForm
import view.util.common.{ControlsPanel, Scrollable, VerticalButtons}
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.fileChooser.SqSwingDirectoryChooser

import java.awt.BorderLayout
import java.awt.event.{ActionEvent, ActionListener}

/**
 * A GUI that allows the user to view some Story Info.
 */
sealed trait InfoView extends AbstractView

object InfoView {

  private class InfoViewImpl(private val infoController: InfoController) extends InfoView {

    /**
     * Sub-portion of render() where graphical elements are added.
     */
    override def populateView(): Unit = {

      this.add(Scrollable(VerticalButtons(List(
        SqSwingButton("Path Checker", _ => showPathCheckerForm(infoController)),
        SqSwingButton("Outcomes from ID", _ => showOutcomeFromIdForm(infoController)),
        SqSwingButton("All possible Outcomes", AllPossibleOutcomeListener(infoController)),
        SqSwingButton("Story walkthrough from ID", _ => showStoryWalkthroughFromIdForm(infoController)),
        SqSwingButton("All Possible stories walkthrough", AllPossibleWalkthroughListener(infoController))
      ))), BorderLayout.CENTER)

      this.add(ControlsPanel(List(
        ("q", ("[Q] Quit", _ => infoController.close())))),
        BorderLayout.SOUTH)
    }
  }

  def apply(infoController: InfoController): InfoView = new InfoViewImpl(infoController)

  abstract class InfoButtonActionListener[A](val infoController: InfoController) extends ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      if (solutions.nonEmpty) {
        SqSwingDirectoryChooser(
          "Select folder where to save the file",
          folderPath => saveFile(folderPath)
        )
      } else {
        NoSolutionDialog(infoController)
      }
    }

    def solutions: List[List[A]]

    def saveFile(folderPath: String): Unit
  }

  case class AllPossibleOutcomeListener(override val infoController: InfoController)
    extends InfoButtonActionListener[Int](infoController) {
    override def solutions: List[List[Int]] = infoController.allStoryOutcomes

    override def saveFile(folderPath: String): Unit = {
      val filePath: String = folderPath + "/" + AllFinalNodesSolutionsPredicateName + "(0,X)." + TxtExtension
      val builder = InfoFileTextBuilder().title("All the possible story outcomes.")
      solutions.foreach(s => builder.addRecord(listFormattedLikeArray(s)))
      builder.size(solutions.size).outputFile(filePath)
      FileCreatedDialog(infoController, filePath)
    }
  }

  case class AllPossibleWalkthroughListener(override val infoController: InfoController)
    extends InfoButtonActionListener[String](infoController) {
    override def solutions: List[List[String]] = infoController.allStoryWalkthrough

    override def saveFile(folderPath: String): Unit = {
      val filePath: String = folderPath + "/" + AllStoryWalkthroughPredicateName + "(0,X)." + TxtExtension
      val builder = InfoFileTextBuilder().title("All the possible story Walkthrough.")
      solutions.foreach(s0 => {
        builder.addRecord("-------STORY START-------")
        s0.foreach(s1 => builder.addRecord(s1))
        builder.addRecord("--------STORY END--------\n")
      })
      builder.size(solutions.size).outputFile(filePath)
      FileCreatedDialog(infoController, filePath)
    }
  }

}

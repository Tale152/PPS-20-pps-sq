package view

import controller.subcontroller.StoryController
import model.nodes.Pathway
import view.util.InputUtility

object StoryView {

  /**
   * The [[view.StoryView.StoryView]] represents the GUI for the navigation between [[model.nodes.StoryNode]]
   */
  trait StoryView extends View {
    def setNarrative(narrative: String): Unit

    def setPathways(pathways: Set[Pathway]): Unit
  }

  class StoryViewImpl(val storyController: StoryController, val inputStrategy: () => String) extends StoryView {

    private var _narrative: String = ""
    private var _pathways: Seq[Pathway] = Seq()

    object NotValidInputStrategy {
      //used to filter invalid input from user
      val OnlyChoicesStrategy: String => Boolean = input =>
            input == "" ||
            !(input forall Character.isDigit) ||
            input.toInt > _pathways.size ||
            input.toInt < 1
    }

    /**
     * Gets the user input and sends it to [[controller.subcontroller.StoryController]]
     */
    private def waitForUserInput(): Unit = {
      if (_pathways.nonEmpty) {
        val chosenPath = InputUtility.inputAsInt(inputStrategy, NotValidInputStrategy.OnlyChoicesStrategy)
        storyController.choosePathWay(_pathways(chosenPath))
      } else {
        scala.io.StdIn.readLine()
        storyController.close()
      }
    }

    /**
     * Represents graphically the different pathways
     *
     * @param paths the available paths
     */
    private def printPaths(paths: Seq[Pathway]): Unit = {
      paths.zipWithIndex.foreach(t => println(t._2 + 1 + ") " + t._1.description))
    }

    override def setNarrative(narrative: String): Unit = _narrative = narrative

    override def setPathways(pathways: Set[Pathway]): Unit = _pathways = pathways.toSeq

    /**
     * Renders the entire view, made by narrative, pathways and user input
     */
    override def render(): Unit = {
      println(_narrative)
      if (_pathways.nonEmpty) {
        this.printPaths(_pathways)
        println("Choose your way: ")
      }
      waitForUserInput()
    }
  }

  def apply(storyController: StoryController, inputStrategy: () => String): StoryView =
    new StoryViewImpl(storyController, inputStrategy)
}

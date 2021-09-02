import controller.ApplicationController
import controller.editor.Graph.GraphBuilder
import controller.util.ResourceName
import controller.util.ResourceName.MainDirectory.RootGameDirectory
import controller.util.ResourceName.testRandomStoryName
import controller.util.serialization.StoryNodeSerializer.deserializeStory
import model.nodes.{Pathway, StoryNode}

/**
 *  The Application entry point.
 */
object Main extends App {

  ApplicationController.execute()

  System.setProperty("org.graphstream.ui", "swing")
  val end1: StoryNode = StoryNode(2, "end 1", None, Set(), List())
  val end2: StoryNode = StoryNode(1, "end 2", None, Set(), List())
  val toEnd1: Pathway = Pathway("to end 1", end1, Some(m => m.player.maxPS == 100))
  val toEnd2: Pathway = Pathway("to end 2", end2, None)
  val r: StoryNode = StoryNode(0, "r", None, Set(toEnd1, toEnd2), List())

  GraphBuilder.build(
    r
  ).display

}

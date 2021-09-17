package controller.prolog.structs

import model.nodes.{Pathway, StoryNode}

/**
 * Utility methods used to test Prolog structs.
 */
object StructUtil {

  def areAllSolutionPresent[A](_expected: Set[Traversable[A]], solutions: Traversable[A]*): Boolean = {
    var expected = _expected
    solutions.forall(s => {
      val res = expected.contains(s)
      expected -= s
      res
    })
  }

  def storyNodeWithAMiddleNodeAndTwoFinalNodes(): StoryNode = {
    val secondDestinationNode: StoryNode = StoryNode(3, "narrative(3)", None, Set.empty, List())
    val secondDestinationPathway: Pathway = Pathway("description(1-3)", secondDestinationNode, None)
    val firstDestinationNode: StoryNode = StoryNode(2, "narrative(2)", None, Set.empty, List())
    val firstDestinationPathway: Pathway = Pathway("description(1-2)", firstDestinationNode, None)
    val middleNode: StoryNode = StoryNode(
      1, "narrative(1)", None,
      Set(firstDestinationPathway, secondDestinationPathway),
      List()
    )
    val middlePathway: Pathway = Pathway("description(0-1)", middleNode, None)
    StoryNode(0, "narrative(0)", None, Set(middlePathway), List())
  }

}

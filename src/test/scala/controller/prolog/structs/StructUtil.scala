package controller.prolog.structs

import model.nodes.StoryNode

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

}

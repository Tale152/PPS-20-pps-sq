package controller.prolog.engine.util

import alice.tuprolog._

/**
 * An Iterator that returns Terms as long as they are present.
 * @param engine The [[alice.tuprolog.Prolog]] engine.
 * @param goal the goal that the engine should check.
 */
private[engine] case class PrologEngineIterator(engine: Prolog, goal: Term)
  extends Iterator[Term] {
  var optionalSolution: Option[Term] = getNextSolution(_ => engine.solve(goal))

  override def hasNext: Boolean = optionalSolution.isDefined

  override def next(): Term = {
    val solution: Term = optionalSolution.get
    optionalSolution = getNextSolution(_ => engine.solveNext())
    solution
  }

  private def getNextSolution(getStrategy: Unit => SolveInfo): Option[Term] = {
    try {
      Some(getStrategy().getSolution)
    } catch {
      case _: NoSolutionException => None
      case _: NoMoreSolutionException => None
    }
  }

}

package controller.prolog

import alice.tuprolog._
import controller.prolog.EngineTheory.{EngineFileTheory, StoryNodeTheory}
import controller.prolog.structs.Structs
import model.nodes.StoryNode

/**
 * Create a Prolog Engine that has a finite set of facts. A [[model.nodes.StoryNode]] must be passed to generate the
 * [[controller.prolog.engine.EngineTheory.StoryNodeTheory]].
 *
 * @param storyNode the [[model.nodes.StoryNode]] used to generate the Theory of the Engine.
 */
case class SqPrologEngine(storyNode: StoryNode) {

  val engine: Prolog = new Prolog
  val engineTheory: Theory = EngineFileTheory()
  engineTheory.append(StoryNodeTheory(storyNode))
  engine.setTheory(engineTheory)

  def resolve[A <: Term](goal: A): Stream[A] = PrologEngineIterator(engine, goal).toStream

  def theory: String = engineTheory.toString

  /**
   * Create an iterator from the solutions that a [[alice.tuprolog.Prolog]] engine find for a specific goal.
   *
   * @param engine The Prolog engine.
   * @param goal   The goal to check.
   * @tparam A The Generic Term param used to force the solution to be the same type of the goal.
   */
  private case class PrologEngineIterator[A <: Term](engine: Prolog, goal: A)
    extends Iterator[A] {

    var optionalSolution: Option[A] = getNextSolution(() => engine.solve(goal))

    override def hasNext: Boolean = optionalSolution.isDefined

    override def next(): A = {
      try {
        optionalSolution.get
      } finally {
        optionalSolution = getNextSolution(() => engine.solveNext())
      }
    }

    private def getNextSolution(onNextSolution: () => SolveInfo): Option[A] = {
      try {
        Some(Structs(onNextSolution().getSolution).asInstanceOf[A])
      } catch {
        case _: NoSolutionException => None
        case _: NoMoreSolutionException => None
      }
    }

  }
}

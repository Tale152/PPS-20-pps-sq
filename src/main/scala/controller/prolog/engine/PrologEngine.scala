package controller.prolog.engine

import alice.tuprolog._
import controller.prolog.engine.util.PrologEngineIterator

/**
 * A Prolog engine that finds solution to a specific goal, starting from a finite set of facts.
 * @param clauses the set of facts that the Engine will use.
 */
case class PrologEngine(clauses: Set[String]) extends (Term => Stream[Term]) {

  override def apply(goal: Term): Stream[Term] = {
    val engine: Prolog = new Prolog
    engine.setTheory(new Theory(clauses mkString " "))
    PrologEngineIterator(engine, goal).toStream

  }
}

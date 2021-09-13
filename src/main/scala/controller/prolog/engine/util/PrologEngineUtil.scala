package controller.prolog.engine.util

import alice.tuprolog.{Struct, Term}

import scala.language.implicitConversions

object PrologEngineUtil {
  /**
   * Implicit that let the String be treated as a [[alice.tuprolog.Term]].
   * @param string the String to treat like a term.
   * @return The term that the string represents.
   */
  implicit def stringToTerm(string: String): Term = Term.createTerm(string)

  /**
   * Implicit that let a Seq be treated as a [[alice.tuprolog.Term]].
   * @param sequence the sequence of generic objects.
   * @tparam T the generic type of the sequence.
   * @return a formatted string for the items.
   */
  implicit def seqToTerm[T](sequence: Seq[T]): Term = sequence.mkString("[", ",", "]")


  implicit class RichTerm(term: Term) {
    /**
     * Extract a [[alice.tuprolog.Term]] from a structured Term.
     * @param index the index of the element to extract.
     * @return the Term present in the index specified.
     */
    def extractTerm(index: Integer): Term =
      term.asInstanceOf[Struct].getArg(index).getTerm
  }
}
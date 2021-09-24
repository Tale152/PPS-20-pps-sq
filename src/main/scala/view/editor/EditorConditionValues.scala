package view.editor

object EditorConditionValues {

  object InputPredicates {
    val NonEmptyString: String => Boolean = s => s.trim.nonEmpty
  }

  object ConditionDescriptions {

    object Subjects {
      val TheName: String = "The name"

      val TheDescription: String = "The description"

      val TheNarrative: String = "The narrative"
    }

    val mustBeSpecified: String => String = subject => subject + " must be specified."
  }

}

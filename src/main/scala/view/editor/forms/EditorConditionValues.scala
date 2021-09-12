package view.editor.forms

object EditorConditionValues {

  object InputPredicates {
    val NonEmptyString: String => Boolean = s => s.trim.nonEmpty
  }

  object ConditionDescriptions {

    object Subjects {

      val TheId: String = "The ID"

      val TheStartingId: String = "The starting ID"

      val TheEndingId: String = "The ending ID"

      val TheDescription: String = "The description"

      val TheNarrative: String = "The narrative"

      val TheStoryNode: String = "The StoryNode"

      val TheStartingStoryNode: String = "The starting StoryNode"

      val TheEndStoryNode: String = "The ending StoryNode"

      val ThePathway: String = "The Pathway"

      val TheValue: String = "The value"
    }

    val mustBeSpecified: String => String = subject => subject + " must be specified."

    val doesNotExists: String => String = subject => subject + " does not exists."

    val isNotValid: String => String = subject => subject + " is not valid."

  }

}

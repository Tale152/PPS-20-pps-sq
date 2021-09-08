package view.editor

object FormConditionValues {

  object Conditions {
    val NonEmptyString: String => Boolean = s => s.trim.nonEmpty
  }

  object ConditionDescriptions {

    val InvalidIDMessage: String = shouldBeSpecified("An ID")

    val InvalidStartingIDMessage: String = shouldBeSpecified("A starting ID")

    val InvalidEndingIDMessage: String = shouldBeSpecified("An ending ID")

    val InvalidDescriptionMessage: String = shouldBeSpecified("A description")

    val InvalidNarrativeMessage: String = shouldBeSpecified("A narratives")

    val StoryNodeDoesNotExists: String = doesNotExists("The StoryNode")

    val StartingStoryNodeDoesNotExists: String = doesNotExists("The starting StoryNode")

    val EndStoryNodeDoesNotExists: String = doesNotExists("The ending StoryNode")

    def nodeDoesNotExist(id: () => Int) : String = "Node " + id().toString + " doesn't exist."

    private def shouldBeSpecified(subject: String): String = subject + " should be specified."

    private def doesNotExists(subject: String): String = subject + " does not exists."

  }

}

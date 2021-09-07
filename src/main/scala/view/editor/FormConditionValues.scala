package view.editor


object FormConditionValues {

  object Conditions {
    val NonEmptyString: String => Boolean = s => s.trim.nonEmpty
  }

  object ConditionDescriptions {

    def nodeDoesNotExist(id: Int) : String = "Node " + id.toString + " doesn't exist."

    val InvalidIDMessage: String = shouldBeSpecified("An ID")

    val InvalidStartingIDMessage: String = shouldBeSpecified("A starting ID")

    val InvalidEndingIDMessage: String = shouldBeSpecified("An ending ID")

    val InvalidDescriptionMessage: String = shouldBeSpecified("A description")

    val InvalidNarrativeMessage: String = shouldBeSpecified("A narratives")

    private def shouldBeSpecified(subject: String) = subject + " should be specified."

  }

}

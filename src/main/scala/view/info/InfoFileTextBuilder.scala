package view.info

import controller.util.serialization.FileUtil.TextFileBuilder
import view.info.InfoFileTextBuilder.InfoFileTextBuilderOrderingTraits._

object InfoFileTextBuilder {

  /**
   * Traits used to force order of operations.
   */
  object InfoFileTextBuilderOrderingTraits {

    trait BuilderTitleToSet {
      def title(title: String): BuilderRecordsToSet
    }

    trait BuilderRecordsToSet {
      def addRecord(record: String): BuilderRecordsToSet

      def size(size: Int): BuilderReadyToOutputFile
    }

    trait BuilderReadyToOutputFile {
      def outputFile(filePath: String): Unit
    }
  }

  private class InfoFileTextBuilderImplToBuilder()
    extends BuilderTitleToSet with BuilderRecordsToSet with BuilderReadyToOutputFile {

    private val textFileBuilder: TextFileBuilder = TextFileBuilder()

    def title(title: String): BuilderRecordsToSet = {
      textFileBuilder.addText(title + "\n\n")
      this
    }

    def addRecord(record: String): BuilderRecordsToSet = {
      textFileBuilder.addText(record + "\n")
      this
    }

    def size(size: Int): BuilderReadyToOutputFile = {
      textFileBuilder.addText("size: " + size + "\n")
      this
    }

    override def outputFile(filePath: String): Unit = textFileBuilder.outputFile(filePath)

  }

  def apply(): BuilderTitleToSet = new InfoFileTextBuilderImplToBuilder()
}


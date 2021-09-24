package view.explorer

import controller.util.serialization.FileUtil.TextFileBuilder
import controller.util.serialization.StringUtil.traversableFormattedLikeArray
import view.explorer.ExplorerFileTextBuilder.ExplorerFileTextBuilderOrderingTraits._

/**
 * Object that uses a [[controller.util.serialization.FileUtil.TextFileBuilder]] to create a custom TextFileBuilder
 * useful to create Explorer files.
 */
object ExplorerFileTextBuilder {

  /**
   * Traits used to force order of operations.
   */
  object ExplorerFileTextBuilderOrderingTraits {

    trait BuilderTitleToSet {
      def title(title: String): BuilderRecordsToSet
    }

    trait BuilderRecordsToSet {
      def addRecord(record: String): BuilderRecordsToSet

      def addStory(story: List[String]): BuilderRecordsToSet

      def addIterable[A](iterable: Iterable[A]): BuilderRecordsToSet

      def addIterableOfIterables[A](iterables: Iterable[Iterable[A]]): BuilderRecordsToSet

      def addStories(stories: Iterable[List[String]]): BuilderRecordsToSet

      def size(size: Int): BuilderReadyToOutputFile
    }

    trait BuilderReadyToOutputFile {
      def outputFile(filePath: String): Unit
    }
  }

  private class ExplorerFileTextBuilderImpl()
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

    override def addIterable[A](iterable: Iterable[A]): BuilderRecordsToSet =
      addRecord(traversableFormattedLikeArray(iterable))

    override def addIterableOfIterables[A](iterables: Iterable[Iterable[A]]): BuilderRecordsToSet = {
      iterables.foreach(i => addIterable(i))
      this
    }

    def size(size: Int): BuilderReadyToOutputFile = {
      textFileBuilder.addText("\nsize: " + size + "\n")
      this
    }

    override def addStory(story: List[String]): BuilderRecordsToSet = {
      addRecord("-------STORY START-------")
      story.foreach(s => addRecord(s))
      addRecord("--------STORY END--------\n")
    }

    override def addStories(stories: Iterable[List[String]]): BuilderRecordsToSet = {
      stories.foreach(s => addStory(s))
      this
    }

    override def outputFile(filePath: String): Unit = textFileBuilder.outputFile(filePath)

  }

  def apply(): BuilderTitleToSet = new ExplorerFileTextBuilderImpl()
}


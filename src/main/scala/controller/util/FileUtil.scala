package controller.util

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable.ListBuffer

object FileUtil {

  /**
   * Case class builder used to create a text file.
   */
  case class TextFileBuilder() {

    private val content: ListBuffer[String] = ListBuffer()

    def addText(string: String): TextFileBuilder = {
      content += string
      this
    }

    def outputFile(path: String): Unit = writeFile(path, content.toList)

    private def writeFile(path: String, lines: Seq[String]): Unit = {
      val file = new File(path)
      val bw = new BufferedWriter(new FileWriter(file))
      for (line <- lines) {
        bw.write(line)
      }
      bw.close()
    }
  }

}

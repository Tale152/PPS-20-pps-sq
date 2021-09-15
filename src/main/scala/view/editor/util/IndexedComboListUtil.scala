package view.editor.util

object IndexedComboListUtil {

  def createIndexedOption(index: Int, name: String): String = "[" + (index + 1) + "]" + name

  def extractIndexFromOption(indexedOption: String): Int =
    indexedOption.substring(0, indexedOption.indexOf("]")).replace("[", "").toInt - 1

}

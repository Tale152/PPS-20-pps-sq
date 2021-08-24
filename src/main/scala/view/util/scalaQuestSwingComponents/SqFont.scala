package view.util.scalaQuestSwingComponents

import java.awt.Font

case class SqFont(bold: Boolean, fontSize: Int) extends Font("Verdana",
  if (bold) {
    Font.BOLD
  } else {
    Font.PLAIN
  }, fontSize)

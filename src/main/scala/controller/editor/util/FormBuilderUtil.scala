package controller.editor.util

import de.milchreis.uibooster.model.FormBuilder

/**
 * Utility Object that contains useful features to decorate a [[de.milchreis.uibooster.model.FormBuilder]].
 */
object FormBuilderUtil {
  implicit class BetterFormBuilder(val formBuilder: FormBuilder ) {
    def addInt(label: String): FormBuilder =
      formBuilder.addCustomElement(IntegerFormElement(label))
  }
}

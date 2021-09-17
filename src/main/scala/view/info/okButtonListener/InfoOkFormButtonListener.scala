package view.info.okButtonListener

import controller.InfoController
import view.form.{Form, OkFormButtonListener}

abstract class InfoOkFormButtonListener(override val form: Form, override val controller: InfoController)
  extends OkFormButtonListener(form, controller)

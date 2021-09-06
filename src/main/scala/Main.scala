import controller.ApplicationController
import controller.util.ResourceLoader

/**
 *  The Application entry point.
 */
object Main extends App {
  ResourceLoader.loadResources()
  ApplicationController.execute()
}

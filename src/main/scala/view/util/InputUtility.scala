package view.util

object InputUtility {
  /**
   * Does check the input to prevent side effects
   *
   * @return the chosen pathway
   */
   def inputAsInt(inputValueStrategy: () => String, notValidStrategy: String => Boolean): Int = {
    var input: String = ""
    do {
      input = inputValueStrategy()
      if(notValidStrategy(input)){
        println("Please insert a valid value.")
      }
    } while (notValidStrategy(input))
    input.toInt - 1
  }
}
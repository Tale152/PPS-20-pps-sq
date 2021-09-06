package specs

import org.scalatest.Tag

/**
 * Test Tags.
 */
object Tags {

  /** Tag used to ignore the test on the GitHub Action Workflow */
  object IgnoreGitHubAction extends Tag("github-action")

}

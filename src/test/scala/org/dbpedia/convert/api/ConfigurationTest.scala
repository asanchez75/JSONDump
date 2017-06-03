package org.dbpedia.convert.api

import org.junit.Assert.assertNotNull
import org.junit.Test

class ConfigurationTest {
  @Test def testConfiguration() {
    assertNotNull(Configuration.inputFile)
  }
}
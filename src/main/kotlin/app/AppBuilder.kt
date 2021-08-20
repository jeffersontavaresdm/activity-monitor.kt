package app

import util.loggerFor

class AppBuilder {

  private val logger = loggerFor()
  private val systemDataManager = SystemDataManager(Window("ACTIVITY MONITOR"))

  fun createApp() {
    systemDataReport()
    processReport()
  }

  private fun systemDataReport() {
    var count = 0
    Thread {
      while (true) {
        systemDataManager.reportSystemData()
        Thread.sleep(1000)
        logger.info("[SYSTEM DATA REPORTED] Times: {}", count + 1)
        count++
      }
    }.start()
  }

  private fun processReport() {
    var count = 0
    Thread {
      while (true) {
        systemDataManager.reportProcesses()
        Thread.sleep(5000)
        logger.info("[PROCESS DATA REPORTED] Times: {}", count + 1)
        count++
      }
    }.start()
  }
}
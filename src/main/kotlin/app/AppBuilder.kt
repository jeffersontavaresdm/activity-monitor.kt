package app

import util.loggerFor

class AppBuilder {

  private val logger = loggerFor()
  private val systemDataManager = ReportData(Window("ACTIVITY MONITOR"))

  init {
    systemDataReport()
    processReport()
  }

  private fun systemDataReport() {
    var count = 0
    Thread {
      while (true) {
        systemDataManager.reportSystemData()
        Thread.sleep(1000)
        count++
        logger.info("[SYSTEM DATA REPORTED] Times: {}", count)
      }
    }.start()
  }

  private fun processReport() {
    var count = 0
    Thread {
      while (true) {
        systemDataManager.reportProcesses()
        Thread.sleep(5000)
        count++
        logger.info("[PROCESS DATA REPORTED] Times: {}", count)
      }
    }.start()
  }
}
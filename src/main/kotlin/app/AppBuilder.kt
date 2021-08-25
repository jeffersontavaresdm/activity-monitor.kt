package app

import util.loggerFor

class AppBuilder {

  private val logger = loggerFor()
  private val systemDataManager = InvokingData(Window("ACTIVITY MONITOR"))

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
        println("\n[SYSTEM DATA REPORTED] Times: {$count}")
      }
    }.start()
  }

  private fun processReport() {
    var count = 0
    Thread {
      while (true) {
        systemDataManager.reportProcessData()
        Thread.sleep(3000)
        count++
        println("\n[PROCESS DATA REPORTED] Times: {$count}")
      }
    }.start()
  }
}
package app

import util.LocalShell
import util.loggerFor

class AppBuilder {

  private val logger = loggerFor()
  private val systemDataManager = InvokingData(Window())

  init {
    initMessage()
    systemDataReport()
    processReport()
  }

  private fun initMessage(): Unit = logger.info(LocalShell.executeCommand("neofetch"))

  private fun systemDataReport() {
    Thread {
      while (true) {
        systemDataManager.reportSystemData()
        Thread.sleep(1000)
      }
    }.start()
  }

  private fun processReport() {
    Thread {
      while (true) {
        systemDataManager.reportProcessData()
        Thread.sleep(3000)
      }
    }.start()
  }
}
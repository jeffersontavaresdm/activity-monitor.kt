package app

class AppBuilder {

  private val systemDataManager = InvokingData(Window("ACTIVITY MONITOR"))

  init {
    systemDataReport()
    processReport()
  }

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
        Thread.sleep(5000)
      }
    }.start()
  }
}
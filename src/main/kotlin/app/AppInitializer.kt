package app

class AppInitializer {
  init {
    val manager = DataManager()
    reportSystemInfos(manager)
    reportSystemProcesses(manager)
  }

  private fun reportSystemInfos(manager: DataManager) {
    Thread {
      while (true) {
        manager.reportSystemData()
        Thread.sleep(1000)
      }
    }.start()
  }

  private fun reportSystemProcesses(manager: DataManager) {
    Thread {
      while (true) {
        manager.reportProcessData()
        Thread.sleep(3000)
      }
    }.start()
  }
}
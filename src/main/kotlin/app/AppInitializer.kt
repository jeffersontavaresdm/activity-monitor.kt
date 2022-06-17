package app

class AppInitializer {
  init {
    Thread { reportSystemInfo() }.start()
    Thread { reportSystemProc() }.start()
  }

  private fun reportSystemInfo() {
    while (true) {
      SystemManager.printSystemInfo()
      Thread.sleep(1000)
    }
  }

  private fun reportSystemProc() {
    while (true) {
      SystemManager.printSystemProc()
      Thread.sleep(3000)
    }
  }
}
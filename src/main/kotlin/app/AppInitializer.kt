package app

import app.singleton.SystemManagerSingleton

class AppInitializer {
  init {
    Thread { reportSystemInfo() }.start()
    Thread { reportSystemProc() }.start()
  }

  private fun reportSystemInfo() {
    while (true) {
      SystemManagerSingleton.instance.printSystemInfo()
      Thread.sleep(1000)
    }
  }

  private fun reportSystemProc() {
    while (true) {
      SystemManagerSingleton.instance.printSystemProc()
      Thread.sleep(3000)
    }
  }
}
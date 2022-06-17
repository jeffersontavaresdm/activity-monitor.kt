package app

import app.singleton.WindowSingleton
import javax.swing.SwingUtilities

class SystemManager {

  fun printSystemInfo() {
    SwingUtilities.invokeLater { WindowSingleton.instance.updateSystemInfo() }
  }

  fun printSystemProc() {
    SwingUtilities.invokeLater { WindowSingleton.instance.updateSystemProc() }
  }
}
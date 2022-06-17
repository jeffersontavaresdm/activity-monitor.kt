package app

import javax.swing.SwingUtilities

object SystemManager {
  fun printSystemInfo() = SwingUtilities.invokeLater { Window.updateSystemInfo() }
  fun printSystemProc() = SwingUtilities.invokeLater { Window.updateSystemProc() }
}
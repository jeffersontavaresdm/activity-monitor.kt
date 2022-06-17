package app.singleton

import app.SystemManager

object SystemManagerSingleton {
  private var manager: SystemManager? = null

  val instance = manager ?: run { manager = SystemManager(); manager!! }
}
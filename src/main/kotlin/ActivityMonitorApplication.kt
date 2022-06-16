@file:Suppress("SuspiciousCallableReferenceInLambda", "UNUSED_EXPRESSION")

import app.AppInitializer
import javax.swing.SwingUtilities

fun main() {
  SwingUtilities.invokeLater(::AppInitializer)
}
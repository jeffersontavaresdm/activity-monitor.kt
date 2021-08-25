@file:Suppress("SuspiciousCallableReferenceInLambda", "UNUSED_EXPRESSION")

import app.AppBuilder
import javax.swing.SwingUtilities

fun main() {
  SwingUtilities.invokeLater(::AppBuilder)
}
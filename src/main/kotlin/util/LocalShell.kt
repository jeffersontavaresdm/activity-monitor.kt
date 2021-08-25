package util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Pattern

object LocalShell {

  fun getCommandResult(pattern: Pattern, command: String): String? {
    val value: String = executeCommand(command)

    val resultCommandOrNull: String = value
      .lines()
      .filter { line: String -> pattern.matcher(line).matches() }
      .map { line: String ->
        val matcher = pattern.matcher(line)
        matcher.find()
        matcher.group(1)
      }.firstOrNull().orEmpty()

    return resultCommandOrNull.ifEmpty { null }
  }

  fun filterDataProcess(allProcess: String, pattern: Pattern): String? {
    val resultCommandOrNull: String = allProcess
      .lines()
      .filter { line: String -> pattern.matcher(line).matches() }
      .map { line: String ->
        val matcher = pattern.matcher(line)
        matcher.find()
        matcher.group(1)
      }.firstOrNull().orEmpty()

    return resultCommandOrNull.ifEmpty { null }
  }

  @Throws(IOException::class)
  fun executeCommand(command: String): String {
    val commands = listOf("/bin/bash", "-c", command)
    val bufferedReader: BufferedReader?
    val builder = StringBuilder()

    val processBuilder = ProcessBuilder(commands)
    val process = processBuilder.start()
    val inputStream = process.inputStream
    val inputStreamReader = InputStreamReader(inputStream)
    bufferedReader = BufferedReader(inputStreamReader)

    var line: String?
    while (bufferedReader.readLine().also { readLine -> line = readLine } != null) {
      builder.append(line).append("\n")
    }

    bufferedReader.close()
    return builder.toString()
  }
}
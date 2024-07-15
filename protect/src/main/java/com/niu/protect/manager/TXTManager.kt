package com.niu.protect.manager

import android.os.Environment
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

object TXTManager {
    var rootXMLPath = Environment.getExternalStorageDirectory().path + "/testTXT"
    var SAVE_PIC_PATH = Environment.getExternalStorageDirectory().absolutePath
    var SAVE_REAL_PATH = SAVE_PIC_PATH + "/niuprotect/"
    fun writeToTxt(fileName: String, content: String?): Boolean {
        createDirectory(rootXMLPath)
        val file = File(rootXMLPath + "/" + fileName + ".txt")
        return try {
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            val bufferedWriter = BufferedWriter(OutputStreamWriter(fileOutputStream))
            bufferedWriter.write(content)
            bufferedWriter.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun addToTxt(fileName: String, content: String): Boolean {
        createDirectory(rootXMLPath)
        val file = File(rootXMLPath + "/" + fileName + ".txt")
        var out: BufferedWriter? = null
        return try {
            try {
                out = BufferedWriter(OutputStreamWriter(FileOutputStream(file, true)))
                out.write(
                    """
    $content
    
    """.trimIndent()
                )
                out.close()
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                true
            } catch (e2: Exception) {
                e2.printStackTrace()
                try {
                    out!!.close()
                } catch (e3: IOException) {
                    e3.printStackTrace()
                }
                false
            }
        } catch (th: Throwable) {
            try {
                out!!.close()
            } catch (e4: IOException) {
                e4.printStackTrace()
            }
            throw th
        }
    }

    fun readFromTxt(filePath: String?): String? {
        val stringBuilder = StringBuilder()
        val file = File(filePath)
        if (file.exists()) {
            try {
                val fileInputStream = FileInputStream(file)
                val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
                while (true) {
                    val line = bufferedReader.readLine() ?: break
                    stringBuilder.append(line)
                }
                bufferedReader.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            } catch (e2: IOException) {
                e2.printStackTrace()
                return null
            }
        }
        return stringBuilder.toString()
    }

    fun createDirectory(fileDirectory: String?) {
        val file = File(fileDirectory)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun writeTxt(fileName: String, content: String?): String? {
        val sdCardDir = File(SAVE_REAL_PATH)
        if (!sdCardDir.exists() && !sdCardDir.mkdirs()) {
            try {
                sdCardDir.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
                return e.message
            }
        }
        return try {
            val saveFile = File(sdCardDir, "$fileName.txt")
            if (!saveFile.exists()) {
                saveFile.createNewFile()
            }
            var bufferedWriter: BufferedWriter? = null
            try {
                val fileOutputStream = FileOutputStream(saveFile)
                bufferedWriter = BufferedWriter(OutputStreamWriter(fileOutputStream))
                bufferedWriter.write(content)
                bufferedWriter.close()
                null
            } catch (e2: IOException) {
                e2.printStackTrace()
                bufferedWriter!!.close()
                null
            }
        } catch (e3: Exception) {
            e3.printStackTrace()
            e3.message
        }
    }

    @JvmStatic
    fun writeTxtAdd(fileName: String, content: String): String? {
        val sdCardDir = File(SAVE_REAL_PATH)
        if (!sdCardDir.exists() && !sdCardDir.mkdirs()) {
            try {
                sdCardDir.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
                return e.message
            }
        }
        return try {
            val saveFile = File(sdCardDir, "$fileName.txt")
            if (!saveFile.exists()) {
                saveFile.createNewFile()
            }
            var out: BufferedWriter? = null
            try {
                out = BufferedWriter(OutputStreamWriter(FileOutputStream(saveFile, true)))
                out.write(
                    """
    $content
    
    """.trimIndent()
                )
                out.close()
                try {
                    out.close()
                    null
                } catch (e2: IOException) {
                    e2.printStackTrace()
                    e2.message
                }
            } catch (e3: Exception) {
                e3.printStackTrace()
                val message = e3.message
                try {
                    out!!.close()
                    message
                } catch (e4: IOException) {
                    e4.printStackTrace()
                    e4.message
                }
            }
        } catch (e5: Exception) {
            e5.printStackTrace()
            e5.message
        }
    }

    fun readTxt(filePath: String): String? {
        val file = File(SAVE_REAL_PATH + filePath + ".txt")
        val stringBuilder = StringBuilder()
        if (file.exists()) {
            try {
                val fileInputStream = FileInputStream(file)
                val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
                while (true) {
                    val line = bufferedReader.readLine() ?: break
                    stringBuilder.append(line)
                }
                bufferedReader.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            } catch (e2: IOException) {
                e2.printStackTrace()
                return null
            }
        }
        return stringBuilder.toString()
    }
}
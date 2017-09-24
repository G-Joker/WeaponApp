package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project

/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class ADBTask extends DefaultTask {

    def commandParams
    def resultCommand = []

    def getPackageName(def commandParams, def key) {
        def subString = getExecuteResult(commandParams, key)
        subString = subString.replaceAll("'", "")
        def index = subString.indexOf("versionCode")
        logger.quiet "quiet is : " + index
        logger.quiet "subSting is : " + subString.substring(0, index)

        return subString.substring(0, index).trim()
    }

    def getLaunchActivity(def commandParams, def key) {
        def subString = getExecuteResult(commandParams, key)
        subString = subString.replaceAll("'", "")
        subString = subString.replace("label=", "")
        subString = subString.replace("icon=", "")
        assert subString instanceof String
        return subString.trim()
    }

    def getExecuteResult(def command, def key) {
        def result = command.execute().text.readLines().find {
            it.startsWith("$key")
        }
        def sub = {
            it.split(key)[1]
        }
        return sub(result)
    }

    def static getAapt(Project project) {
        Properties properties = new Properties()
        properties.load(project.rootProject.file('local.properties').newDataInputStream())
        def sdkPath = properties.getProperty('sdk.dir')
        println "sdk path is : $sdkPath"
        def buildToolsScr = sdkPath + "/build-tools/"

        def aaptDirectory = ""
        project.file(buildToolsScr).eachDir {
            aaptDirectory = it.toString()
        }

        return aaptDirectory + "/aapt"
    }

}
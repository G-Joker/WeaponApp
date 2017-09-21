import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

public class AaptTask extends ADBTask {

    def resultCommand = []

    @TaskAction
    public void excute() {

        if (resultCommand instanceof List) {
            resultCommand.removeAll {
                it < 0
            }
        }

        def buildToolsScr = getAapt(project)
        def aaptDirectory = ""
        project.file(buildToolsScr).eachDir {
            aaptDirectory = it.toString()
        }

        def aapt = aaptDirectory + '/aapt'
        logger.quiet "aapt is : $aapt"
        resultCommand.add(0, aapt)
        resultCommand.addAll(commandParams)
        project.android.applicationVariants.all { variant ->
            if (variant.name == "debug") {
                def apkPath = variant.outputs[0].outputFile
                resultCommand.add(apkPath)
                def launchActivity = getLaunchActivity(resultCommand, 'launchable-activity: name=')
                def packageName = getPackageName(resultCommand, 'package: name=')
                logger.quiet "launchActivity is : $launchActivity"
                logger.quiet "resultParams is : $resultCommand"
                logger.quiet "packageName is $packageName"

                def start = ['adb', 'shell', 'am', 'start', '-n', "$packageName/$launchActivity"]
                start.execute()
            }
        }
    }

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
        return sdkPath + "/build-tools/"
    }
}
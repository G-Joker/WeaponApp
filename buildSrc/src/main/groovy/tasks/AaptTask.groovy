import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

public class AaptTask extends ADBTask {

    @TaskAction
    public void excute() {
        def buildToolsScr = getAapt(project)
        def aaptDirectory = ""
        project.file(buildToolsScr).eachDir {
            aaptDirectory = it.toString()
        }
        assert aaptDirectory instanceof String
        def aapt = aaptDirectory + '/aapt'
        logger.quiet "aapt is : $aapt"
        project.android.applicationVariants.all { variant ->
            if (variant.name == "debug") {
                def apkPath = variant.outputs[0].outputFile
                logger.quiet "apk path is $apkPath"
                commandParams.add(0,aapt)
                commandParams.add(apkPath)
                logger.quiet "commandParams is : $commandParams"
                ADBCommand.execute(commandParams)
            }
        }
    }

    def static getAapt(Project project) {
        Properties properties = new Properties()
        properties.load(project.rootProject.file('local.properties').newDataInputStream())
        def sdkPath = properties.getProperty('sdk.dir')
        println "sdk path is : $sdkPath"
        return sdkPath + "/build-tools/"
    }
}
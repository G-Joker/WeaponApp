import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

public class AaptTask extends ADBTask {

    @TaskAction
    public void excute() {

        if (resultCommand instanceof List) {
            resultCommand.removeAll {
                it < 0
            }
        }

        def aapt = getAapt(project)
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
}
import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

/**
 * author: yueyang
 * date: 2017.9.22
 * e-mail: hi.yangyue1993@gmail.com
 */
public class UninstallTask extends ADBTask {

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
            if (variant.name == 'debug') {
                def apkPath = variant.outputs[0].outputFile
                resultCommand.add(apkPath)
                def packageName = getPackageName(resultCommand, 'package: name=')
                def start = ['adb', 'uninstall', "$packageName"]
                logger.quiet "packageName is $packageName"
                logger.quiet "start command is : $start"
                start.execute()
            }
        }
    }
}
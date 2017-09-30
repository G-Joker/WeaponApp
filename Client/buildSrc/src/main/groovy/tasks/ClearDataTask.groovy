import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

/**
 * 清除当前应用的缓存
 * author : yueyang
 * e-mail: hi.yangyue1993@gmail.com
 */
public class ClearDataTask extends ADBTask {

    @TaskAction
    public void excute() {

        if (resultCommand instanceof List) {
            resultCommand.removeAll {
                it < 0
            }
        }

        def aapt = getAapt(project)
        resultCommand.add(0,aapt)
        resultCommand.addAll(ADBCommand.AAPT_INFO)
        project.android.applicationVariants.all { variant ->
            if (variant.name == 'debug') {
                def apkPath = variant.outputs[0].outputFile
                resultCommand.add(apkPath)
                def packageName = getPackageName(resultCommand,'package: name=')
                commandParams.add(packageName)
                logger.quiet("start is $commandParams")
                commandParams.execute()
            }
        }
    }
}
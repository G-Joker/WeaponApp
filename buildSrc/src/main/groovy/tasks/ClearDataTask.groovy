import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

public class ClearDataTask extends ADBTask {

    def resultCommand = []

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
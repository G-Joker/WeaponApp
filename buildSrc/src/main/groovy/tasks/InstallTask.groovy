package tasks

import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class InstallTask extends ADBTask {

    def commandParams
    def apkPath
    Project commandProject

    @TaskAction
    public void excute() {

        commandProject.android.applicationVariants.all { variant ->
            println "*** start install Task***"
            println variant.outputs[0].outputFile
            apkPath = variant.outputs[0].outputFile
            if (apkPath != null) {
                ADBCommand.execute(commandParams + apkPath)
            }
        }
    }

}
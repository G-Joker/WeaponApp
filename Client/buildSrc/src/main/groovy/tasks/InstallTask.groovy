package tasks

import org.gradle.api.tasks.TaskAction
/**
 * 安装当前的应用
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class InstallTask extends ADBTask {

    def apkPath

    /**
     * 安装的原理是 adb install -r + apkpath
     * variant中可以读取到apk路径等相关的属性
     * author : yueyang
     * e-mail: hi.yangyue1993@gmail.com
     */
    @TaskAction
    public void excute() {
        project.android.applicationVariants.all { variant ->
            println "*** start install Task***"
            println variant.outputs[0].outputFile
            apkPath = variant.outputs[0].outputFile
            if (apkPath != null) {
                println "adb execute is : " + commandParams + apkPath
                ADBCommand.execute(commandParams + apkPath)
            }
        }
    }

}
import extensions.ADBExtensions
import extensions.FileOutputExtensions
import groovy.text.SimpleTemplateEngine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
import tasks.InstallTask

/**
 * author: yueyang
 * date: 2017.9.14
 * e-mail: hi.yangyue1993@gmail.com
 */
public class JokerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // 判断当前项目中是否使用了Android Gradle Plugin
        def androidGradlePlugin = getAndroidGradlePluginVersion(project)
        if (androidGradlePlugin == null) throw new StopExecutionException('Joker Plugin depends on androidGradlePlugin')
        // 关联外部引用的插件配置
        def advancedOutputOption = project.extensions.create('advancedOutput', FileOutputExtensions, project)

        project.afterEvaluate {
            println 'status : project.afterEvaluate'
            println "project name is $project.name"
            project.android.applicationVariants.all {
                generateOutputFile(advancedOutputOption, it)
            }
        }

        ADBExtensions adbExtensions = project.extensions.create('command', ADBExtensions, project)
        adbExtensions.createADBTask('install', InstallTask, ADBCommand.ADB_INSTALL)
        adbExtensions.createADBTask('version', CommonADBTask, ADBCommand.ADB_VERSION)
        adbExtensions.createADBTask('devices', CommonADBTask, ADBCommand.ADB_DEVICES)
        adbExtensions.createADBTask('launch', LaunchTask, ADBCommand.AAPT_INFO)
        adbExtensions.createADBTask('uninstall', UninstallTask, ADBCommand.AAPT_INFO)
        adbExtensions.createADBTask('clearData', ClearDataTask, ADBCommand.ADB_CLEAR)
    }

    /**
     * 更新输出的文件的名称
     * @param fileOutputOption 外部引用这个插件的设置
     * @param variant 构建变种(可以是debug、release等等)
     * @return
     */
    def static generateOutputFile(fileOutputOption, variant) {
        println("projec version is $variant.versionCode,\nname is $variant.versionName")
        println "advancedOutput name is $fileOutputOption.fileOutputName"
        def generateTemplate = new SimpleTemplateEngine();
        def templateMap = [
                'versionName': variant.versionName,
                'versionCode': variant.versionCode,
                'customName' : fileOutputOption.fileOutputName
        ]

        String placeTemplate = '$customName-$versionCode-$versionName'
        String fileOutputName = generateTemplate.createTemplate(placeTemplate).make(templateMap);
        println "fileOutputName is $fileOutputName"
        def file = variant.outputs[0].packageApplication.outputFile
        variant.outputs[0].packageApplication.outputFile =
                new File(file.parent, fileOutputName + "-unaligned.apk")
    }

    /**
     * 获取到根目录中的com.android.tools.build:gradle插件
     * author : yueyang
     * e-mail: hi.yangyue1993@gmail.com
     */
    def static getAndroidGradlePluginVersion(Project project) {
        if (project == null) throw IllegalArgumentException("project cannot be null")
        return project.rootProject.buildscript.configurations.classpath.dependencies.find {
            it.group != null && it.group == 'com.android.tools.build' && it.name == 'gradle'
        }
    }
}
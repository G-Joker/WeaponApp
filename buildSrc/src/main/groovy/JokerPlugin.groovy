import groovy.text.SimpleTemplateEngine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
/**
 * author: yueyang
 * date: 2017.9.14
 * e-mail: hi.yangyue1993@gmail.com
 */
public class JokerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def androidGradlePlugin = getAndroidGradlePluginVersion(project)
        if (androidGradlePlugin == null) throw new StopExecutionException('Joker Plugin depends on androidGradlePlugin')
        def advancedOutputOption = project.extensions.create('advancedOutput',FileOutputExtensions,project)
        project.afterEvaluate {
            println 'status : project.afterEvaluate'
            println "project name is $project.name"
            project.android.applicationVariants.all {
                generateOutputFile(advancedOutputOption,it)
            }
        }

        ADBExtensions adbExtensions = project.extensions.create('command',ADBExtensions,project)
        adbExtensions.createADBTask(project,'devices',ADBCommand.ADB_DEVICES)
        adbExtensions.createADBTask(project,'version',ADBCommand.ADB_VERSION)
    }

    def static generateOutputFile(fileOutputOption,variant) {
        println("projec version is $variant.versionCode,\nname is $variant.versionName")
        println "advancedOutput name is $fileOutputOption.fileOutputName"
        def generateTemplate = new SimpleTemplateEngine();
        def templateMap = [
                'versionName' : variant.versionName,
                'versionCode' : variant.versionCode,
                'customName' : fileOutputOption.fileOutputName
        ]

        String placeTemplate = '$customName-$versionCode-$versionName'
        String fileOutputName = generateTemplate.createTemplate(placeTemplate).make(templateMap);
        println "fileOutputName is $fileOutputName"
        def file = variant.outputs[0].packageApplication.outputFile
        variant.outputs[0].packageApplication.outputFile =
                new File(file.parent, fileOutputName + "-unaligned.apk")
    }

    def static getAndroidGradlePluginVersion(Project project) {
        if (project == null) throw IllegalArgumentException("project cannot be null")
        return project.rootProject.buildscript.configurations.classpath.dependencies.find {
            it.group != null && it.group == 'com.android.tools.build' && it.name == 'gradle'
        }
    }
}
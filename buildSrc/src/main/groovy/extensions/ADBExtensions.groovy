package extensions

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import tasks.ADBTask
/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class ADBExtensions {
    Project project

    ADBExtensions(Project project) {
        this.project = project
    }

    def createADBTask(def taskName, Class<? extends ADBTask> type, def params) {
        createADBTask(taskName, type, params, null)
    }

    def createADBTask(def taskName, Class<? extends ADBTask> type, def params, Class<? extends DefaultTask> dependsOn) {
        project.task(taskName, type: type, dependsOn: dependsOn) {
            commandParams = params
        }
    }
}
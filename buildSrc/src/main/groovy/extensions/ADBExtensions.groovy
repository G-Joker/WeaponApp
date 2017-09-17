package extensions

import org.gradle.api.Project
import org.gradle.api.tasks.StopExecutionException
import tasks.ADBTask

/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class ADBExtensions {

    private Project project

    public ADBExtensions(Project project) {
        this.project = project
    }

    def createADBTask(def taskName, Class<? extends ADBTask> type, def params) {
        if (project == null) throw new StopExecutionException('project cannot be null')
        project.task(taskName, type: type) {
            commandParams = params
            commandProject = project
        }
    }
}
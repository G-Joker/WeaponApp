import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.StopExecutionException

public class ADBExtensions {

    private Project project

    public ADBExtensions(Project project) {
        this.project = project
    }

    def createADBTask(Project project, taskName, executeCommand) {
        if (project == null) throw new StopExecutionException('project cannot be null')
        project.task(taskName, type: Exec) {
            commandLine executeCommand
        }
    }
}
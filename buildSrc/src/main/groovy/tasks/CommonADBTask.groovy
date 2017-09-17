import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import tasks.ADBTask

/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class CommonADBTask extends ADBTask {

    def commandParams
    Project commandProject

    @TaskAction
    public void excute() {
        ADBCommand.execute(commandParams)
    }
}
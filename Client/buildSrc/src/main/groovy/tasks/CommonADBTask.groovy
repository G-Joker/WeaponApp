import org.gradle.api.tasks.TaskAction
import tasks.ADBTask
/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class CommonADBTask extends ADBTask {

    @TaskAction
    public void excute() {
        println "commonADBTask **********************************"
        ADBCommand.execute(commandParams)
    }
}
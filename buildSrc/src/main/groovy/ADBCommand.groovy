import org.gradle.api.Project

/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class ADBCommand {
    def static ADB_VERSION = ['adb', 'version']
    def static ADB_DEVICES = ['adb', 'devices']
    def static ADB_INSTALL = ['adb', 'install', '-r']
    def static ADB_CLEAR = ['adb', 'shell', 'pm', 'clear']
    def static AAPT_INFO = ['dump', 'badging']

    def static execute(def command) {
        command.execute().text.eachLine { line ->
            println line
        }
    }

    def static getAdb(Project project) {
        Properties properties = new Properties()
        properties.load(project.rootProject.file('local.properties').newDataInputStream())
        def adbPath = properties.getProperty('sdk.dir')
        return adbPath + "/platform-tools/adb"
    }
}
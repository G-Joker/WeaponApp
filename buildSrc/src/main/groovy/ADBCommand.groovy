/**
 * author: yueyang
 * date: 2017.9.17
 * e-mail: hi.yangyue1993@gmail.com
 */
public class ADBCommand {
    def static ADB_VERSION = ["adb", 'version']
    def static ADB_DEVICES = ['adb', 'devices']
    def static ADB_INSTALL = ['adb', 'install', '-r']

    def static execute(def command) {
        command.execute().text.eachLine { line ->
            println line
        }
    }
}
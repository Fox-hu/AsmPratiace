import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @Author Silver-Fox
 * @Date 2023/3/18 12:38
 */
class AsmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("AsmPlugin init ")
        val android = project.extensions.getByType(AppExtension::class.java)
        android.registerTransform(AsmTransform())
    }
}
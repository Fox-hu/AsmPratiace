import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils


/**
 * @Author fox
 * @Date 2023/3/28 11:32
 */
class AsmTransform: Transform() {
    override fun getName(): String {
        return "AsmTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean = false

    override fun transform(transformInvocation: TransformInvocation) {
        println("AsmTransform init ")
        super.transform(transformInvocation)
        transformInvocation.inputs.forEach {
            //处理文件夹类型
            it.directoryInputs.forEach { directoryInput ->
                val destDir = transformInvocation.outputProvider.getContentLocation(
                    directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY
                )
                //将文件夹类型的文件拷贝到目标目录
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }
            //处理jar类型
            it.jarInputs.forEach { jarInput ->
                val dest = transformInvocation.outputProvider.getContentLocation(
                    jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
                )
                //将jar类型的文件拷贝到目标目录
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

//        val fos = FileOutputStream(mappingJarFile)
//        val jarOutputStream = JarOutputStream(fos)
//        val zipEntry = ZipEntry(AsmByteCodeBuilder.CLASS_NAME + ".class")
//        jarOutputStream.putNextEntry(zipEntry)
////        jarOutputStream.write()
//        jarOutputStream.closeEntry()
//        jarOutputStream.close()
//        fos.close()
    }
}
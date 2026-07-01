package com.eowise.imagemagick.tasks
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

/**
 * Created by aurel on 14/12/13.
 */
class SvgToPng extends DefaultTask {
    @Incremental
    @InputFiles
    FileTree inputFiles

    @OutputDirectory
    File outputDir

    def SvgToPng() {
    }
    
    def files(FileTree inputFiles) {
        this.inputFiles = inputFiles
    }

    def into(String outputDir) {
        this.outputDir = project.file(outputDir)
    }

    @TaskAction
    void execute(InputChanges inputChanges) {
        String outputFile
        inputChanges.getFileChanges(inputFiles).each { change ->
            outputFile = outputDir.toString() + '/' + change.file.name.replace(".svg", ".png")
            project.exec {
                commandLine 'inkscape', '--export-png=' + outputFile, '--export-background-opacity=0', '--without-gui', change.file
            }
        }
    }
}

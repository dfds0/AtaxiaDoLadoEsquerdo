package runnable

import reader.GroovyReader
import runnable.common.AbstractRunner
import writer.FactoryWriter
import writer.PrintClassStatement
import writer.common.AbstractWriter

class RBS extends AbstractRunner {

    public void run() {

        abstractReader = new GroovyReader()

        File folder = new File("")

        explore(folder)
        folder.listFiles().each {
            explore(it)
        }

        abstractReader.classStatements.each {
//            PrintClassStatement.print(it)
        }

        abstractReader.classStatements.each {
            AbstractWriter.RGPL.put(it.name, it)
        }

        FactoryWriter factoryWriter = new FactoryWriter()
        abstractReader.classStatements.each {
            println factoryWriter.writeBuildFunction(it)

            println ''

            println factoryWriter.writeCreateFunction(it)
        }

    }

}
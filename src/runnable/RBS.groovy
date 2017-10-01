package runnable

import reader.GroovyReader
import runnable.common.AbstractRunner

class RBS extends AbstractRunner {

    public void run() {

        abstractReader = new GroovyReader()

        File folder = new File("")

        explore(folder)
        folder.listFiles().each {
            explore(it)
        }

    }

}
package runnable.common

import reader.GroovyReader

abstract class AbstractRunner implements Runnable {

    protected GroovyReader abstractReader

    protected void explore(File file) {

        if (file.isDirectory()) {
            file.listFiles().each {
                explore(it)
            }

        } else {
            if (file.name.contains('.groovy')) {
                abstractReader.read(file)
            }
        }

    }

}

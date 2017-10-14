package runnable.common

import reader.GroovyReader

abstract class AbstractRunner implements Runnable {

    protected GroovyReader abstractReader
    Set readerFiles = []

    protected void explore(File file) {

        if (file.isDirectory()) {
            file.listFiles().each {
                explore(it)
            }

        } else {

            if (file.name.contains('.groovy') && !readerFiles.contains(file.name) && file.exists()) {
                try {
                    abstractReader.read(file)
                    readerFiles.add(file.name)

                } catch (Exception e) {
                    e.printStackTrace()
                    println '---------------------------------' + file.name
                }

            }
        }

    }

}

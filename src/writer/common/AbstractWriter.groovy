package writer.common

import statement.ClassStatement

/**
 * Created by dfds on 3/23/17.
 */
class AbstractWriter {

    /**
     * Stand - Class Library
     */
    public static Map<String, ClassStatement> RGPL = [:]

    protected StringBuilder builder

    protected void riotBuilder() {
        builder = new StringBuilder()
    }

    protected Closure append = { String text ->
        builder.append(text)
        builder.append('\n')
    }

}

package writer.common

/**
 * Created by dfds on 3/23/17.
 */
class AbstractWriter {

    protected StringBuilder builder

    protected void riotBuilder() {
        builder = new StringBuilder()
    }

    protected Closure append = { String text ->
        builder.append(text)
        builder.append('\n')
    }

}

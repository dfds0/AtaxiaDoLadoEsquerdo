package statement

/**
 * Created by dfds on 9/30/17.
 */
class FunctionStatement {

    String name
    String type
    String visibility
    boolean isAbstract
    boolean isStatic

    List<ArgumentStatement> arguments

    public FunctionStatement() {
        this.arguments = []
    }
}

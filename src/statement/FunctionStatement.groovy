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
    boolean isDefault
    boolean isFinal

    List<ArgumentStatement> arguments

    List<String> exceptions

    public FunctionStatement() {
        this.arguments = []
        this.exceptions = []
    }
}

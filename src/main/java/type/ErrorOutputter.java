package type;

import java.util.function.Consumer;

/**
 * The interface Error outputter. *
 * Is exactly the same as a Consumer type String, but aliased to a more descriptive name.
 */
public interface ErrorOutputter extends Consumer<String> {

}

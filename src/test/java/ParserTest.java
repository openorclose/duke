import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest{
    @Test
    public void parseTodoTest(){
        assertEquals(Parser.returnArgs("todo my CS2103 work")[1], "my CS2103 work");
    }
    @Test
    public void parseBye() {
        assertEquals(Parser.returnArgs("bye").length, 1);
    }

    @Test
    public void parseDeadline(){
        assertEquals(Parser.returnArgs("deadline do work /by tommorrow")[2], "tommorrow");
    }
}
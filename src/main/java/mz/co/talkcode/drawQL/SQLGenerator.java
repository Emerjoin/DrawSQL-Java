package mz.co.talkcode.drawQL;
/**
 * Represents an INSERT SQL statements generator.
 * @author Mário Júnior
 */
public interface SQLGenerator extends DrawQLComponent {

    /**
     * Generates an SQL INSERT statement for each row found in a table.
     * The sql statements will be each on a new line and will end by a semi-colon.
     * @param tableInfo - information about the table containing the rows to be converted into SQ INSERT Statements.
     * @return a string containing multiple SQL Statements, each ending by a semi-colon and a new line.
     * @throws SQLGenerationException - an error occurred while generating the INSERT sql statements
     */
    public String generateInserts(TableInfo tableInfo) throws SQLGenerationException;


    /**
     * Will generate a single INSERT SQL statement without a semi-colon at the end.
     * @param rowData thr row to generate the SQL statement for
     * @return a single INSERT SQL statement without a semi-colon at the end.
     * @throws SQLGenerationException - an error occurred while generating the INSERT sql statement.
     */
    public String generateInsert(TableInfo.DataRow rowData) throws SQLGenerationException;

}

package mz.co.talkcode.drawQL;

import java.util.Scanner;

/**
 * Represents a sketches parser. Reads a sketch and turns it into a {@link TableInfo} array.
 * @author Mário Júnior
 */
public interface Parser extends DrawQLComponent {

    /**
     * Finds information about tables in a sketch : this method should only
     * find out the table names, their boundaries (lines) and get the sub-sketch that represents them.
     * To set the table information, this method uses {@link TableInfo#setName(String)}, {@link TableInfo#setStartLine(int)},
     * {@link TableInfo#setEndLine(int)}, and {@link TableInfo#setSubSketch(String)}.
     * The {@link #readColumns(TableInfo)} and {@link #readColumns(TableInfo)} methods
     * depend heavily on this one, as their work is mostly based on the {@link TableInfo#getSubSketch()}.
     * @see {@code TableInfo}, {@link #readRows(TableInfo)}, {@link #readColumns(TableInfo)}.
     * @param document a {@link Scanner} instance representing the sketch to be parsed.
     * @return an array of {@link TableInfo} instances
     * @throws ParseException - if the sketch is invalid or if something else went wrong while parsing the sketch
     */
    public TableInfo[] scan(Scanner document) throws ParseException;


    /**
     * Parses each row in a table and generates a {@link mz.co.talkcode.drawQL.TableInfo.DataRow} instance
     * for each parsed row. This method acts on a sub-sketch previously set to the {@link TableInfo}
     * along with its  {@link mz.co.talkcode.drawQL.TableInfo.ColumnInfo} instances.
     * This method only acts after the {@link #readColumns(TableInfo)}.
     * @param tableInfo information about the table containing the rows
     * @throws ParseException - if the sketch is missformated or something else went wrong during the rows parsing process.
     */
    public void readRows(TableInfo tableInfo) throws ParseException;


    /**
     * Figures out what are the columns declared for a table.
     * Creates a {@link mz.co.talkcode.drawQL.TableInfo.ColumnInfo} instance for
     * each declared column and adds to the
     * @param tableInfo information about the table containing the rows
     * @throws ParseException - if the sketch is missformated or something else went wrong during the columns parsing process.
     */
    public void readColumns(TableInfo tableInfo) throws ParseException;


}

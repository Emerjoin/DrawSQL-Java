import mz.co.talkcode.drawSQL.DrawSQL;
import mz.co.talkcode.drawSQL.DrawSQLException;
import mz.co.talkcode.drawSQL.TableInfo;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

/**
 * @author Mário Júnior
 */
public class DrawSQLTest {

    @Test
    public void buildTest1(){

        try {

            DrawSQL.Builder builder = new DrawSQL.Builder();
            DrawSQL drawSQL = builder.fromSketch(new File("test/sketch1.dql")).build();
            assertEquals(1, drawSQL.getTableInfos().length);


            TableInfo tableInfo = drawSQL.getTableInfos()[0];
            TableInfo.DataRow[] rows = tableInfo.getRows();
            assertEquals("person",tableInfo.getName());
            assertEquals(2,rows.length);
            assertEquals(2,tableInfo.totalColumns());
            TableInfo.ColumnInfo col1 = tableInfo.getColumns()[0];
            assertEquals("id",col1.getName());
            TableInfo.ColumnInfo col2 = tableInfo.getColumns()[1];
            assertEquals("name",col2.getName());

            TableInfo.DataRow row1 = rows[0];
            assertEquals(1,row1.getInt("id"));
            assertEquals("Mario",row1.getString("name"));

            TableInfo.DataRow row2 = rows[1];
            assertEquals(2,row2.getInt("id"));
            assertEquals("Romildo",row2.getString("name"));

        }catch (DrawSQLException ex){

            ex.printStackTrace();
            fail();

        }catch (IOException ex){

            fail("Test sketch file error : "+ex.getMessage());
            ex.printStackTrace();

        }

    }

    @Test
    public void buildTest2(){

        try {

            DrawSQL.Builder builder = new DrawSQL.Builder();
            DrawSQL drawSQL = builder.fromSketch(new File("test/sketch2.dql")).build();
            assertEquals(1, drawSQL.getTableInfos().length);

            TableInfo tableInfo = drawSQL.getTableInfos()[0];
            TableInfo.DataRow[] rows = tableInfo.getRows();
            assertEquals("person",tableInfo.getName());
            assertEquals(2,rows.length);
            assertEquals(2,tableInfo.totalColumns());
            TableInfo.ColumnInfo col1 = tableInfo.getColumns()[0];
            assertEquals("id",col1.getName());
            TableInfo.ColumnInfo col2 = tableInfo.getColumns()[1];
            assertEquals("name",col2.getName());

            TableInfo.DataRow row1 = rows[0];
            assertEquals(1,row1.getInt("id"));
            assertEquals("Mario Junior",row1.getString("name"));

            TableInfo.DataRow row2 = rows[1];
            assertEquals(2,row2.getInt("id"));
            assertEquals("Romildo",row2.getString("name"));

        }catch (DrawSQLException ex){

            ex.printStackTrace();
            fail();

        }catch (IOException ex){

            fail("Test sketch file error : "+ex.getMessage());
            ex.printStackTrace();

        }

    }


    @Test
    public void buildTest3(){

        try {

            DrawSQL.Builder builder = new DrawSQL.Builder();
            DrawSQL drawSQL = builder.fromSketch(new File("test/sketch3.dql")).enableLogging().build();
            assertEquals(3, drawSQL.getTableInfos().length);

            TableInfo table1 = drawSQL.getTableInfos()[0];
            assertEquals("person",table1.getName());
            assertEquals(2,table1.totalRows());
            assertEquals(2,table1.totalColumns());
            TableInfo.ColumnInfo table1Col1 = table1.getColumns()[0];
            assertEquals("id",table1Col1.getName());
            TableInfo.ColumnInfo table1Col2 = table1.getColumns()[1];
            assertEquals("name",table1Col2.getName());
            TableInfo.DataRow[] table1Rows = table1.getRows();
            TableInfo.DataRow row1 = table1Rows[0];
            assertEquals(1,row1.getInt("id"));
            assertEquals("Mario Junior",row1.getString("name"));
            TableInfo.DataRow row2 = table1Rows[1];
            assertEquals(2,row2.getInt("id"));
            assertEquals("Romildo",row2.getString("name"));

            TableInfo table2 = drawSQL.getTableInfos()[1];
            assertEquals("group",table2.getName());
            assertEquals(2,table2.totalRows());
            TableInfo.ColumnInfo table2Col1 = table2.getColumns()[0];
            assertEquals("id",table2Col1.getName());
            TableInfo.ColumnInfo table2Col2 = table2.getColumns()[1];
            assertEquals("name",table2Col2.getName());
            TableInfo.DataRow table2Row1 = table2.getRows()[0];
            assertEquals(1,table2Row1.getInt("id"));
            assertEquals("Programmers",table2Row1.getString("name"));
            TableInfo.DataRow table2Row2 = table2.getRows()[1];
            assertEquals(2,table2Row2.getInt("id"));
            assertEquals("Developers",table2Row2.getString("name"));

            TableInfo table3 = drawSQL.getTableInfos()[2];
            assertEquals("person_group",table3.getName());
            assertEquals(3,table3.totalColumns());
            assertEquals(2,table3.totalRows());
            TableInfo.DataRow table3Row1 = table3.getRows()[0];
            assertEquals(1,table3Row1.getInt("id"));
            assertEquals(1,table3Row1.getInt("group_id"));
            assertEquals(1,table3Row1.getInt("person_id"));

            TableInfo.DataRow table3Row2 = table3.getRows()[1];
            assertEquals(2,table3Row2.getInt("id"));
            assertEquals(1,table3Row2.getInt("group_id"));
            assertEquals(2,table3Row2.getInt("person_id"));

        }catch (DrawSQLException ex){

            fail();

        }catch (IOException ex){

            fail("Test sketch file erro : "+ex.getMessage());
            ex.printStackTrace();

        }

    }

}

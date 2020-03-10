import mz.co.talkcode.drawSQL.DrawSQL;
import mz.co.talkcode.drawSQL.DrawSQLException;
import mz.co.talkcode.drawSQL.TableInfo;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Americo.Chaquisse
 */
public class DrawSQLSemicolonTest {

    @Test
    public void buildTest2(){

        try {

            DrawSQL.Builder builder = new DrawSQL.Builder();
            DrawSQL drawSQL = builder.fromSketch(new File("test/sketch_semicolon.dql")).build();
            assertEquals(1, drawSQL.getTableInfos().length);

            TableInfo tableInfo = drawSQL.getTableInfos()[0];
            TableInfo.DataRow[] rows = tableInfo.getRows();
            assertEquals("Email",tableInfo.getName());
            assertEquals(2,rows.length);
            assertEquals(4,tableInfo.totalColumns());

            TableInfo.ColumnInfo col1 = tableInfo.getColumns()[0];
            assertEquals("id",col1.getName());
            TableInfo.ColumnInfo col2 = tableInfo.getColumns()[1];
            assertEquals("from",col2.getName());
            TableInfo.ColumnInfo col3 = tableInfo.getColumns()[2];
            assertEquals("to",col3.getName());

            TableInfo.DataRow row1 = rows[0];
            assertEquals(1,row1.getInt("id"));
            assertEquals("user@domain.com",row1.getString("from"));
            assertEquals("user11@domail.com;user2@domain.com",row1.getString("to"));
            assertEquals("Subject 1",row1.getString("subject"));

            TableInfo.DataRow row2 = rows[1];
            assertEquals(2,row2.getInt("id"));
            assertEquals("uzer@domain.com",row2.getString("from"));
            assertEquals("user11@domail.com;user2@domain.com;user3@domain.com; ",row2.getString("to"));
            assertEquals("Subject 2",row2.getString("subject"));

        }catch (DrawSQLException ex){

            ex.printStackTrace();
            fail();

        }catch (IOException ex){

            fail("Test sketch file error : "+ex.getMessage());
            ex.printStackTrace();

        }

    }

}

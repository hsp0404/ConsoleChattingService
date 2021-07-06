import java.sql.*;

public class Select extends DBConnect{
    public Select(String sql) {
        super(sql);
    }

    @Override
    public void Result() throws Exception {
        super.Connect();
        super.Execute();
        try {
            int columnCnt = rsmd.getColumnCount();
            for(int i=1; i<=columnCnt;i++){
                System.out.print(rsmd.getColumnName(i)+"    ");
            }
            System.out.println();
            while(rs.next()){
                String[] value = new String[columnCnt];
                for(int i=0; i<columnCnt; i++){
                    value[i] = rs.getString(rsmd.getColumnName(i+1));
                    System.out.print("      "+value[i]+"       ");
                }
                System.out.println();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

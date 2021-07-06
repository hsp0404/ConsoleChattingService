import java.sql.SQLException;

public class Insert extends DBConnect{
    @Override
    public void Execute() throws Exception {
        try {
            int r = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception();
        }
    }

    public Insert(String sql) {
        super(sql);
    }
}
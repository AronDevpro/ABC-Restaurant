package dbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        if(sql.startsWith("SELECT")){
            return (T) stmt.executeQuery();
        }
        return (T)(Boolean)(stmt.executeUpdate()>0);
    }
}

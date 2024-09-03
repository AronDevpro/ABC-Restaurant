package dbConnection;

import java.sql.*;

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

    public static Object[] executeStoredProcedure(String sql, Object... params) throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStmt = connection.prepareCall(sql)) {

            // Set input parameters
            int paramIndex = 1;
            for (Object param : params) {
                if (param instanceof OutputParam) {
                    OutputParam outputParam = (OutputParam) param;
                    callableStmt.registerOutParameter(paramIndex, outputParam.getSqlType());
                } else {
                    callableStmt.setObject(paramIndex, param);
                }
                paramIndex++;
            }

            // Execute stored procedure
            boolean hasResultSet = callableStmt.execute();
            Object[] result = new Object[params.length];

            // Retrieve output parameters
            paramIndex = 1;
            for (Object param : params) {
                if (param instanceof OutputParam) {
                    result[paramIndex - 1] = callableStmt.getObject(paramIndex);
                }
                paramIndex++;
            }

            // Handle result sets (if any)
            if (hasResultSet) {
                try (ResultSet rs = callableStmt.getResultSet()) {
                    // Assuming there's only one result set; if there are multiple, you need to handle them accordingly
                    result[0] = rs;
                }
            } else {
                // If there are no result sets, we might want to check for update counts or other outcomes
                int updateCount = callableStmt.getUpdateCount();
                if (updateCount != -1) {
                    result[0] = updateCount;
                }
            }

            return result;
        } catch (SQLException e) {
            // Log the exception and handle as necessary
            e.printStackTrace();
            throw e;
        }
    }


}

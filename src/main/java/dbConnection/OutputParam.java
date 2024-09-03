package dbConnection;

public class OutputParam {
    private final int sqlType;

    public OutputParam(int sqlType) {
        this.sqlType = sqlType;
    }

    public int getSqlType() {
        return sqlType;
    }
}

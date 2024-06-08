package repository.jdbc;

public enum DBMS {
    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/"),
    CUSTOM("","");
    private String driverClass;
    private String url;

    DBMS(String driverClass, String urlPrefix) {
        this.driverClass = driverClass;
        this.url = urlPrefix;
    }
    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

# File System Search
A simple Java Servlet web application that able to search files and folders in the file system.


Before starting the web application DataSource should be configured in the context.xml with a resource as shown below.
```
<Resource name="connpool" auth="Container" type="javax.sql.DataSource"
        username="" password="" driverClassName="org.postgresql.Driver"
        url="jdbc:postgresql://{host}:{port}/{db_name}"/>
```

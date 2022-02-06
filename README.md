# File System Search
A simple Java Servlet web application that is able to search files and folders in the file system.


Before starting the web application DataSource should be configured in the context.xml with a resource as shown below. And sql scripts in "init.sql" should be executed to create required table and views.
```
<Resource name="connpool" auth="Container" type="javax.sql.DataSource"
        username="" password="" driverClassName="org.postgresql.Driver"
        url="jdbc:postgresql://{host}:{port}/{db_name}"/>
```

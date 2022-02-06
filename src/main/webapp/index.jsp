<%@ page import="java.util.ArrayList" %>

<html>
<head>
<title>File System Search</title>
</head>
<body>
<h2>File System Search</h2>
<form id="searchForm" action="SearchServlet" method="get">
<input type="text" name="text" value="<%=request.getAttribute("text") == null ? "" : request.getAttribute("text")%>">
<input id="submit" type="submit" value="Search"> <br/>
</form>
<%
if(request.getAttribute("resultList")!=null)
{
	ArrayList<String> dirs = (ArrayList)request.getAttribute("resultList");
	int count = 0;
	for(String dir : dirs){
		count++;
%>
<p id="dir-<%=count%>" style="color:#A92B23;"><%=dir%></p>
<%
	}
	
	if(count == 0){
%>
<p style="color:#C92B23;">No records found</p>
<%
	}
}
%>
</body>
</html>

<%-- 
    Document   : index
    Created on : 13/05/2012, 10:04:26 PM
    Author     : Edgardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EasyBus Medellin Test</title>
    </head>
    <body>
        <form method ="get" action="EjecutarServlet">
            <input type = "text" id = "latI" name = "LatI" value="6.243633" />
            <input type = "text" id = "LongI" name = "LongI" value="-75.611644" />
            <input type = "text" id = "latD" name = "LatD" value="6.199947" />
            <input type = "text" id = "LongD" name = "LongD" value="-75.577834" />
            <input type = "text" id = "Rango" name = "Rango" value="500" />
            <input type= "submit" value= "enviar"/>
        </form>
    </body>
</html>

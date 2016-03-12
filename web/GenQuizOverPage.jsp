<%-- 
    Document   : GenQuizOverPage
    Created on : Mar 11, 2016, 10:32:47 AM
    Author     : 984852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
    <head >
        <title>NumberQuiz is over</title> 
    </head> 
    <body> 
        <p style='color:red'>The number quiz is over! You are Einstein now.</p>	</body>
    Your total Score : ${quiz.totScore}
    <form method='post'>
        <p><input type='submit' name='btnReset' value='Reset' /></p> 
    </form>
</html> 
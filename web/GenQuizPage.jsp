<%-- 
    Document   : GenQuizPage
    Created on : Mar 11, 2016, 10:31:51 AM
    Author     : 984852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>NumberQuiz</title>
    </head>
    <body>
        <form method='post'>
            <h3>Have fun with NumberQuiz!</h3>
            
            <span style ='display:${showAgeRequest}'>
                Age: <input  type ="text" name ="txtAge" />
                <br>
                <span style='color:red'>${ageErrMsg}</span>
            </span>          
            
            <p>Your current score is: ${quiz.numCorrect} </br></br>
            <p>Guess the next number in the sequence! 
                ${quiz.currentQuestion}</p>

            <p>Your answer:<input type='text' name='txtAnswer' value='' /></p> 
            
            


            <p style='color:red;display:${displayWrong};' >Your last answer was not correct! Please try again</p>


            <p><input type='submit' name='btnNext' value='Next' /></p> 
            <p><input type='submit' name='btnReset' value='Reset' /></p> 
        </form>
        <p><button onclick="alert('${quiz.currentHint}')" />Hint</button></p> 
</body></html>

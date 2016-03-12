/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gunjan.WAP.JSP;

import com.gunjan.WAP.model.Quiz;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 984852
 */
@WebServlet(name = "JSPServlet", urlPatterns = {"/JSPServlet"})
public class JSPServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Quiz quiz = new Quiz();
        request.setAttribute("displayWrong", "none");
        request.getSession().setAttribute("quiz", quiz);
        request.getSession().setAttribute("ageErrMsg", "");
        request.getSession().setAttribute("age", null);
        request.getSession().setAttribute("showAgeRequest", "block");
        request.getSession().setAttribute("ans", "");
        request.getSession().setAttribute("showAnswer",false);
        System.out.println("Got DoGet");
        RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizPage.jsp");
        dispatch.forward(request, response);
//        response.sendRedirect("GenQuizPage.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
//        request.getSession().setAttribute("tries", quiz.getCurrentTries());

        if (request.getParameter("btnNext") != null) {

            String ans = request.getParameter("txtAnswer");
            if (!(boolean)request.getSession().getAttribute("showAnswer")) {
                if (quiz.isCorrect(ans)) {
                    quiz.scoreAnswer();
                    request.setAttribute("displayWrong", "none");
                } else {

                    request.setAttribute("wrongGuessMsg", "Your last answer was not correct! Please try again");
                    request.setAttribute("displayWrong", "block");
                    attemptLimitLeft(request);
                }
                if (quiz.getNumCorrect() == quiz.getNumQuestions()) {
                    getQuizOverPage(request, response);
                    return;
                }

            }

            getQuizPage(request, response);
            System.out.println(request.getSession().getAttribute("showAnswer"));
            if ((boolean) request.getSession().getAttribute("showAnswer")) {
                quiz.scoreAnswer();
            }

        } else if (request.getParameter("btnReset") != null) {
            response.sendRedirect("");
        } else if (request.getParameter("btnAge") != null) {
            getValidateAgeFromPage(request);
            getQuizPage(request, response);
        }

//        } else {
//            
//            RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizPage.jsp");
//            dispatch.forward(request, response);
//        }
    }

    private void getQuizPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizPage.jsp");
        dispatch.forward(request, response);
    }

    private void getQuizOverPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizOverPage.jsp");
        dispatch.forward(request, response);
    }

    private boolean attemptLimitLeft(HttpServletRequest request) {
        Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
        if (quiz.getCurrentTries() == 3) {
            request.setAttribute("ans", quiz.getCurrentAns());
            request.setAttribute("wrongGuessMsg", "Tries limit Reached. Correct Answer: " + quiz.getCurrentAns());
            request.setAttribute("displayWrong", "block");
            request.setAttribute("showAnswer", true);
            return false;
        }
//        if (quiz.getCurrentTries() > 3) {
//            
//        }
        return true;
    }

    private int getValidateAgeFromPage(HttpServletRequest request) {
        int age;
        if (request.getSession().getAttribute("age") == null) {
            try {
                age = Integer.parseInt(request.getParameter("txtAge"));

                if (age > 4 && age < 100) {
                    request.getSession().setAttribute("showAgeRequest", "none");
                    request.getSession().setAttribute("age", age);
                    return age;
                } else {
                    request.getSession().setAttribute("ageErrMsg", "Age Must be between 4 and 100");
                    return -1;
                }
            } catch (NumberFormatException err) {
                request.getSession().setAttribute("ageErrMsg", "Age must be a number; not a valid Number");
                return -1;
            }
        } else {
            return (int) request.getSession().getAttribute("age");
        }
    }
}

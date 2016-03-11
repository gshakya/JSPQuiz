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
        request.getSession().setAttribute("age",null);
        request.getSession().setAttribute("showAgeRequest", "block");
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
        if (getValidateAgeFromPage(request) > 0) {
            if (request.getParameter("btnNext") != null) {
                String ans = request.getParameter("txtAnswer");
                request.setAttribute("displayWrong", "block");
                if (quiz.isCorrect(ans)) {
                    quiz.scoreAnswer();
                    request.setAttribute("displayWrong", "none");
                }
                if (quiz.getNumCorrect() == quiz.getNumQuestions()) {
                    RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizOverPage.jsp");
                    dispatch.forward(request, response);
                } else {
                    RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizPage.jsp");
                    dispatch.forward(request, response);
                }

            } else if (request.getParameter("btnReset") != null) {
                response.sendRedirect("");
            }
        } else {
            RequestDispatcher dispatch = request.getRequestDispatcher("GenQuizPage.jsp");
            dispatch.forward(request, response);
        }

    }

    private int getValidateAgeFromPage(HttpServletRequest request) {
        int age ;
        if (request.getSession().getAttribute("age") == null) {
            try {
                age = Integer.parseInt(request.getParameter("txtAge"));

                if (age > 4 && age < 100) {
                    request.getSession().setAttribute("showAgeRequest", "none");
                    request.getSession().setAttribute("age",age);
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

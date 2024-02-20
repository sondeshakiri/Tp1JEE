package net.javaguides.registration.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javaguides.registration.dao.EmployeeDao;
import net.javaguides.registration.model.Employee;

@WebServlet("/register")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeDao employeeDao;

    public void init() {
        employeeDao = new EmployeeDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    registerEmployee(request, response);
                    break;
                case "update":
                    updateEmployee(request, response);
                    break;
                default:
                    response.sendRedirect("employeedetails.jsp");
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteEmployee(request, response);
                    break;
                default:
                    response.sendRedirect("employeedetails.jsp");
                    break;
            }
        } else {
            listEmployees(request, response);
        }
    }

    private void registerEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setContact(contact);
        employee.setAddress(address);

        try {
            employeeDao.registerEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("employee?action=list");
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Employee> employees = employeeDao.getAllEmployees();
            request.setAttribute("employees", employees);
            RequestDispatcher dispatcher = request.getRequestDispatcher("employeedetails.jsp");
            dispatcher.forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Employee existingEmployee = employeeDao.getEmployeeById(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
            request.setAttribute("employee", existingEmployee);
            dispatcher.forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");

        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setContact(contact);
        employee.setAddress(address);

        try {
            employeeDao.updateEmployee(employee);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.sendRedirect("employee?action=list");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            employeeDao.deleteEmployee(id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.sendRedirect("employee?action=list");
    }
}

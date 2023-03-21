package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.dto.Student;
import in.ineuron.service.IStudentService;
import in.ineuron.servicefactory.StudentServiceFactory;

@WebServlet("/controller/*")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {

		IStudentService stdService = StudentServiceFactory.getStudentService();

		System.out.println("Request URI :: " + request.getRequestURL());
		System.out.println("PathInfo :: " + request.getPathInfo());

		if (request.getRequestURI().endsWith("addform")) {
			String sage = request.getParameter("sage");
			String sname = request.getParameter("sname");
			String saddr = request.getParameter("saddr");

			Student student = new Student();
			student.setSage(Integer.parseInt(sage));
			student.setSname(sname);
			student.setSaddress(saddr);

			String status = stdService.addStudent(student);
			PrintWriter out = response.getWriter();
			if (status.equals("success")) {
				out.println("<h1 style='color:green; text-align:center;'>REGISTRATION SUCCESSFUL </h1>");
			} else {
				out.println("<h1 style = 'color:red; text-align:center;' >REGISTRATION FAILED</h1>");
			}
			out.close();
		}

		if (request.getRequestURI().endsWith("searchform")) {
			String sid = request.getParameter("sid");
			Student student = stdService.searchStudent(Integer.parseInt(sid));
			PrintWriter out = response.getWriter();
			if (student != null) {
				out.println("<body>");
				out.println("<center>");
				out.println("<table border=1>");
				out.println("<tr><th>ID</th><td>" + student.getSid() + "</td></tr>");
				out.println("<tr><th>Name</th><td>" + student.getSname() + "</td></tr>");
				out.println("<tr><th>Age</th><td>" + student.getSage() + "</td></tr>");
				out.println("<tr><th>Address</th><td>" + student.getSaddress() + "</td></tr>");
				out.println("</table>");
				out.println("</center>");
				out.println("</body>");

			} else {
				out.println("<h1 style ='color:red;text-align:center;'>RECORD NOT AVAILABLE FOR THE GIVEN ID " + sid
						+ "</h1>");
			}
			out.close();
		}

		if (request.getRequestURI().endsWith("deleteform")) {

			PrintWriter out = response.getWriter();
			String sid = request.getParameter("sid");
			String status = stdService.deleteStudent(Integer.parseInt(sid));
			if (status.equals("success")) {
				out.println("<h1 style='color:green;text-align=denter;'>RECORD DELETED SUCCESFULLY with ID " + sid
						+ "</h1>");
			} else {
				out.println(
						"<h1 style='color:green;text-align=denter;'>RECORD DELETION FAILED FOR ID " + sid + "</h1>");
			}
			out.close();
		}
		if (request.getRequestURI().endsWith("editform")) {

			String sid = request.getParameter("sid");
			Student student = stdService.searchStudent(Integer.parseInt(sid));
			PrintWriter out = response.getWriter();
			if (student != null) {
				// display student records as a form data so it is editable
				out.println("<body>");
				out.println("<center>");
				out.println("<form method='post' action='.controller/updaterecord'>");
				out.println("<table>");
				out.println("<tr><th>ID</th><td>" + student.getSid() + "</td></tr>");
				out.println("<input type ='hidden' name='sid' value='" + student.getSid() + "'/>");
				out.println("<tr><th>NAME</th><td><input type='text' name='sname' value=" + student.getSname()
						+ "></td></tr>");
				out.println("<tr><th>AGE</th><td><input type='text' name='sage' value=" + student.getSage()
						+ "></td></tr>");
				out.println("<tr><th>ADDRESS</th><td><input type='text' name='saddr' value=" + student.getSaddress()
						+ "></td></tr>");
				out.println("<tr><td></td><td><input type='submit' value='update'/></td></tr>");
				out.println("</table>");
				out.println("</center>");
				out.println("</body>");
			} else {
				// display not found message
				out.println("<body>");
				out.println("<h1 style='color:red;text-align:center;'>RECORD NOT AVAILABLE FOR THE GIVEN ID :: " + sid);
				out.println("</body>");
			}
			out.close();
		}

		if (request.getRequestURI().endsWith("updaterecord")) {
			PrintWriter out = response.getWriter();
			String sid = request.getParameter("sid");
			String sname = request.getParameter("sname");
			String sage = request.getParameter("sage");
			String saddr = request.getParameter("saddr");
			Student student = new Student();
			student.setSid(Integer.parseInt(sid));
			student.setSname(sname);
			student.setSage(Integer.parseInt(sage));
			student.setSaddress(saddr);

			String status = stdService.updateStudent(student);
			if (status.equals("success")) {
				out.println("<h1 style='color:green;text-align:center;'>RECORD UPDATED FOR THE GIVEN ID :: " + sid);
			} else {
				out.println(
						"<h1 style='color:green;text-align:center;'>RECORD UPDATION FAILED FOR THE GIVEN ID :: " + sid);
			}
			out.close();
		}

	}
}

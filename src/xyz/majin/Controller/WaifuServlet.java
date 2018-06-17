package xyz.majin.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.majin.Bean.PageBean;
import xyz.majin.Bean.QueryInfo;
import xyz.majin.Service.QueryService;
import xyz.majin.utils.Utils;

@WebServlet("/WaifuServlet")
public class WaifuServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 查询的关键字、页面信息
		QueryInfo qInfo = Utils.request2bean(request, QueryInfo.class);

		QueryService service = new QueryService();

		try {
			// 查找文档
			PageBean pageBean = service.getDocs(getServletContext(),qInfo);

			// 将查找的文档list保存到request域中
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("wd", request.getParameter("wd"));

			// 返回并显示
			request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
		} catch (Exception e) {
			// 显示错误界面
			request.getRequestDispatcher("/WEB-INF/jsp/404.jsp").forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);

	}

}

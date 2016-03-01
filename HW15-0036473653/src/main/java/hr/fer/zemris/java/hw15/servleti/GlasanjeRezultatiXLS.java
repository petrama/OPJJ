package hr.fer.zemris.java.hw15.servleti;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Result;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**

 * Servlet koji generira xls datoteku čiji je sadržaj rezultat glasovanja.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeRezultatiXLS extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

			Long pollID;
			try {
				pollID = Long.parseLong(req.getParameter("pollID"));
			} catch (NumberFormatException e1) {
				req.setAttribute("message", "Id glasanja mora biti broj!");
				req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
				return;
			}
			
			List<Result> results=null;
			try {
				results = DAOProvider.getDao().dohvatiRezultate(pollID);
			} catch (DAOException e) {
				req.setAttribute("message", "Dogodila se pogreška u pristupanju bazi!");
				req.getRequestDispatcher("/WEB-INF/pages/message.jsp");
				return;
			}
			if(results==null || results.isEmpty()){
				req.setAttribute("message", "U bazi nema nijedne opcije vezane za to glasanje!");
				req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
				return;
		
		}
		resp.setContentType("application/vnd.ms-excel");
		createExcelFile(results).write(resp.getOutputStream());
	}

	/**
	 * Metoda koja iz predane liste rezultata generira sadrža tražene datoteke.
	 * @param results lista rezultata glasovanja.
	 * @return sadržaj datoteke.
	 */
	private HSSFWorkbook createExcelFile(List<Result> results) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("sheet 1");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue(new HSSFRichTextString("Option"));
		rowhead.createCell(1).setCellValue(new HSSFRichTextString("Votes"));
		for (int i = 0; i < results.size(); i++) {
			Result r = results.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(
					new HSSFRichTextString(r.getOptionTitle()));
			row.createCell(1).setCellValue(r.getGlasovi());
		}
		return hwb;
	}
}

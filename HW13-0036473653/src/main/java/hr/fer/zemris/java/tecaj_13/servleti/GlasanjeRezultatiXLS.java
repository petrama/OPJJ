package hr.fer.zemris.java.tecaj_13.servleti;

import hr.fer.zemris.java.tecaj_13.servleti.GlasanjeRezultatiServlet.Result;
import hr.fer.zemris.java.tecaj_13.servleti.GlasanjeServlet.Trio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
@WebServlet("/glasanje-xls")
public class GlasanjeRezultatiXLS extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Result> results = (List<Result>) req.getServletContext().getAttribute(
				"rezultati");
		if(results==null){
			results=GlasanjeRezultatiServlet.createResults(req);
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
		rowhead.createCell(0).setCellValue(new HSSFRichTextString("Band"));
		rowhead.createCell(1).setCellValue(new HSSFRichTextString("Votes"));
		for (int i = 0; i < results.size(); i++) {
			Result r = results.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(
					new HSSFRichTextString(r.getTrio().getBend()));
			row.createCell(1).setCellValue(r.getGlasovi());
		}
		return hwb;
	}
}

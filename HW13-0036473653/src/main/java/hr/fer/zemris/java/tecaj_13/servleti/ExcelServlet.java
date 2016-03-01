package hr.fer.zemris.java.tecaj_13.servleti;

import java.io.IOException;
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
 * Servlet koji odgovara na korisnikov zahtjev za generiranjem xls dokumenta.
 * Kao argumente zahtjeva prima tri broja.
 * Prvi broj odeđuje donju granicu, drugi gornju granicu ranga brojeva koji će se generirati a treći broj stranica u xml dokumentu.
 * Prvi argument mora biti označen kao 'a', a drugi kao 'b' i vrijednost im mora biti od -100 do 100 uključivo.
 * Legalni broj stranica je od 1 do 5 uključivo.
 * Bez obzira na zadane brojeve xml će sadržavati točno 20 brojeva od a do a+20.
 * Prvi stupac svake stranice xml-a bit će ti brojevi a drugi stupac n-ta potencija tog broja gdje je n broj trenutne stranice.
 * @author petra
 *
 */
@SuppressWarnings("serial")
@WebServlet("/powers")
public class ExcelServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer a = null;
		Integer b = null;
		Integer n = null;
		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception ex) {
			a = 0;

		}
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception ex) {
			b = 20;
		}

		try {
			n = Integer.valueOf(req.getParameter("n"));
		} catch (Exception ex) {
			n = 1;
		}
		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.setAttribute("message",
					"Error: Wrong arguments a, b must be between -100 and 100 and n between 1 and 5!");
			req.getRequestDispatcher("WEB-INF/pages/message.jsp").forward(
					req, resp);
			return;
		}
		
		resp.setContentType("application/vnd.ms-excel");
	
		createExcelFile(a, b, n).write(resp.getOutputStream());
		req.setAttribute("message", "Success: file is generated!");
		req.getRequestDispatcher("WEB-INF/pages/message.jsp").forward(req,
				resp);

	}

	/**
	 * Pomoćna metoda koja kreira cjelokupni sadržaj dokumenta.
	 * @param a donja granica 
	 * @param b gornja granica ranga
	 * @param n broj stranica dokumenta
	 * @return sadržaj xml-a
	 */
	private HSSFWorkbook createExcelFile(int a, int b, int n) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 0; i <= n; i++) {
			createSheet(hwb, i + 1, a, b);
		}
		return hwb;
	}

	/**
	 * Pomoćna metoda koja kreira pojedinu stranicu xls fajla.
	 * @param hwb sadržaj xls-a
	 * @param sheetNum broj stranice koja se treba kreirati
	 * @param a donja granica ranga
	 * @param b gornja granica
	 */
	private void createSheet(HSSFWorkbook hwb, int sheetNum, int a, int b) {
		HSSFSheet sheet = hwb.createSheet("sheet" + sheetNum);
		createRowHead(sheet);
		for (int i = 0; i < b - a + 1; i++) {
			createRow(sheet, sheetNum, i + 1, a + i);
		}

	}

	/**
	 * Pomoćna metoda koja kreira jedan redak u stranici fajla.
	 * @param sheet stranica na kojoj triba kreirat redak.
	 * @param power broj stranice u kojoj se redak kreira.
	 * @param rowNum broj retka u stranici.
	 * @param rowValue vrijednost prvog stupca retka koji se kreira.
	 */
	private void createRow(HSSFSheet sheet, int power, int rowNum, int rowValue) {
		HSSFRow row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue(rowValue);
		row.createCell(1).setCellValue(Math.pow(rowValue, power));

	}

	/**
	 * Metoda koja kreira zaglavlje stranice
	 * @param sheet stranica na kojoj treba kreirati zaglavlje.
	 */
	private void createRowHead(HSSFSheet sheet) {
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue(new HSSFRichTextString("Number"));
		rowhead.createCell(1).setCellValue(new HSSFRichTextString("Power"));

	}

}

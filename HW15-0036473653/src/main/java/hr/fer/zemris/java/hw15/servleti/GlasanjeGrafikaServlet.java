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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
/**
 * Servlet koji iscrtava dijagram rezultata glasovanja.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			Long pollID;
			try {
				pollID = Long.parseLong(req.getParameter("pollID"));
			} catch (Exception e) {
				req.setAttribute("message", "Id glasanja mora biti broj!");
				req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
				return;
			}
		
			List<Result> results;
			try {
				results = DAOProvider.getDao().dohvatiRezultate(pollID);
			} catch (DAOException e) {
				req.setAttribute("message", "Dogodila se pogreska u pristupanju bazi");
				req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
				return;
			}
			if(results==null || results.isEmpty()){
				req.setAttribute("message", "U bazi nema nijedne opcije vezane za to glasanje!");
				req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
				return;
		}
		PieDataset dataset = createDataset(results);
		JFreeChart chart = createChart(dataset, "Results");
		resp.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 500, 270);
	}
	/**
	 * Metoda koja stvara skup podataka koji su ulaz za dijagram.
	 * @param results lista rezultata glasovanja.
	 * @return kreirani dataset.
	 */
	private PieDataset createDataset(List<Result> results) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (Result r : results) {
			result.setValue(r.getOptionTitle(), r.getGlasovi());
		}
		return result;

	}
	/**
	 * Metoda koja kreira dijagram zadanog naslova iz predanog skupa podataka.
	 * @param dataset skup podataka iz kojeg treba kreirati dijagram.
	 * @param title naslov dijagrama.
	 * @return dijagram.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset);
		return chart;
	}

}

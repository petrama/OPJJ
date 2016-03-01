package hr.fer.zemris.java.tecaj_13.servleti;

import hr.fer.zemris.java.tecaj_13.servleti.GlasanjeRezultatiServlet.Result;
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
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Result> results = (List<Result>) req.getServletContext().getAttribute(
				"rezultati");
		if(results==null){
			results=GlasanjeRezultatiServlet.createResults(req);
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
			result.setValue(r.getTrio().getBend(), r.getGlasovi());
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

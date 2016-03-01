package hr.fer.zemris.java.tecaj_13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
/**
 * Servlet koji ispisuje sliku rezultata jednog istraživanja.
 * Istraživanje se odnosi na broj korisnika tri operacijska sustava.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/reportImage")
public class ReportServlet extends HttpServlet {
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("image/png");
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Comparison");

	ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart,500, 270);

	}

	/**
	 * Metoda kreira podatke iz koji se generira grafikon.
	 * @return ulazni podatci za stvaranje grafikona.
	 */
	public PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;

	}

	/**
	 * Metoda koja iz predanog skupa podataka stvara grafikon sa zadanim naslovom.
	 * @param dataset skup ulaznih podataka.
	 * @param title naslov.
	 * @return stvoreni grafikon.
	 */
	public JFreeChart createChart(PieDataset dataset, String title) {
	
		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart
																// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}

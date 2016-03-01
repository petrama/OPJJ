package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Razred koji predstavlja radnika koji na zahtjev klijenta kao odgovor salje sliku.
 * Dimenzije slike su 200*200, a slika prikazuje plavi krug.
 * @author Petra Marče
 *
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		BufferedImage bim = new BufferedImage(200, 200,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(bim.getMinX(), bim.getMinY(), bim.getWidth(),
				bim.getHeight());
		g2d.setColor(Color.CYAN);
		g2d.fillOval(100, 100, 50, 50);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(bim, "png", bos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.write(bos.toByteArray());

	}

}

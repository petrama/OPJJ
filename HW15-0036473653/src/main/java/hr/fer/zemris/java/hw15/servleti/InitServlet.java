package hr.fer.zemris.java.hw15.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Glasanje;
import hr.fer.zemris.java.tecaj_13.model.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet ubacuje dva nova glasanja u bazu podataka i opcije tih glasanja.
 * Ukoliko u bazi već postoji glasanje sa istim imenom servlet ne dodaje novo.
 * @author Petra Marče
 *
 */
@SuppressWarnings("serial")
@WebServlet("/servleti/init")
public class InitServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
	
	Glasanje g=DAOProvider.getDao().dohvatiGlasanje("Glasanje za omiljeni bend");
	if(g==null){
		DAOProvider.getDao().ubaciGlasanje(new Glasanje(null, "Glasanje za omiljeni bend", "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!"));
		g=DAOProvider.getDao().dohvatiGlasanje("Glasanje za omiljeni bend");
		List<Result> zapisi=new ArrayList<>();
		zapisi.add(new Result(null, "The Beatles",  "http://www.geocities.com/~goldenoldies/TwistAndShout-Beatles.mid", 0,g.getId()));
		zapisi.add(new Result(null, "The Platters", "http://www.geocities.com/~goldenoldies/SmokeGetsInYourEyes-Platters-ver2.mid",0,g.getId()));
		zapisi.add(new Result(null, "The Beach Boys", "http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid", 0,g.getId()));
		zapisi.add(new Result(null, "The Four Seasons", "http://www.geocities.com/~goldenoldies/BigGirlsDontCry-FourSeasons.mid", 0,g.getId()));
		zapisi.add(new Result(null, "The Marcels", "http://www.geocities.com/~goldenoldies/Bluemoon-Marcels.mid", 0,g.getId()));
		zapisi.add(new Result(null, "The Everly Brothers", "http://www.geocities.com/~goldenoldies/All.I.HaveToDoIsDream-EverlyBrothers.mid", 0,g.getId()));
		zapisi.add(new Result(null, "The Mamas And The Papas", "http://www.geocities.com/~goldenoldies/CaliforniaDreaming-Mamas-Papas.mid", 0,g.getId()));
		DAOProvider.getDao().ubaciRezultate(zapisi);
	}
	
	g=DAOProvider.getDao().dohvatiGlasanje("Glasanje za omiljenu hranu");
	if(g==null){
		DAOProvider.getDao().ubaciGlasanje(new Glasanje(null, "Glasanje za omiljenu hranu", "Od sljedećega, što biste sada najradije pojeli? Kliknite da biste glasali!"));
		g=DAOProvider.getDao().dohvatiGlasanje("Glasanje za omiljenu hranu");
		List<Result> zapisi=new ArrayList<>();
		zapisi.add(new Result(null, "Janje sa ražnja",  "http://dobarzivot.net/hrana/kako-dobro-ispeci-janje-na-raznju/", 0,g.getId()));
		zapisi.add(new Result(null, "Odojak", "http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid",0,g.getId()));
		zapisi.add(new Result(null, "Škampi na buzaru", "http://wall.hr/clanak/moj-recept-skampi-na-buzaru/", 0,g.getId()));
		zapisi.add(new Result(null, "Pizza", "http://pizza.com/", 0,g.getId()));
		zapisi.add(new Result(null, "Čokolino", "http://www.podravka.hr/brandovi/proizvodi/djecja-hrana/lino-cokolino", 0,g.getId()));
		
		DAOProvider.getDao().ubaciRezultate(zapisi);
	}
	
	
	resp.sendRedirect(req.getContextPath()+"/servleti/index.html");
	}
	
	

}

package hr.fer.zemris.java.tecaj_11.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja apstraktnog subjekta u oblikovnom obrascu Promatrač.
 * Razred obavještava svoje promatrače ako dođe do promjene stanja.
 * @author petra
 *
 */
public abstract class AbstractLocalizationProvider implements
		ILocalizationProvider {
	List<ILocalizationListener> listeners;
	public AbstractLocalizationProvider(){
		listeners=new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if(listeners.contains(listener)==false){
			listeners=new ArrayList<>(listeners);
			listeners.add(listener);
		}

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners=new ArrayList<>(listeners);
		listeners.remove(listener);

	}

	public void fire(){
		for(ILocalizationListener listener:listeners){
			listener.localizationChanged();
		}
	}

}

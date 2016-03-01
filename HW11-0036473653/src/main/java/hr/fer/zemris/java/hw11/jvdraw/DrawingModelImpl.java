package hr.fer.zemris.java.hw11.jvdraw;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jvdraw.geometrical.Circle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.Line;

public class DrawingModelImpl implements DrawingModel {
	List<GeometricalObject> geomObj;
	List<DrawingModelListener> observers;

	public DrawingModelImpl() {
		geomObj = new ArrayList<GeometricalObject>();
		observers = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return geomObj.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= getSize()) {
			throw new IllegalArgumentException("Index must be between 0 and "
					+ (getSize() - 1));
		}
		return geomObj.get(index);
	}

	@Override
	public void add(GeometricalObject object) {

		geomObj.add(object);
		for (DrawingModelListener l : observers) {
			l.objectsAdded(this, geomObj.size(), geomObj.size());
		}

	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (!observers.contains(l)) {
			observers = new ArrayList<>(observers);
			observers.add(l);
		}

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		observers = new ArrayList<>(observers);
		observers.remove(l);

	}
	/**
	 * Metoda koja vraća stringovnu reprezentaciju svih geometrijskih objekata u modelu.
	 * Pojedini elementi odvojeni su oznakom novog reda.
	 * A elementi jednog lika razmakom.
	 * @return stringovna reprezentacija liste objekata.
	 */

	public String toText() {
		StringBuilder sb = new StringBuilder();
		for (GeometricalObject g : geomObj) {
			sb.append(g.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * Metoda koja iz stringovne reprezentacije geometrijskih objekata rekonstruira model.
	 * @param text string iz kojeg se model rekonstruira.
	 */

	public void fromText(String text) {
		String[] comp = text.split("\r\n");
	
		List<GeometricalObject> nova = new ArrayList<>();
		for (String component : comp) {
			String[] oneComponent = component.split(" ");

			switch (oneComponent[0]) {
			case "LINE":
				if (oneComponent.length != 8) {
					throw new IllegalArgumentException("File error!");
				}
				nova.add(new Line(Circle.parse(oneComponent[1]), Circle
						.parse(oneComponent[2]), Circle.parse(oneComponent[3]),
						Circle.parse(oneComponent[4]), Circle
								.parse(oneComponent[5]), Circle
								.parse(oneComponent[6]), Circle
								.parse(oneComponent[7])));

				break;

			case "CIRCLE":
				if (oneComponent.length != 7) {
					throw new IllegalArgumentException("File error!");
				}

				try {
					Double.parseDouble(oneComponent[3]);
					
					
				} catch (NumberFormatException ne) {
					throw new IllegalArgumentException("File error!");
				}
				nova.add(new Circle(Circle.parse(oneComponent[1]), Circle
						.parse(oneComponent[2]), 	Double.parseDouble(oneComponent[3]),
						Circle.parse(oneComponent[4]), Circle
								.parse(oneComponent[5]), Circle
								.parse(oneComponent[6])));

				break;

			case "FCIRCLE":
				if (oneComponent.length != 10) {
					throw new IllegalArgumentException("File error!");
				}

				try {
					double broj = Double.parseDouble(oneComponent[3]);
					Integer br = (int) broj;
					System.out.println(br);
					
					System.out.println(oneComponent[3]);
					
				} catch (NumberFormatException ne) {
					throw new IllegalArgumentException("File error!");
				}
				nova.add(new FilledCircle(Circle.parse(oneComponent[1]), Circle
						.parse(oneComponent[2]),
						Double.parseDouble(oneComponent[3]),
						Circle.parse(oneComponent[4]), Circle
								.parse(oneComponent[5]), Circle
								.parse(oneComponent[6]), Circle
								.parse(oneComponent[7]), Circle
								.parse(oneComponent[8]), Circle
								.parse(oneComponent[9])));

				break;
				default:break;

			}
		}
	geomObj=nova;
	}

	
	/**
	 * Metoda koja vraća gornji lijevi kut slike.
	 * On se trazi tako da se pita sve elemente u listi na kojim su koordinatama te se od tih koordinata uzme minimalna x i y koor.
	 * @return gornji lijevi kut
	 */
	public Point getUpperLeftCorner(){
		int minx=geomObj.get(0).getMinX();
		int miny=geomObj.get(0).getMinY();
		
		for(GeometricalObject g:geomObj){
			
			if(g.getMinX()<minx){
				minx=g.getMinX();
			}
			
			if(g.getMinY()<miny){
				miny=g.getMinY();
			}
		}
		return new Point(minx,miny);
	}
	
	
	/**
	 * Metoda koja vraća gornji lijevi kut slike.
	 * On se trazi tako da se pita sve elemente u listi na kojim su koordinatama te se od tih koordinata uzme maksimalna x i y koor.
	 * @return donji desni kut
	 */
	
	public Point  getBottomRightCorner(){
		
		int maxx=geomObj.get(0).getMaxX();
		
		int maxy=geomObj.get(0).getMaxY();
		
		for(GeometricalObject g:geomObj){
			if(g.getMaxX()>maxx){
				maxx=g.getMaxX();
			}
			
			if(g.getMaxY()>maxy){
				maxy=g.getMaxY();
			}
			
		}
		return new Point(maxx,maxy);
	}
}

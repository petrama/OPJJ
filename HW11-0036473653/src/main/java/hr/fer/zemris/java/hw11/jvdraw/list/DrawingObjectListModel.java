package hr.fer.zemris.java.hw11.jvdraw.list;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw11.jvdraw.geometrical.GeometricalObject;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DrawingModel model;
	
	 private final List<ListDataListener> promatraci = new ArrayList<>();
	
	
	public DrawingObjectListModel(DrawingModel model) {
		super();
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	 @Override
	 public void addListDataListener(ListDataListener l) {
	  if (!promatraci.contains(l)) {
	   promatraci.add(l);
	  }
	 }

	 

	 public void fireIntervalAdded(ListDataEvent event) {
	  for (ListDataListener l : promatraci) {
	   l.intervalAdded(event);
	  }
	 }

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		 fireIntervalAdded(new ListDataEvent(this,
				    ListDataEvent.INTERVAL_ADDED, index0, index1));
		
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		 fireIntervalAdded(new ListDataEvent(this,
				    ListDataEvent.INTERVAL_ADDED, index0, index1));
		
		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		 fireIntervalAdded(new ListDataEvent(this,
				    ListDataEvent.INTERVAL_ADDED, index0, index1));
		
		
	}

	

	

}

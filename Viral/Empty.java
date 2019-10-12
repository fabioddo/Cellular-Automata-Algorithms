package Viral;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*; 
import java.awt.event.*;
import java.awt.color.*;
//import javafx.scene.paint.Color;
import Viral.Initiator;
import java.util.Random;
//import javafx.scene.paint.Color;
import it.unipr.sowide.actodes.util.gui.AutomataViewer;
import it.unipr.sowide.actodes.util.gui.CellShape;
import it.unipr.sowide.actodes.ca.cell.Cell;
import it.unipr.sowide.actodes.core.actor.Behavior;
import it.unipr.sowide.actodes.core.actor.BehaviorState;
import it.unipr.sowide.actodes.core.actor.CaseFactory;
import it.unipr.sowide.actodes.core.actor.MessageHandler;
import it.unipr.sowide.actodes.core.actor.MessagePattern;
import it.unipr.sowide.actodes.core.configuration.Configuration;
import it.unipr.sowide.actodes.core.configuration.PropertiesGroup;
import it.unipr.sowide.actodes.core.controller.SpaceInfo;
import it.unipr.sowide.actodes.core.filtering.constraint.IsEqual;
import it.unipr.sowide.actodes.core.filtering.constraint.IsInstance;
import it.unipr.sowide.actodes.core.interaction.Status;
import it.unipr.sowide.actodes.core.registry.Reference;


public final class Empty extends Cell<String>{
	
	private static final long serialVersionUID = 1L;
	
	//reagisco all'istanza della classe "Healthy"
	private static final MessagePattern PATTERN_HEALTHY = MessagePattern.contentPattern(new IsInstance(Healthy.class));
	
	//associo un determinato valore a tutte le istanze
	public static final int value=0;
	
	
	

public Empty(final int x, final int y, final List<Reference> n)
{
	    super(x, y, n);
	    
}


public Empty(final Cell<String> c)
{
  super(c);
}


/** {@inheritDoc} **/
@Override
public void cases(final CaseFactory c)
{
  super.cases(c);

  MessageHandler h = (m) -> {
    getNeighborhood().forEach((i) -> send(i, value));

    return null;
  };

  c.define(START, h);


  h = (m) -> {
	  //siccome la cella "Healthy" si è sdoppiata ora l'istanza diventa di quel tipo
	  return new Healthy(this);
  };

  c.define(PATTERN_HEALTHY, h);
  
}



/** {@inheritDoc} **/
@Override
public Behavior update()

{ 
	getNeighborhood().forEach((i) -> send(i, value));

	return null;
}
	
	

}

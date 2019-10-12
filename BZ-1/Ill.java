package Belousov_Zhabotinsky_Reaction;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*; 
import java.awt.event.*;
import java.awt.color.*;
//import javafx.scene.paint.Color;

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


public final class Ill extends Cell<String> {
	
	private static final long serialVersionUID = 1L;
	
	public static final int n=Initiator.n;
	
	
	public Ill(final int x, final int y, final List<Reference> n)
	{
		    super(x, y, n);
		    
	}
	
	
	public Ill(final Cell<String> c)
	{
	  super(c);
	}
	
	
	/** {@inheritDoc} **/
	@Override
	public void cases(final CaseFactory c)
	{
	  super.cases(c);

	  MessageHandler h = (m) -> {
	    getNeighborhood().forEach((i) -> send(i, n));

	    return null;
	  };

	  c.define(START, h);

//non utilizzo il message pattern che reagisce ai valori della classe Integer siccome non necessito di salvare
//la conta dei vicini appartenenti alle 3 classi
	  
//codice eseguito al posto del metodo update()  
	  h = (m) -> {
		 //cambia stato istantaneamente
		 return new Healthy(this);
	   
	  };
	  
	  c.define(CYCLE, h);
	  
	}
	
	
	/** {@inheritDoc} **/
	@Override
	public Behavior update()
	{ 	 
		getNeighborhood().forEach((i) -> send(i, n));
		return null;
		
	}

}

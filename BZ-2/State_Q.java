package BZ_Reaction2;


import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*;

import Belousov_Zhabotinsky_Reaction.Healthy;
import Belousov_Zhabotinsky_Reaction.Ill;
import Belousov_Zhabotinsky_Reaction.Infected;
import Belousov_Zhabotinsky_Reaction.Initiator;

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


public final class State_Q extends Cell<String>{
	
private static final long serialVersionUID = 1L;
	
private static final MessagePattern PATTERN_INT = MessagePattern.contentPattern(new IsInstance(Integer.class));
	
	//valore che assumono tutte le istanze di questa classe
	public static int state=BZ_Reaction2.Initiator.q;
	private int sum, sumQ, sumThrough, sum1;
	
	
	public State_Q(final int x, final int y, final List<Reference> n)
	{
		    super(x, y, n);
		    
		    this.sum=0;
		    this.sum1=0;
		    this.sumQ=0;
		    this.sumThrough=0;
			
	}
			
	public State_Q(final Cell<String> c)
	{
	  super(c);
	 
	  this.sum=0;
	  this.sum1=0;
	  this.sumQ=0;
	  this.sumThrough=0;
	}	
	
	
	/** {@inheritDoc} **/
	@Override
	public void cases(final CaseFactory c)
	{
	  super.cases(c);

	  MessageHandler h = (m) -> {
	    getNeighborhood().forEach((i) -> send(i, state));

	    return null;
	  };

	  c.define(START, h);

	  h = (m) -> {
		  this.sum+=(int)m.getContent();
		  if((int)m.getContent()==State_1.state)
			  this.sum1++;
		  else if((int)m.getContent()==state)
		  {
			  this.sumQ++;
		  }
		  else this.sumThrough++;
		  
		  System.out.println("Contenuto del messaggio: "+(int)m.getContent());
	    return null;
	  };
	  
	  c.define(PATTERN_INT, h);
	  
	  
	  
	  h = (m) -> {
		  //passa immediatamente allo stato 1
			return new State_1(this);	  
	  };

	  c.define(CYCLE, h);
	  

	}

	/** {@inheritDoc} **/
	@Override
	public Behavior update()
	
	{ 
		
		this.sum=0;
		this.sum1=0;
		this.sumQ=0;
		this.sumThrough=0;

	    getNeighborhood().forEach((i) -> send(i, state));

	    return null;
	    
	}
	
	
}

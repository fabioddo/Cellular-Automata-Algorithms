package BZ_Reaction2;


import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*;

import Belousov_Zhabotinsky_Reaction.Healthy;
import Belousov_Zhabotinsky_Reaction.Ill;
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



public final class State_Intermediate extends Cell<String>{
	
private static final long serialVersionUID = 1L;
	
private static final MessagePattern PATTERN_INT = MessagePattern.contentPattern(new IsInstance(Integer.class));
	
	private int state;
	private int sum, sumQ, sumThrough, sum1;
	
	//definisco il costruttore di questa classe
	public State_Intermediate(final int x, final int y, final List<Reference> n)
	{
		    super(x, y, n);
		    
		    this.sum=0;
		    this.sum1=0;
		    this.sumQ=0;
		    this.sumThrough=0;
		    
		    //associo all'istanza un valore casuale compreso nel range [2, q-1]
		    Random rand = new Random();
		    
		    this.state=rand.nextInt(BZ_Reaction2.Initiator.q-2)+2;
	}
			
	
	public State_Intermediate(final Cell<String> c, int val)
	{
	  super(c);
	  
	  this.state=val;
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
	    getNeighborhood().forEach((i) -> send(i, this.state));

	    return null;
	  };

	  c.define(START, h);

	  h = (m) -> {
		  this.sum+=(int)m.getContent();
		  if((int)m.getContent()==State_1.state)
			  this.sum1++;
		  else if((int)m.getContent()==State_Q.state)
		  {
			  this.sumQ++;
		  }
		  else this.sumThrough++;
		 
		  System.out.println("Contenuto del messaggio: "+(int)m.getContent());
	    return null;
	  };

	  
	  c.define(PATTERN_INT, h);
	  
	  
h = (m) -> {
		  
	int newstate;
	
	//calcolo il nuovo stato
		newstate=(int)(((this.state+this.sum)/(9-this.sum1))+BZ_Reaction2.Initiator.g);
		
		
		//stesso controllo precedente (visto in State_1), associato alle regole (v) e (vi)
		if(newstate>=BZ_Reaction2.Initiator.q)
		{
			return new State_Q(this);	
		}
		else
		{
		
		if(newstate==State_1.state) return new State_1(this);
		else {//else omissibile perchè ci entra di sicuro se non ritorna prima
			
			this.sum=0;
			this.sum1=0;
			this.sumQ=0;
			this.sumThrough=0;
			//associo all'istanza il suo nuovo valore
			this.state=newstate;
			//invia a tutti i suoi vicini il suo nuovo stato
			getNeighborhood().forEach((i) -> send(i, this.state));
			return null;
			}
		
		}
		
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
			
		getNeighborhood().forEach((i) -> send(i, this.state));
		return null;

	}

}

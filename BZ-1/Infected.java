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


public final class Infected extends Cell<String>{
	
	
	private static final long serialVersionUID = 1L;
	
	private static final MessagePattern PATTERN_INT = MessagePattern.contentPattern(new IsInstance(Integer.class));
	
	private int value;
	private int ill;
	private int infected;
	private int healthy;
	private int sum;
	
	public Infected(final int x, final int y, final List<Reference> n)
	{
		    super(x, y, n);
		  
		    //associo all'istanza creata un certo valore tra 1 e n-1 (siccome 0->Healthy cell, n->Ill cell)
		    Random rand = new Random();
		    this.value=rand.nextInt(Ill.n-1)+1;
		    
		    System.out.println("Infected random value is: "+this.value);
		    
		    this.ill=0;
		    this.infected=0;
		    this.healthy=0;
		    this.sum=0;
		    
	}
	
	
	public Infected(final Cell<String> c, int valore)
	{
	  super(c);
	  //attribuisco il nuovo valore
	  this.value=valore;
	  	  
	  this.ill=0;
	  this.infected=0;
	  this.healthy=0;
	  this.sum=0;
	  
	}
	
	
	
	/** {@inheritDoc} **/
	@Override
	public void cases(final CaseFactory c)
	{
	  super.cases(c);

	  MessageHandler h = (m) -> {
		//value non è static! cambia instanza per istanza (si usa il this)
	    getNeighborhood().forEach((i) -> send(i, this.value));

	    return null;
	  };

	  c.define(START, h);

	  
	  h = (m) -> {
		  
		  this.sum+=(int)m.getContent();
		  if((int)m.getContent()==Ill.n)
			  this.ill++;
		  
		  else if((int)m.getContent()==Healthy.value) this.healthy++;
		  else this.infected++;
		  
		  System.out.println("Contenuto del messaggio: "+(int)m.getContent());
	    return null;
	  };

	  
	  c.define(PATTERN_INT, h);
	  

	  h = (m) -> {
		  //calcolo del nuovo stato
		  int valore;
			valore=(int)((this.sum+this.value)/(this.infected+this.ill+1))+Initiator.g;	
			
			if(valore==0)
			{
				return new Healthy(this);
			}
			
			
			if(valore==Ill.n)
			{				
				return new Ill(this); 
			}
			
			return new Infected(this, valore);
			
			//oppure si esegue il seguente codice invece di ricreare un'istanza dello stesso tipo con un nuovo valore
			
			/*this.value=valore;
			this.ill=0;
			this.infected=0;
			this.healthy=0;
			this.sum=0;
			getNeighborhood().forEach((i) -> send(i, this.value));
			
			return null;*/
		   
		  };

		  
		  c.define(CYCLE, h);
		  
	}
	

	/** {@inheritDoc} **/
	@Override
	public Behavior update()
	{ 
			
		this.ill=0;
		this.infected=0;
		this.healthy=0;
		this.sum=0;
		getNeighborhood().forEach((i) -> send(i, this.value));
		
		return null;
	}
		
		

}

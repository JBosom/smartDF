package reseauSimple;

import jade.core.Agent;

public class ProducteurAgent extends Agent {

	  protected void setup() {
	  	System.out.println("Hello World! My name is "+getLocalName());
	   	// Make this agent terminate
	  	doDelete();
	  } 
	}
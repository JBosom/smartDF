package reseauSimple.horloge;

import reseauSimple.global.AbstractAgent;
import reseauSimple.global.GlobalBehaviourSearchAnnuaires;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HorlogeAgent extends AbstractAgent
{
	private static final long serialVersionUID = 1L;
	
	private static final long tickerTalker = 500;
	
	private HorlogeBehaviourTalker horlogeTalker;
	
	protected void setup()
	{
		setServiceName("horloge");
		super.setup();
		
		System.out.println("Céation de l'horloge nommée : " + getName() + "\n");
		
		DFAgentDescription rechercheAllAgents = new DFAgentDescription();
		ServiceDescription SDAllAgents = new ServiceDescription();
		rechercheAllAgents.addServices(SDAllAgents);
		addBehaviour(new GlobalBehaviourSearchAnnuaires(this, rechercheAllAgents));
		horlogeTalker = new HorlogeBehaviourTalker(this, tickerTalker, AbstractAgent.HORLOGE_PHASE_NEGOCIATION);
		addBehaviour(horlogeTalker);
		addBehaviour(new HorlogeBehaviourListener(this));
	}
	
	public HorlogeBehaviourTalker getHorlogeTalker() {
		return horlogeTalker;
	}
	
}

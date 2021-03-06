package reseauSimple.observateur;

import reseauSimple.global.AbstractAgent;

public class ObservateurAgent extends AbstractAgent
{
	// Constantes
	private static final long serialVersionUID = 1L;
	
	// Variables statiques
	private static int currentID = 0;

	// Caractéristiques de l'observateur
	private final int idObservateur = getNextID();

	// Variables propres
	private ObservateurGUI myGUI;
	private ObservateurBehaviourMsgListenerAllPhases msgListenerAllPhases;
	
	protected void setup()
	{
		setServiceName("observateur");
		super.setup();

		System.out.println("Creation d'un nouvel observateur :\n" + toString());
		
		// Create and show the GUI
		myGUI = new ObservateurGUI(this);
		myGUI.setVisible(true);
		
		// Ajout d'un listener pour récupérer les réponses aux messages envoyés en continu
		addBehaviour(new ObservateurBehaviourHorlogeListener(this));
		msgListenerAllPhases = new ObservateurBehaviourMsgListenerAllPhases(this);
		addBehaviour(msgListenerAllPhases);
	}
	
	public ObservateurGUI getMyGUI()
	{
		return myGUI;
	}
	
	public static int getNextID()
	{
		return currentID++;
	}
	
	public ObservateurBehaviourMsgListenerAllPhases getMsgListenerAllPhases()
	{
		return msgListenerAllPhases;
	}
	
	@Override
	public String toString()
	{
		return "Observateur " + idObservateur + " nommé : " + getName() + "\n";
	}
	
}

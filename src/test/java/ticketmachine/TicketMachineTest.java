package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}
	
	@Test
	// S3 : On n'imprime pas le ticket si le montant inséré est insuffisant 
	public void nImprimePasSiPasAssezDArgent() {
		// Given : "J'ai une machine neuve"
		// When : "Je ne mets pas assez d'argent"
		machine.insertMoney(PRICE-1);
		// Then : "On ne doit pas imprimer le ticket"
		assertFalse(machine.printTicket());
	}

	@Test
	// S4 : On imprime le ticket si le montant inséré est suffisant 
	public void imprimeSiAssezDArgent(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket());
	}

}

package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket 
	public void decrementeQuandImprime() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(0,machine.getBalance(), "La balance n'est pas mise à jour");
	}

	@Test
	// S6 : Le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	public void collecteMontantApresImpression() {
		machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(), "Le total est mis à jour au mauvais moment");
		machine.printTicket();
		assertEquals(50,machine.getTotal(), "Le total n'est pas mis à jour au bon moment");
	}

	@Test
	// S7 : refund() rends correctement la monnaie
	public void refundRendsMonnaie() {
		machine.insertMoney(PRICE+30);
		assertEquals(PRICE+30,machine.refund(),"La fonctionne refund ne rends pas le bon montant");
		machine.printTicket();
		assertEquals(30,machine.refund(),"La fonctionne refund ne rends pas le bon montant");
	}

	@Test 
	// S8 : refund() remet la balance à 0 
	public void refundResetBalance() {
		machine.insertMoney(PRICE+30);
		machine.printTicket();
		machine.refund();
		assertEquals(0,machine.getBalance(),"refund ne remet pas à 0 la balance");
	}

	@Test
	// S9 : On ne peut pas insérer un montant négatif 
	public void pasMontantNeg() {
		assertThrows(IllegalArgumentException.class,()->{ machine.insertMoney(-PRICE);});
	}

	@Test 
	// S10 : On ne peut pas créer de machine qui délivre des tickets dont le prix est négatif 
	public void pasTicketNeg() {
		assertThrows(IllegalArgumentException.class,()->{TicketMachine m = new TicketMachine(-PRICE);});
	}
}

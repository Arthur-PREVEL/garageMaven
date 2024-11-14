package garages;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.PrintStream;
import java.util.*;


/**
 * Représente une voiture qui peut être stationnée dans des garages.
 */
@RequiredArgsConstructor
@ToString
public class Voiture {

	@Getter
	@NonNull
	private final String immatriculation;
	@ToString.Exclude // On ne veut pas afficher les stationnements dans toString
	private final List<Stationnement> myStationnements = new LinkedList<>();

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws IllegalStateException Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws IllegalStateException {
		// Et si la voiture est déjà dans un garage ?
		if ( !myStationnements.isEmpty() && myStationnements.getLast().estEnCours()) {
			throw new IllegalStateException( " La voiture est déjà dans un garage");

		}
		else {
			Stationnement s = new Stationnement(this, g);
			myStationnements.add(s);

		}
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws IllegalStateException si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() {
		// Trouver le dernier stationnement de la voiture
		// Terminer ce stationnement
		if (myStationnements.isEmpty() || !myStationnements.getLast().estEnCours()  ) {
			throw new IllegalStateException( "La voiture ne stationne dans aucun garage. ");
		}
		else {
			myStationnements.getLast().terminer();
		}

	}

	/**
	 * Calcule l'ensemble des garages visités par cette voiture
	 * 
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> listGarage = new HashSet<>();
		for (Stationnement s : this.myStationnements) {
			listGarage.add( s.getGarageVisite() );
		}
		return listGarage;
	}

	/**
	 * Détermine si la voiture est actuellement dans un garage
	 * 
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// Vrai si il y a des stationnements et le dernier stationnement est en cours
		boolean var = false;
		if ( (!myStationnements.isEmpty()) && (myStationnements.getLast().estEnCours()) ) {
			var = true;
		}
		return var;

	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 * <br>
	 * Exemple :
	 * 
	 * <pre>
	 * Garage(name=Universite Champollion Albi):
	 * 		Stationnement{ entree=13/11/2024, sortie=13/11/2024 }
	 * Garage(name=ISIS Castres):
	 * 		Stationnement{ entree=13/11/2024, sortie=13/11/2024 }
	 * 		Stationnement{ entree=13/11/2024, en cours }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out pour imprimer dans la
	 *            console)
	 */
	public void imprimeStationnements(PrintStream out) {
		// Utiliser les méthodes toString() de Garage et Stationnement
		String chaine = "";

		for (Garage g : this.garagesVisites() ) {
			String chaineGarage = g.toString() + ": " + "\n";
			for (Stationnement s : myStationnements) {
				if (g == s.getGarageVisite()) {
					chaineGarage += "\t" + s.toString() + "\n";;
				}
			}
			chaine += chaineGarage;
		}
		out.println( chaine );

	}

}

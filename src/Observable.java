/**
 * Interface Observable pour Observateur
 */
public interface Observable {
    /**
     * Attache un Observateur à l'Observable
     * @param o l'Observateur à attacher
     */
    void attacheObservateur(Observateur o);

    /**
     * Détache un Observateur d'un Observable
     * @param o l'Observateur à détacher
     */
    void detacheObservateur(Observateur o);

    /**
     * Notifie tous les Observateur pour actualiser
     */
    void notifieObservateur();
}

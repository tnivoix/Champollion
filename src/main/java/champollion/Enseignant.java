package champollion;

import java.util.ArrayList;
import java.util.List;

public class Enseignant extends Personne {

    private List<ServicePrevu> servicesPrevus = new ArrayList<>();
    private List<Intervention> interventionsPlanifiees = new ArrayList<>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures
     * équivalent TD" Pour le calcul : 1 heure de cours magistral vaut 1,5 h
     * "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut
     * 0,75h "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet
     * enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        float tmp = 0;
        for (ServicePrevu s : servicesPrevus) {
            tmp += s.getVolumeTD() + s.getVolumeCM() * 1.5 + s.getVolumeTP() * 0.75;
        }
        return (int) tmp;
    }

    /**
     * Vérifie si l'enseignant à au moins 192h de prévu
     *
     * @return l'enseignant est en sous service ou non
     */
    public boolean enSousService() {
        return heuresPrevues() < 192;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE
     * spécifiée en "heures équivalent TD" Pour le calcul : 1 heure de cours
     * magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent
     * TD" 1 heure de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet
     * enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        float tmp = 0;
        for (ServicePrevu s : servicesPrevus) {
            if (s.getEnseignement() == ue) {
                tmp += s.getVolumeTD() + s.getVolumeCM() * 1.5 + s.getVolumeTP() * 0.75;
            }
        }
        return (int) tmp;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        ServicePrevu s = new ServicePrevu(ue, this, volumeCM, volumeTD, volumeTP);
        servicesPrevus.add(s);
    }

    /**
     * Ajoute une intervention à l'enseignant
     *
     * @param inter l'intervention concernée
     */
    public void ajouteIntervention(Intervention inter) {
        interventionsPlanifiees.add(inter);
    }

    public int resteAPlanifier(UE ue, TypeIntervention type) {
        float prevu = 0;
        for (ServicePrevu s : servicesPrevus) {
            if (s.getEnseignement() == ue) {
                switch (type) {
                    case CM:
                        prevu += s.getVolumeCM();
                        break;
                    case TD:
                        prevu += s.getVolumeTD();
                        break;
                    case TP:
                        prevu += s.getVolumeTP();
                        break;
                }
            }
        }
        int planifie = 0;
        for (Intervention i : interventionsPlanifiees) {
            if (i.getMatiere() == ue && i.getType()==type) {
                planifie+=i.getDuree();
            }
        }
        return (int)(prevu)-planifie;
    }
}

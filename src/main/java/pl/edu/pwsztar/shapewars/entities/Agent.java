package pl.edu.pwsztar.shapewars.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="AGENT")
public class Agent{

    @Id
    @GeneratedValue
    @Column(name="AGENT_ID")
    private Long ID;

    /**
     * Pojedynczy bot jest przypisany do danego gracza
     */
    @OneToOne
    @JoinColumn(name="DEDICATED_PLAYER_ID")
    private User dedicatedPlayer;

    @Column(name="VERSION")
    private Long version;

    /**
     * Kolumna odpowiada za decyzje podejmowane na podstawie
     * ogólnego stanu pola walki
     */
    @Column(name="OVERALL_BALANCE_PRIORITY")
    private Double overallBalancePriority;

    /**
     * Kolumna odpowiada za decyzje podejmowane na podstawie
     * siły wrogiej drużyny
     */
    @Column(name="ENEMY_INTERNAL_BALANCE_PRIORITY")
    private Double enemyInternalBalancePriority;

    /**
     * Kolumna odpowiada za decyzje podejmowane na podstawie
     * siły sprzymierzeńców
     */
    @Column(name="ALLY_INTERNAL_BALANCE_PRIORITY")
    private Double allyInternalBalancePriority;

    /**
     * Kolumna odpowiada za decyzje podejmowane na podstawie
     * siły poszczególnych wrogów
     */
    @Column(name="INDIVIDUAL_ENEMY_PRIORITY")
    private Double individualEnemyPriority;

    /**
     * Kolumna odpowiada za decyzje podejmowane na podstawie
     * siły poszczególnych sprzymierzeńców
     */
    @Column(name="INDIVIDUAL_ALLY_PRIORITY")
    private Double individualAllyPriority;

    /**
     * Kolumna odpowiada za decyzje podejmowane na podstawie
     * zdolności jednostki do zadania obrażeń
     */
    @Column(name="DAMAGE_OUTPUT_PRIORITY")
    private Double damageOutputPriority;

    @PrePersist
    public void prePersist(){
        this.version=1L;
    }
    @PreUpdate
    public void preUpdate(){
        this.version+=1L;
    }


}

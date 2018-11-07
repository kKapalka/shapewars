package pl.edu.pwsztar.shapewars.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name="SKILL")
public class Skill {
    @Id
    @Generated
    @Column(name="SKILL_ID")
    private Long ID;

    @Column(name="NAME")
    private String name;

    @Column(name="TOOLTIP")
    private String tooltip;
}

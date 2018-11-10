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
@Table(name="KEYWORD")
public class Keyword {

    @Id
    @Generated
    @Column(name="KEYWORD_ID")
    private Long ID;

    @Column(name="NAME")
    private String name;

    @Column(name="EXPLANATION")
    private String explanation;

}

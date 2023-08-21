package jeonb.usedcompu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import jeonb.usedcompu.model.CompuPost;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @NotBlank
    @Column(length = 100)
    private String memberId;

    private Long compuPostId;

    @OneToOne
    @JoinColumn(name = "compuPostId",referencedColumnName = "id", insertable = false, updatable = false)
    private CompuPost compuPost;
}

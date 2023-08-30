package jeonb.usedcompu.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
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

    @OneToMany(mappedBy = "cart")
    private List<CompuPost> posts =  new ArrayList<>();
}

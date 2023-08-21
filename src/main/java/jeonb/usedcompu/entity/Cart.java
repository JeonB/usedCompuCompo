package jeonb.usedcompu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
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

    private String memberId;
    private Long compuPostId;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "memberId",referencedColumnName = "writerEmail", insertable = false, updatable = false),
            @JoinColumn(name = "compuPostId",referencedColumnName = "id", insertable = false, updatable = false)})
    private CompuPost compuPost;
}

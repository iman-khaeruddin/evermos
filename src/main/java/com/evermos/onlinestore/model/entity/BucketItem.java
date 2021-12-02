package com.evermos.onlinestore.model.entity;

import com.evermos.onlinestore.model.enums.YesNoFlag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Table(name="bucket_item")
@Where(clause = " flag_active = 'y'")
@Entity
public class BucketItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bucket_item_id")
    private Integer bucketItemId;

    @Column(name = "bucket_id")
    private Integer bucketId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "bucket_id", referencedColumnName = "bucket_id", insertable = false, updatable = false)
    @JsonBackReference
    private Bucket bucket;

    @Column(name = "item_id")
    private Integer itemId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", insertable = false, updatable = false)
    private Item item;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "flag_active", columnDefinition = "enum('y','n') DEFAULT 'y'")
    @Enumerated(EnumType.STRING)
    private YesNoFlag flagActive = YesNoFlag.y;
}

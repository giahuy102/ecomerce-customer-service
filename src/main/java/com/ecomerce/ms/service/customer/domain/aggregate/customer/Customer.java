package com.ecomerce.ms.service.customer.domain.aggregate.customer;

import com.huyle.ms.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends AggregateRoot<UUID> {

    @Column(name = "full_name")
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @JoinColumn(name = "address_id")
    @OneToOne
    private Address address;

    public boolean satisfyOrderMakingCondition() {
        return isActive && address.hasAllAddressInfo();
    }
}

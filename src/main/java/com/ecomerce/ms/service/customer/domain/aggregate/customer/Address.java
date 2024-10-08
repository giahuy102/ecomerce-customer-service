package com.ecomerce.ms.service.customer.domain.aggregate.customer;

import com.huyle.ms.domain.LocalEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address extends LocalEntity<UUID> {

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street_name")
    private String streetName;

    private String city;

    private String province;

    public boolean hasAllAddressInfo() {
        return !streetNumber.isBlank()
                && !streetName.isBlank()
                && !city.isBlank()
                && !province.isBlank();
    }
}

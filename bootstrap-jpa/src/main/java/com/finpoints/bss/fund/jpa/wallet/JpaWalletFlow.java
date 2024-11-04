package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@Table(name = "wallet_flow")
@AllArgsConstructor
public class JpaWalletFlow extends JpaEntityBase {
}

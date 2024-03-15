package com.commerce.payment.service.common.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record Money(BigDecimal amount) {

    public boolean isGreaterThanZero() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount.compareTo(money.amount()) > 0;
    }

    public Money add(Money money) {
        return new Money(setScale(this.amount.add(money.amount())));
    }

    public Money substract(Money money) {
        return new Money(setScale(this.amount.subtract(money.amount())));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    /*
     * Bazı fractional(ondalıklı) sayılar tam olarak ifade edilemez. Mesela 7/10 değeri tam ifade edilemez içinde bir noktadan sonra sürekli tekrar eden
     * değerler gelir. İşlemimizi yaparken; bu değer Java'da mevcut bitler kullanılarak en doğru sonuç ile seçilir.Biz işlemlere her devam ettiğimizde
     * hata büyümeye devam eder.Bu hatayı en aza indirmek için yuvarlama modunu Half Even ayarladık.Half Even Kümülatif hataları en aza indirir.
     * */
    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}

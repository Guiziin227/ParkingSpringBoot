package com.guiweber.estacionamento.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;


    public static String gerarRecibo(){
        LocalDateTime date = LocalDateTime.now();
        String recibo = date.toString().substring(0,19);
        return recibo = recibo.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }

    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutes = entrada.until(saida, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total += PRIMEIROS_15_MINUTES;
            // complete com a lógica para calcular o custo até 15 minutos de uso

        } else if (minutes <= 60) {
            total += PRIMEIROS_60_MINUTES;
            // complete com a lógica para calcular o custo até os primeiros 60 minutos de uso

        } else {
            long addicionalMinutes = minutes - 60;
            Double totalParts = ((double) addicionalMinutes / 15);
            if (totalParts > totalParts.intValue()) {
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * (totalParts.intValue() + 1));
            } else {
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * totalParts.intValue());
            }
            // complete com a lógica para calcular o custo acima de 60 minutos de uso

        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }



    public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {

        BigDecimal desconto = ((numeroDeVezes > 0) && (numeroDeVezes % 10 == 0))
                ? custo.multiply(new BigDecimal(DESCONTO_PERCENTUAL))
                : new BigDecimal(0);

        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }
}

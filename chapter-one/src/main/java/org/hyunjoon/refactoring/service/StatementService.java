package org.hyunjoon.refactoring.service;

import org.hyunjoon.refactoring.model.Invoice;
import org.hyunjoon.refactoring.model.Performance;
import org.hyunjoon.refactoring.model.Play;
import org.hyunjoon.refactoring.model.Type;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StatementService {
    public static String statement(Invoice invoice, Map<String, Play> plays) {
        Objects.requireNonNull(invoice, "invoice");
        Objects.requireNonNull(plays, "plays");

        int totalAmount = 0;
        int volumeCredits = 0;
        final StringBuilder result = new StringBuilder();
        result.append("Invoice (Customer: ").append(invoice.getCustomer()).append(")\n");
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();

        List<Performance> performances = invoice.getPerformances();
        for (Performance performance : performances) {
            final Play play = plays.get(performance.getPlayID());
            int thisAmount = amountFor(performance, play);

            volumeCredits += Math.max(performance.getAudience() - 30, 0);
            if (play.getType() == Type.COMEDY) {
                volumeCredits += Math.floor(performance.getAudience() / 5);
            }

            result.append(" -").append(play.getName()).append(": ").append(moneyFormat.format(thisAmount * 10))
                    .append(" (").append(performance.getAudience()).append("ppl)\n");
            totalAmount += thisAmount;
        }

        result.append("Total Amount: ").append(moneyFormat.format(totalAmount * 10)).append("\n");
        result.append("Credit: ").append(volumeCredits).append("pts\n");

        return result.toString();
    }

    private static int amountFor(Performance performance, Play play) {
        int thisAmount = 0;

        switch (play.getType()) {
            case TRAGEDY:
                thisAmount = 40000;
                if (performance.getAudience() > 30) {
                    thisAmount += 1000 * (performance.getAudience() - 30);
                }
                break;

            case COMEDY:
                thisAmount = 30000;
                if (performance.getAudience() > 20) {
                    thisAmount += 10000 + 500 * (performance.getAudience() - 20);
                }
                thisAmount += 300 * performance.getAudience();
                break;

            default:
                throw new RuntimeException("Unknown Type: " + play.getType());
        }

        return thisAmount;
    }
}

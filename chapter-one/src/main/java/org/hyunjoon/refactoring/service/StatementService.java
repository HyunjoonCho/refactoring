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
    private Map<String, Play> plays;

    public String statement(Invoice invoice, Map<String, Play> plays) {
        this.plays = Objects.requireNonNull(plays, "plays");

        int totalAmount = 0;
        int volumeCredits = 0;
        final StringBuilder result = new StringBuilder();
        result.append("Invoice (Customer: ").append(invoice.getCustomer()).append(")\n");
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();

        List<Performance> performances = invoice.getPerformances();
        for (Performance performance : performances) {
            int thisAmount = amountFor(performance);

            volumeCredits += Math.max(performance.getAudience() - 30, 0);
            if (playFor(performance).getType() == Type.COMEDY) {
                volumeCredits += Math.floor(performance.getAudience() / 5);
            }

            result.append(" -").append(playFor(performance).getName()).append(": ").append(moneyFormat.format(thisAmount * 10))
                    .append(" (").append(performance.getAudience()).append("ppl)\n");
            totalAmount += thisAmount;
        }

        result.append("Total Amount: ").append(moneyFormat.format(totalAmount * 10)).append("\n");
        result.append("Credit: ").append(volumeCredits).append("pts\n");

        return result.toString();
    }

    private Play playFor(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    private int amountFor(Performance aPerformance) {
        int result = 0;

        switch (playFor(aPerformance).getType()) {
            case TRAGEDY:
                result = 40000;
                if (aPerformance.getAudience() > 30) {
                    result += 1000 * (aPerformance.getAudience() - 30);
                }
                break;

            case COMEDY:
                result = 30000;
                if (aPerformance.getAudience() > 20) {
                    result += 10000 + 500 * (aPerformance.getAudience() - 20);
                }
                result += 300 * aPerformance.getAudience();
                break;

            default:
                throw new RuntimeException("Unknown Type: " + playFor(aPerformance).getType());
        }

        return result;
    }
}

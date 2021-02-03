package org.hyunjoon.refactoring.service;

import org.hyunjoon.refactoring.model.Invoice;
import org.hyunjoon.refactoring.model.Performance;
import org.hyunjoon.refactoring.model.Play;
import org.hyunjoon.refactoring.model.Type;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;

public class StatementService {
    private Invoice invoice;
    private Map<String, Play> plays;

    public String statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = Objects.requireNonNull(invoice, "invoice");
        this.plays = Objects.requireNonNull(plays, "plays");

        final StringBuilder result = new StringBuilder();
        result.append("Invoice (Customer: ").append(invoice.getCustomer()).append(")\n");

        for (Performance performance : invoice.getPerformances()) {
            result.append(" -").append(playFor(performance).getName()).append(": ").append(krw(amountFor(performance)))
                    .append(" (").append(performance.getAudience()).append("ppl)\n");
        }

        result.append("Total Amount: ").append(krw(totalAmount())).append("\n");
        result.append("Credit: ").append(totalVolumeCredits()).append("pts\n");

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

    private int volumeCreditsFor(Performance aPerformance) {
        int result = 0;

        result += Math.max(aPerformance.getAudience() - 30, 0);
        if (playFor(aPerformance).getType() == Type.COMEDY) {
            result += Math.floor(aPerformance.getAudience() / 5);
        }

        return result;
    }

    private int totalVolumeCredits() {
        int result = 0;

        for (Performance performance : invoice.getPerformances()) {
            result += volumeCreditsFor(performance);
        }

        return result;
    }

    private int totalAmount() {
        int result = 0;

        for (Performance performance : invoice.getPerformances()) {
            result += amountFor(performance);
        }

        return result;
    }

    private String krw(int aNumber) {
        return NumberFormat.getCurrencyInstance().format(aNumber * 10);
    }
}

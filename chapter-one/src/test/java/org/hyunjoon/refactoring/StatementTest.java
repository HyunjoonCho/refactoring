package org.hyunjoon.refactoring;

import org.hyunjoon.refactoring.model.Invoice;
import org.hyunjoon.refactoring.model.Performance;
import org.hyunjoon.refactoring.model.Play;
import org.hyunjoon.refactoring.model.Type;
import org.hyunjoon.refactoring.service.StatementService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatementTest {
    private final String expectedInvoice = "Invoice (Customer: BigCo)\n" +
            " -Hamlet: KRW 650,000 (55ppl)\n" +
            " -As You Like It: KRW 580,000 (35ppl)\n" +
            " -Othello: KRW 500,000 (40ppl)\n" +
            "Total Amount: KRW 1,730,000\n" +
            "Credit: 47pts\n";

    @Test
    public void testStatementService() {
        StatementService statementService = new StatementService();
        Assert.assertEquals(expectedInvoice, statementService.statement(createInvoice(), createPlays()));
    }

    private Invoice createInvoice() {
        List<Performance> performances = new ArrayList<>();
        performances.add(new Performance("hamlet", 55));
        performances.add(new Performance("as-like", 35));
        performances.add(new Performance("othello", 40));

        return new Invoice("BigCo", performances);
    }

    private Map<String, Play> createPlays() {
        Map<String, Play> plays = new HashMap<>();

        Play hamlet = new Play("Hamlet", Type.TRAGEDY);
        plays.put("hamlet", hamlet);

        Play asLike = new Play("As You Like It", Type.COMEDY);
        plays.put("as-like", asLike);

        Play othello = new Play("Othello", Type.TRAGEDY);
        plays.put("othello", othello);

        return plays;
    }
}

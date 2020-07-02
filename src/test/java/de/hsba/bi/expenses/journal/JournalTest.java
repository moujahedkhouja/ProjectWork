package de.hsba.bi.expenses.journal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.hsba.bi.projectWork.user.User;

public class JournalTest {

    @Test
    void shouldComputeTheBalance() {
        // given
        User anne = new User("Anne");
        User benedikt = new User("Benedikt");
        User charlotte = new User("Charlotte");

        Journal journal = new Journal(anne);
        addEntry(journal, 15, "Drinks", anne, anne, benedikt, charlotte);
        addEntry(journal, 16, "Kino", charlotte, anne, charlotte);
        addEntry(journal, 10, "Taxi", benedikt, anne, charlotte);
        addEntry(journal, 5, "Ausgleich", charlotte, benedikt);

        // when
        Map<User, BigDecimal> balance = journal.computeBalance();

        printJournal(journal);
        printBalance(balance);

        // then
        assertThat(balance).hasSize(3);
        assertThat(balance.get(benedikt)).isZero();
        assertThat(balance.get(anne)).isEqualTo(new BigDecimal("-3.00"));
        assertThat(balance.get(charlotte)).isEqualTo(new BigDecimal("3.00"));
    }

    private void addEntry(Journal journal, int amount, String description, User creditor, User... debitors) {
        journal.getEntries().add(new JournalEntry(new BigDecimal(amount), description, creditor, Set.of(debitors)));
    }

    private void printBalance(Map<User, BigDecimal> balance) {
        System.out.println("------------------------------------------");
        System.out.println(balance);

        for (Map.Entry<User, BigDecimal> entry : balance.entrySet()) {
            int comparison = entry.getValue().compareTo(BigDecimal.ZERO);
            if (comparison > 0) {
                System.out.printf("%s bekommt %s%n", entry.getKey(), entry.getValue());
            } else if (comparison < 0) {
                System.out.printf("%s schuldet %s%n", entry.getKey(), entry.getValue().negate());
            }
        }
    }

    private void printJournal(Journal journal) {
        for (JournalEntry entry : journal.getEntries()) {
            System.out.printf("%s hat %s ausgegeben für %s%n", entry.getCreditor(), entry.getAmount(), entry.getDebitorNames());
        }
    }
}

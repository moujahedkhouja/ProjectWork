package de.hsba.bi.expenses.journal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsba.bi.projectwork.user.User;

@SpringBootTest
@Transactional
class JournalServiceIntegrationTest {

    private final User testUser = new User("test", "pw", "USER");
    @Autowired
    private JournalService serviceToTest;
    @Autowired
    private JournalRepository journalRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        journalRepository.deleteAll();
        entityManager.persist(testUser);
    }

    @Test
    void shouldFindJournals() {
        // given
        Journal j1 = buildJournalWithDescription("something");
        Journal j2 = buildJournalWithDescription("nothing");
        Journal j3 = buildJournalWithDescription("nonsense");

        // when
        List<Journal> journals = serviceToTest.findJournals("");
        // then
        assertThat(journals).containsExactlyInAnyOrder(j1, j2, j3);

        // when
        journals = serviceToTest.findJournals("anything");
        // then
        assertThat(journals).isEmpty();

        // when
        journals = serviceToTest.findJournals("thing");
        // then
        assertThat(journals).containsExactlyInAnyOrder(j1, j2);

        // when
        journals = serviceToTest.findJournals("some");
        // then
        assertThat(journals).containsExactly(j1);
    }

    private Journal buildJournalWithDescription(String description) {
        Journal journal = new Journal(testUser);
        journal.setName("Test");

        JournalEntry entry = new JournalEntry();
        entry.setAmount(BigDecimal.ONE);
        entry.setCreditor(testUser);
        entry.setDebitors(Set.of(testUser));
        entry.setDescription(description);

        serviceToTest.addJournalEntry(journal, entry);
        return serviceToTest.save(journal);
    }
}

package de.hsba.bi.expenses.journal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import de.hsba.bi.projectWork.user.User;

@MockitoSettings
class JournalServiceUnitTest {

    @Mock
    private JournalRepository journalRepositoryMock;

    @InjectMocks
    private JournalService serviceToTest;

    @Test
    void shouldFindJournalsWithSearch() {
        // given
        List<Journal> givenJournals = List.of(new Journal(new User()));
        given(journalRepositoryMock.findByEntryDescription("dummy")).willReturn(givenJournals);

        // when
        List<Journal> foundJournals = serviceToTest.findJournals("dummy");

        // then
        assertThat(foundJournals).isEqualTo(givenJournals);
        verify(journalRepositoryMock, never()).findAll();
    }

    @Test
    void shouldFindJournalsWithoutSearch() {
        // given
        List<Journal> givenJournals = List.of(new Journal(new User()));
        given(journalRepositoryMock.findAll()).willReturn(givenJournals);

        // when
        List<Journal> foundJournals = serviceToTest.findJournals(" ");

        // then
        assertThat(foundJournals).isEqualTo(givenJournals);
        verify(journalRepositoryMock, never()).findByEntryDescription(anyString());
    }
}

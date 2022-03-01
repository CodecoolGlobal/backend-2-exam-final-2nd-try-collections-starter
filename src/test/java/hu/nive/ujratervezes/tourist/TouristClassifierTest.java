package hu.nive.ujratervezes.tourist;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TouristClassifierTest {
    private List<Tourist> tourists = new ArrayList<>();
    private LocalDate now;
    private TouristClassifier classifier;

    @BeforeEach
    void setUp() {
        tourists.add(new Tourist("Aladar", LocalDate.of(1960, Month.APRIL, 2), Set.of("kirandulas", "sport", "barkacsolas", "hegymaszas", "csonakazas")));
        tourists.add(new Tourist("Bela", LocalDate.of(1992, Month.JUNE, 22), Set.of("foci", "fozes", "vitorlazas")));
        tourists.add(new Tourist("Cecil", LocalDate.of(1960, Month.MARCH, 2), Set.of("uszas", "strandolas", "muzeumlatogatas")));
        tourists.add(new Tourist("Dezire", LocalDate.of(2009, Month.FEBRUARY, 8), Set.of("barkacsolas", "futas")));
        tourists.add(new Tourist("Eleonora", LocalDate.of(1960, Month.MARCH, 12), Set.of("kirandulas", "sport", "barkacsolas")));
        tourists.add(new Tourist("Ferenc", LocalDate.of(1960, Month.SEPTEMBER, 7), Set.of("muzeumlatogatas", "foci", "barkacsolas")));
        tourists.add(new Tourist("Gerda", LocalDate.of(2011, Month.FEBRUARY, 8), Set.of("kirandulas", "barlangaszat", "vitorlazas")));
        tourists.add(new Tourist("Hanna", LocalDate.of(2003, Month.JUNE, 8), Set.of("sieles")));
        tourists.add(new Tourist("Ibolya", LocalDate.of(1984, Month.FEBRUARY, 8), Set.of("kirandulas", "sport")));
        tourists.add(new Tourist("Jozsef", LocalDate.of(2011, Month.JUNE, 3), Set.of("kirandulas", "hegymaszas", "barkacsolas")));
        tourists.add(new Tourist("Karoly", LocalDate.of(1998, Month.APRIL, 8), Set.of("kirandulas", "tenisz")));

        now = LocalDate.of(2022, Month.JANUARY, 10);
        classifier = new TouristClassifier(tourists);
    }

    @Order(1)
    @Test
    void testIfTouristClassifierThrowsException() {
        assertThatThrownBy(() -> {
            new TouristClassifier(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Order(2)
    @Test
    void testTouristCanTellAge() {
        Tourist t = new Tourist("A", now.minus(10, ChronoUnit.YEARS), null);
        assertThat(t.getAge(now)).isEqualTo(10);
        Tourist t2 = new Tourist("A", now.minus(20, ChronoUnit.YEARS), null);
        assertThat(t2.getAge(now)).isEqualTo(20);
        Tourist t3 = new Tourist("A", now.minus(42, ChronoUnit.YEARS), null);
        assertThat(t3.getAge(now)).isEqualTo(42);
    }

    @Order(3)
    @Test
    void findYoungTourists() {
        assertThat(classifier.findYoungTourists(now))
                .isNotEmpty()
                .hasSize(3)
                .allMatch(t -> t.getAge(now) < 18);
    }

    @Order(4)
    @Test
    void testFindTouristsOlderThan() {
        for (int i = 1; i < 80; i++) {
            int a = classifier.findTouristsOlderThan(i, now).size();
            int b = classifier.findTouristsOlderThan(i + 1, now).size();
            assertThat(b).isLessThanOrEqualTo(a);
        }
        assertThat(classifier.findTouristsOlderThan(0, now)).hasSameSizeAs(tourists);
        assertThat(classifier.findTouristsOlderThan(17, now)).hasSize(8);
        assertThat(classifier.findTouristsOlderThan(80, now)).isEmpty();
    }

    @Order(5)
    @Test
    void testFindTouristsWithHobbiesIsCalledWithEnoughHobbies() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            classifier.findTouristsWithHobbies(null);
        });
        assertThatIllegalArgumentException().isThrownBy(() -> {
            classifier.findTouristsWithHobbies(Collections.emptySet());
        });
        assertThatIllegalArgumentException().isThrownBy(() -> {
            classifier.findTouristsWithHobbies(Set.of("biciklizes"));
        });
        assertThat(classifier.findTouristsWithHobbies(Set.of("A", "B"))).isNotNull();
    }

    @Order(6)
    @Test
    void testFindTouristsWithHobbiesReturnsTheProperAnswer() {
        Set<String> hobbies1 = Set.of("kirandulas", "barkacsolas");
        List<Tourist> g1 = classifier.findTouristsWithHobbies(hobbies1);
        assertThat(g1)
                .isNotEmpty()
                .hasSize(3)
                .allMatch(p -> p.getHobbies().stream().filter(hobbies1::contains).toList().size() >= 2);

        Set<String> hobbies2 = Set.of("muzeumlatogatas", "barkacsolas");
        List<Tourist> g2 = classifier.findTouristsWithHobbies(hobbies2);
        assertThat(g2)
                .isNotEmpty()
                .hasSize(1)
                .allMatch(p -> p.getHobbies().stream().filter(hobbies2::contains).toList().size() >= 2);

        Set<String> hobbies3 = Set.of("kirandulas", "sport");
        List<Tourist> g3 = classifier.findTouristsWithHobbies(hobbies3);
        assertThat(g3)
                .isNotEmpty()
                .hasSize(3)
                .allMatch(p -> p.getHobbies().stream().filter(hobbies3::contains).toList().size() >= 2);
    }

    @Order(7)
    @Test
    void testGetNamesPerMonth() {
        Map<Month, List<String>> namesPerMonth = classifier.getNamesPerMonth();
        assertThat(namesPerMonth).hasSize(5);
        assertThat(namesPerMonth.get(Month.FEBRUARY)).containsExactly("Dezire", "Gerda", "Ibolya");
        assertThat(namesPerMonth.get(Month.JUNE)).containsExactly("Bela", "Hanna", "Jozsef");
        System.out.println(namesPerMonth.size());

    }
}
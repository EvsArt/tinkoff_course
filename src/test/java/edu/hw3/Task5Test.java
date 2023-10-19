package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Список контактов")
class Task5Test {

    @Test
    @DisplayName("Normal Data")
    void parseNormalContacts() {

        String[] contacts = new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes", "Anton"};

        Task5.FamilyName[] resAsc = Task5.parseContacts(contacts, "ASC");
        Task5.FamilyName[] resDesc = Task5.parseContacts(contacts, "DESC");

        List<Task5.FamilyName> expectedRes = List.of(
            new Task5.FamilyName(null, "Anton"),
            new Task5.FamilyName("Aquinas", "Thomas"),
            new Task5.FamilyName("Descartes", "Rene"),
            new Task5.FamilyName("Hume", "David"),
            new Task5.FamilyName("Locke", "John")
        );

        assertThat(resAsc).isEqualTo(expectedRes.toArray());
        assertThat(resDesc).isEqualTo(expectedRes.reversed().toArray());

    }

    @Test
    @DisplayName("Fail Data")
    void parseFailContacts() {

        String[] emptyContacts = new String[] {};
        String[] nullContact = new String[] {null};
        String[] blancContact = new String[] {""};
        String[] nullContacts = new String[] {null, null};
        String[] blankContacts = new String[] {"", ""};
        String[] nullArr = null;
        String[] normArr = new String[] {"Artur", "Shelby Thomas"};

        Task5.FamilyName[] res1 = Task5.parseContacts(emptyContacts, "ASC");
        Task5.FamilyName[] res2 = Task5.parseContacts(nullContact, "ASC");
        Task5.FamilyName[] res3 = Task5.parseContacts(blancContact, "ASC");
        Task5.FamilyName[] res4 = Task5.parseContacts(nullContacts, "ASC");
        Task5.FamilyName[] res5 = Task5.parseContacts(blankContacts, "ASC");
        Task5.FamilyName[] res6 = Task5.parseContacts(nullArr, "ASC");
        Task5.FamilyName[] res7 = Task5.parseContacts(normArr, "NOTASCORDESC");

        assertThat(res1).isEqualTo(new Task5.FamilyName[] {});
        assertThat(res2).isEqualTo(new Task5.FamilyName[] {null});
        assertThat(res3).isEqualTo(new Task5.FamilyName[] {null});
        assertThat(res4).isEqualTo(new Task5.FamilyName[] {null, null});
        assertThat(res5).isEqualTo(new Task5.FamilyName[] {null, null});
        assertThat(res6).isEqualTo(new Task5.FamilyName[] {});
        assertThat(res7).isEqualTo(new Task5.FamilyName[] {});

    }

}

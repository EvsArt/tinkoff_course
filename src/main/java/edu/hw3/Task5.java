package edu.hw3;

import java.util.Arrays;
import java.util.Comparator;
import org.jetbrains.annotations.Nullable;

public final class Task5 {

    private Task5() {
    }

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    public static FamilyName[] parseContacts(
        @Nullable String[] contacts,
        String sortOrder
    ) {  // sortOrder = "ASC"/"DESC"

        if (contacts == null) {
            return new FamilyName[0];
        }

        FamilyName[] contactsList = new FamilyName[contacts.length];

        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i] == null || contacts[i].isBlank()) {
                continue;
            }
            FamilyName contact = createObjWithFamilyAndName(contacts[i]);
            contactsList[i] = contact;
        }
        if (!sortOrder.equals(ASC) && !sortOrder.equals(DESC)) {
            return new FamilyName[0];
        }

        boolean isReversed = sortOrder.equals(DESC);

        Comparator<FamilyName> contactsComparator = (o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }

            String usingValueThisObj;
            String usingValueAnotherObj;

            usingValueThisObj = (o1.family == null) ? o1.name : o1.family;
            usingValueAnotherObj = (o2.family == null) ? o2.name : o2.family;

            return usingValueThisObj.compareTo(usingValueAnotherObj);

        };

        if (isReversed) {
            Arrays.sort(contactsList, contactsComparator.reversed());
        } else {
            Arrays.sort(contactsList, contactsComparator);
        }

        return contactsList;

    }

    private static FamilyName createObjWithFamilyAndName(String in) {    // IN = "Name Family" or "Name"

        String[] familyName = in.split(" ");
        if (familyName.length == 2) {
            return new FamilyName(familyName[1], familyName[0]);
        } else {
            return new FamilyName(null, familyName[0]);
        }
    }

    public record FamilyName(@Nullable String family, String name) {
    }

}

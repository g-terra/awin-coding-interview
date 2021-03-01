package com.awin.coffeebreak.entity;

import com.awin.coffeebreak.services.utils.formatting.WebFormat;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "coffee_break_preference")
public class CoffeeBreakPreference  implements WebFormat {

    public static List<String> TYPES = List.of("food", "drink");
    public static List<String> DRINK_TYPES = List.of("coffee", "tea");
    public static List<String> FOOD_TYPES = List.of("sandwich", "crisps", "toast");

    @Id
    Integer id;

    @Column
    String type;

    @Column
    String subType;

    @ManyToOne
    StaffMember requestedBy;

    @Column
    Instant requestedDate;

    @ElementCollection
    @CollectionTable(name = "coffebreak_preferences_detail",
            joinColumns = {@JoinColumn(name = "coffebreak_preference_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "detail")
    @Column
    Map<String, String> details;

    public CoffeeBreakPreference(
            final String type, final String subType, final StaffMember requestedBy, final Map<String, String> details
    ) {
        if (!TYPES.contains(type)) {
            throw new IllegalArgumentException();
        }
        if (type.equals("food")) {
            if (!FOOD_TYPES.contains(subType)) {
                throw new IllegalArgumentException();
            }
        } else {
            if (!DRINK_TYPES.contains(subType)) {
                throw new IllegalArgumentException();
            }
        }

        this.type = type;
        this.subType = subType;

        this.requestedBy = requestedBy;
        if (details != null && !details.isEmpty()) {
            setDetails(details);
        } else {
            setDetails(new HashMap<>());
        }
    }

    public CoffeeBreakPreference() {

    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(final String subType) {
        this.subType = subType;
    }

    public StaffMember getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(final StaffMember requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Instant getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(final Instant requestedDate) {
        this.requestedDate = requestedDate;
    }

    public void setDetails(final Map<String, String> details) {
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public String getAsJson() {
        return "{" +
                "\"id\":" + id +
                ", \"type\":\"" + type + '"' +
                ", \"subType\":\"" + subType + '"' +
                ", \"requestedBy\":\"" + requestedBy + '"' +
                ", \"requestedDate\":\"" + requestedDate + '"' +
                ", \"details\":\"" + details + '"' +
                '}';
    }

    public String getAsXml() {
        return "<preference type=\"" + type + "\" subtype=\"" + subType + "\">" +
                "<requestedBy>" + requestedBy + "</requestedBy>" +
                "<details>" + details + "</details>" +
                "</preference>";
    }

    public String getAsListElement() {
        final String detailsString = details.keySet().stream()
                .map(e -> e + " : " + details.get(e))
                .collect(Collectors.joining(","));

        return "<li>" + requestedBy.getName() + " would like a " + subType + ". (" + detailsString + ")</li>";
    }
}

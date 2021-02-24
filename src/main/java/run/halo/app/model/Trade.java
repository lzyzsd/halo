package run.halo.app.model;

import lombok.*;


@Data
@ToString
@EqualsAndHashCode
public class Trade {
    private String name;
    private String price;
    private String turnover;
    private String volume;
}

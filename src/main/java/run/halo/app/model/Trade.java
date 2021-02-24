package run.halo.app.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Trade {
    private String bigTradeName;
    private String allTradeValue;
    private String turnover;
    private String volume;
}

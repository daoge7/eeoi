package cn.ccsit.common.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsExclude;

import java.util.Objects;

@Data
public class CrmRelaCompanise {
    private String CUID;
    private String CLIENT_CODE;
    private String IACS_CODE;
    private String CLIENT_NAME_CN;
    private String CLIENT_NAME_EN;
    private String IS_VALID;
    private String CONTACT_NAME;
    private String CONTACT_TEL;
    private String CONTACT_EMAIL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CrmRelaCompanise that = (CrmRelaCompanise) o;
        return Objects.equals(CLIENT_CODE, that.CLIENT_CODE);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), CLIENT_CODE);
    }
}

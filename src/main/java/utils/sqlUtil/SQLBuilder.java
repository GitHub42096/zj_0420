package utils.sqlUtil;

import java.math.BigDecimal;

/**
 * @author huit
 * @date 19/07/2018
 */
public class SQLBuilder {
    StringBuilder sb = new StringBuilder();
    boolean isInsert;
    boolean isWhereAdd;

    public SQLBuilder(String sb) {
        this.sb = new StringBuilder(sb);
        if (sb.startsWith("update") || sb.startsWith("UPDATE")) {
            if (!sb.trim().endsWith("set")) {
                this.sb.append(" set ");
            }
        } else if (sb.startsWith("insert") || sb.startsWith("INSERT")) {
            if (!sb.trim().endsWith("(")) {
                this.sb.append(" values (");
            }
            isInsert = true;
        }
    }

    public SQLBuilder appendSetStr(String key, String value) {
        sb.append(key).append('=');
        appendStr(value);
        return this;
    }

    public SQLBuilder appendStr(String value) {
        if (null == value || value.length() == 0) {
            sb.append("null").append(',');
        } else {
            value = escapeSql(value);
            sb.append('\'').append(value).append('\'').append(',');
        }
        return this;
    }


    public String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char src = str.charAt(i);
            switch (src) {
                case '\'':
                    sb.append("''");
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                default:
                    sb.append(src);
                    break;
            }
        }
        return sb.toString();
    }


    public SQLBuilder appendInt(Object value) {
        if (null == value) {
            sb.append("null").append(',');
        } else {
            sb.append(value).append(',');
        }
        return this;
    }

    public SQLBuilder appendSetInt(String key, Object value) {
        sb.append(key).append('=');
        appendInt(value);
        return this;
    }

    public SQLBuilder appendIntZero(Object value) {
        if (null == value) {
            sb.append(0).append(',');
        } else {
            sb.append(value).append(',');
        }
        return this;
    }

    public SQLBuilder appendSetIntZero(String key, Object value) {
        sb.append(key).append('=');
        appendIntZero(value);
        return this;
    }

    public String build() {
        delLastComma();
        if (isInsert) {
            sb.append(")");
        }
        return sb.toString();
    }

    private void delLastComma() {
        if (',' == sb.charAt(sb.length() - 1)) {
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public SQLBuilder appendDecimal(BigDecimal amount) {
        sb.append(amount).append(',');
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public SQLBuilder appendWhere(String key, Object data) {
        if (!isWhereAdd) {
            delLastComma();
            sb.append(" where ");
            isWhereAdd = true;
        } else {
            sb.append(" and ");
        }
        sb.append(key).append("='").append(data).append('\'');
        return this;
    }
}

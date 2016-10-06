/**
 * This class is generated by jOOQ
 */
package sample.jooq.domain;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<Record> {

    private static final long serialVersionUID = 1732843091;

    /**
     * The reference instance of <code>spring_boot_blog.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>spring_boot_blog.user.id</code>.
     */
    public final TableField<Record, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>spring_boot_blog.user.avatar_url</code>.
     */
    public final TableField<Record, String> AVATAR_URL = createField("avatar_url", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>spring_boot_blog.user.bio</code>.
     */
    public final TableField<Record, String> BIO = createField("bio", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>spring_boot_blog.user.email</code>.
     */
    public final TableField<Record, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>spring_boot_blog.user.github</code>.
     */
    public final TableField<Record, String> GITHUB = createField("github", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>spring_boot_blog.user.name</code>.
     */
    public final TableField<Record, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * Create a <code>spring_boot_blog.user</code> table reference
     */
    public User() {
        this("user", null);
    }

    /**
     * Create an aliased <code>spring_boot_blog.user</code> table reference
     */
    public User(String alias) {
        this(alias, USER);
    }

    private User(String alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private User(String alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return SpringBootBlog.SPRING_BOOT_BLOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<Record, Long> getIdentity() {
        return Keys.IDENTITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(String alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    public User rename(String name) {
        return new User(name, null);
    }
}

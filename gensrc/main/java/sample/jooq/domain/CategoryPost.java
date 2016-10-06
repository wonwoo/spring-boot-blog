/**
 * This class is generated by jOOQ
 */
package sample.jooq.domain;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class CategoryPost extends TableImpl<Record> {

    private static final long serialVersionUID = -2101268933;

    /**
     * The reference instance of <code>spring_boot_blog.category_post</code>
     */
    public static final CategoryPost CATEGORY_POST = new CategoryPost();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>spring_boot_blog.category_post.id</code>.
     */
    public final TableField<Record, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>spring_boot_blog.category_post.category_id</code>.
     */
    public final TableField<Record, Long> CATEGORY_ID = createField("category_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>spring_boot_blog.category_post.post_id</code>.
     */
    public final TableField<Record, Long> POST_ID = createField("post_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>spring_boot_blog.category_post</code> table reference
     */
    public CategoryPost() {
        this("category_post", null);
    }

    /**
     * Create an aliased <code>spring_boot_blog.category_post</code> table reference
     */
    public CategoryPost(String alias) {
        this(alias, CATEGORY_POST);
    }

    private CategoryPost(String alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private CategoryPost(String alias, Table<Record> aliased, Field<?>[] parameters) {
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
        return Keys.IDENTITY_CATEGORY_POST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.KEY_CATEGORY_POST_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.KEY_CATEGORY_POST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.<ForeignKey<Record, ?>>asList(Keys.FKGRLW4Y7OYWGGM56QBNEHFN4A4, Keys.FKMHKF2T5E9LCR9DL9IXC36X4B3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryPost as(String alias) {
        return new CategoryPost(alias, this);
    }

    /**
     * Rename this table
     */
    public CategoryPost rename(String name) {
        return new CategoryPost(name, null);
    }
}

package me.wonwoo.github.asciidoc;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-05
 * Time : 오후 6:18
 * desc :
 */
public class GettingStartedGuide extends AbstractGuide {

    private final static String TYPE_LABEL = "Getting Started";

    // only used for JSON serialization
    public GettingStartedGuide() {
        this.setTypeLabel(TYPE_LABEL);
    }

    public GettingStartedGuide(GuideMetadata metadata) {
        super(metadata);
        this.setTypeLabel(TYPE_LABEL);
    }
}